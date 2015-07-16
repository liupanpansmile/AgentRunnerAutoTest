package oneapm.synthetic.agent.test.unittest;

import oneapm.synthetic.agent.runner.config.ConfigOperation;
import oneapm.synthetic.agent.runner.report.Report;
import oneapm.synthetic.agent.test.config.AgentRunnerConfig;
import oneapm.synthetic.agent.test.config.Config;
import oneapm.synthetic.agent.test.config.Configuration;
import oneapm.synthetic.agent.test.result.GenerateResultReport;
import oneapm.synthetic.agent.test.util.FileUtil;
import oneapm.synthetic.agent.test.util.ProcessUtil;

public class ModifyAgentRunnerConfigTest {

	public static void main(String[] args) {
		

		
		 Config config = Configuration.instance().getConfig();
			int poolSizeMin = config.getAgentRunnerConfig().getPoolSizeMin();
			int poolSizeMax = config.getAgentRunnerConfig().getPoolSizeMax();
			

			String agentRunnerConfigPath = config.getAgentRunnerConfig().getConfigFilePath() ;
			AgentRunnerConfig agentRunnerConfig = config.getAgentRunnerConfig() ;
			
			for (int poolSize = poolSizeMin; poolSize <= poolSizeMax; ++poolSize) {
				/*modify agent runner config poolsize*/
				ConfigOperation.instance().updateAgentRunnerConfigPoolSize(agentRunnerConfigPath, poolSize);
				
				
			}
	
	}

}
