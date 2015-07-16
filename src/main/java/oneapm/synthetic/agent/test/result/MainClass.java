package oneapm.synthetic.agent.test.result;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import oneapm.synthetic.agent.runner.config.ConfigOperation;
import oneapm.synthetic.agent.runner.report.Report;
import oneapm.synthetic.agent.test.config.AgentRunnerConfig;
import oneapm.synthetic.agent.test.config.Config;
import oneapm.synthetic.agent.test.config.Configuration;
import oneapm.synthetic.agent.test.util.FileUtil;
import oneapm.synthetic.agent.test.util.ProcessUtil;
import oneapm.synthetic.script.build.BatchBuildScript;
import oneapm.synthetic.script.config.ScriptConfig;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainClass {

	private static final Logger logger = LogManager.getLogger(MainClass.class
			.getName());

	// generate script,and return generated script number
	public int formOSLScript(ScriptConfig scriptConfig) {

		BatchBuildScript batchBuildScript = new BatchBuildScript(
				scriptConfig.getTemplateFilename(),
				scriptConfig.getDestFilePath(), scriptConfig.getScriptCount());
		batchBuildScript.batchBuildScript();
		logger.info("batch build script count:" + scriptConfig.getScriptCount());
		return scriptConfig.getScriptCount();
	}

	public void checkResultReady(Config config) {
		Thread thread = new Thread(new ScanRunnerResultThread(
				config.getResultPath(), config.getCheckFrequency()));
		thread.start();
		try {
			logger.info("scanRunnerResultThread join");
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			logger.warn("join checkResultReady interrupt " + e);
		}
	}

	private Report setReport(Config config, int poolSize) {

		Instant startTimeStamp = null;// = Instant.now();
		Instant endTimeStamp = null;

		ConfigOperation.instance().getAgentRunnerConfig(
				config.getAgentRunnerConfig().getConfigFilePath());

		int jobCount = config.getScriptConfig().getScriptCount();
		formOSLScript(config.getScriptConfig());
		
		startTimeStamp = Instant.now();
		checkResultReady(config);
		endTimeStamp = Instant.now();
		
		Report report = new Report();
		report.setStartTime(startTimeStamp);
		report.setEndTime(endTimeStamp);

		report.setPoolSize(poolSize);
		report.setJobCount(jobCount);
		return report;
	}

	private String getCurrentTime(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		return df.format(new Date()) ;
	}
	
	private long getAgentRunnerPid(String pidFile){
		long pid = -1 ;
		try{
			pid = Long.parseLong(FileUtil.readFile(pidFile)) ;
			logger.info("current agent runner pid: " + pid);
		}catch(ClassCastException e){
			logger.warn("pid transfer failed:" + e.getMessage());
		}
		return pid ;
	}
	public void doRegressWork(Config config) {
		logger.info("do regress work");
		oneapm.synthetic.agent.runner.config.Config userConfig = ConfigOperation.instance().getAgentRunnerConfig(config.getAgentRunnerConfig().getConfigFilePath());
		int poolSize = userConfig.getAgentConfigs().getAgentConfigList().get(0).getPoolSize();
		Report report = setReport(config, poolSize);
		GenerateResultReport.instance().gererateStatisticsReport(config.getResultPath(),config.getResultReportPath(),getCurrentTime(), report);
	}

	public void nonDoRegressWork(Config config) {
		logger.info("do non regress work");
		int poolSizeMin = config.getAgentRunnerConfig().getPoolSizeMin();
		int poolSizeMax = config.getAgentRunnerConfig().getPoolSizeMax();
		String timeStamp = getCurrentTime() ;

		AgentRunnerConfig agentRunnerConfig = config.getAgentRunnerConfig() ;
		String agentRunnerConfigPath = agentRunnerConfig.getConfigFilePath() ;
		
		for (int poolSize = poolSizeMin; poolSize <= poolSizeMax; ++poolSize) {
			/*modify agent runner config poolsize*/
			ConfigOperation.instance().updateAgentRunnerConfigPoolSize(agentRunnerConfigPath, poolSize);
			
			/*kill process by pid*/
			long pid = getAgentRunnerPid(agentRunnerConfig.getPidFile());
			ProcessUtil.killProcessByPID(pid);

			Report report = setReport(config, poolSize);
			GenerateResultReport.instance().gererateStatisticsReport(config.getResultPath(), config.getResultReportPath(),timeStamp,report,true);
		}
	}

	public void analysisAgentRunnerResult(Config config) {
		if (config.getAgentRunnerConfig().isDoRegress()) {
			doRegressWork(config);
		} else {
			nonDoRegressWork(config);
		}
	}

	public static void main(String[] args) {

		MainClass mainClass = new MainClass();
		Config config = Configuration.instance().getConfig();

		mainClass.analysisAgentRunnerResult(config);
		logger.info("execute end..........");

	}

}
