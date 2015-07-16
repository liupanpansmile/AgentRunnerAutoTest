package oneapm.synthetic.agent.test.unittest;

import oneapm.synthetic.agent.test.config.AgentRunnerConfig;
import oneapm.synthetic.agent.test.config.Config;
import oneapm.synthetic.agent.test.config.Configuration;

public class ConfigTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Config config = Configuration.instance().getConfig() ;
		AgentRunnerConfig agentRunnerConfig =  config.getAgentRunnerConfig();
		
		int poolSizeMin = agentRunnerConfig.getPoolSizeMin() ;
		int poolSizeMax = agentRunnerConfig.getPoolSizeMax() ;
		String  configFilePath = agentRunnerConfig.getConfigFilePath() ;
		String pidFile = agentRunnerConfig.getPidFile() ;
		
		System.out.println("min: " + poolSizeMin);
		System.out.println("max: " + poolSizeMax);
		System.out.println("configFilePath: " + configFilePath);
		System.out.println("pidFile: " + pidFile);
		

	}

}
