package oneapm.synthetic.agent.test.unittest;

import oneapm.synthetic.agent.runner.config.AgentConfig;
import oneapm.synthetic.agent.test.config.AgentRunnerConfig;
import oneapm.synthetic.agent.test.config.Config;
import oneapm.synthetic.agent.test.config.Configuration;
import oneapm.synthetic.agent.test.config.XMLUtil;


public class XMLUtilTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Config config = Configuration.instance().getConfig() ;
		AgentRunnerConfig agentRunnerConfig =  config.getAgentRunnerConfig();
		
	//	int poolSizeMin = agentRunnerConfig.getPoolSizeMin() ;
	//	int poolSizeMax = agentRunnerConfig.getPoolSizeMax() ;
		String  configFilePath = agentRunnerConfig.getConfigFilePath() ;
		String pidFile = agentRunnerConfig.getPidFile() ;
	try {
		oneapm.synthetic.agent.runner.config.Config agentRunnerConfigObject = XMLUtil.toBeanFromFile("", configFilePath, oneapm.synthetic.agent.runner.config.Config.class) ;
		AgentConfig agentConfig = agentRunnerConfigObject.getAgentConfigs().getAgentConfigList().get(0) ;
		int poolSize = agentConfig.getPoolSize() ;
		System.out.println("poolSize = " + poolSize);
		agentConfig.setPoolSize(100);
		XMLUtil.toFileFromBean(configFilePath, agentRunnerConfigObject, oneapm.synthetic.agent.runner.config.Config.class);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
	
	}

}
