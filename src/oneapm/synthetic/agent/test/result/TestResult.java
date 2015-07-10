package oneapm.synthetic.agent.test.result;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import oneapm.synthetic.agent.test.config.Config;
import oneapm.synthetic.agent.test.config.Configuration;
import oneapm.synthetic.agent.test.config.ProcessConfig;
import oneapm.synthetic.agent.test.util.ProcessUtil;


public class TestResult {
	
	
	private static final Logger logger = LogManager.getLogger(TestResult.class.getName());
	
	// Éú³Éosl½Å±¾
	public  void formOSLScript(ProcessConfig processConfig){
		
		String processAddress = processConfig.getProcessAddress() ;
		String cmdLine = processConfig.getCmdLine() ;
		String workDirectory = processConfig.getWorkDirectory() ;
		
		ProcessUtil.launchProcess(processAddress, cmdLine,workDirectory);
	}
	
	public void checkResultReady(Config config){
		Thread thread = new Thread(new ScanRunnerResultThread(config.getResultPath(), config.getCheckFrequency())) ;
		thread.start();
		try {
			logger.info("scanRunnerResultThread join");
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			logger.warn("join checkResultReady interrupt " + e) ;
		}
	}

	public static void main(String[] args) {
		
		TestResult testResult = new TestResult() ;
		Config config = Configuration.instance().getConfig() ;
		ProcessConfig processConfig = config.getProcessConfig() ;
		
		
		testResult.formOSLScript(processConfig);
		testResult.checkResultReady(config);
		GenerateResultReport.instance().gererateStatisticsReport(config.getResultPath(), config.getResultReportPath());
		
		logger.info("execute end..........");
		

	}

}
