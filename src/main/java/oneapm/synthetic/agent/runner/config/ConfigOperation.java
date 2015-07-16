package oneapm.synthetic.agent.runner.config;

import org.apache.logging.log4j.Logger;

import oneapm.synthetic.agent.test.config.XMLUtil;

public class ConfigOperation {
	
	static Logger logger = org.apache.logging.log4j.LogManager.getLogger(ConfigOperation.class.getName());
	private ConfigOperation() {
	}

	static class Holder {
		static ConfigOperation instance = new ConfigOperation();
	}

	public static ConfigOperation instance() {
		return Holder.instance;
	}

	
	private void setPoolSize(AgentConfig agentConfig, int poolSize) {
		agentConfig.setPoolSize(poolSize);
		agentConfig.setProfileSize(2 * poolSize);
	}

	/* update agent runner config file */
	public void updateAgentRunnerConfigPoolSize(String configFilePath, int poolSize) {

		logger.info("updata agent runner config pool size");
		Config agentRunnerConfigObject = getAgentRunnerConfig(configFilePath);
		AgentConfig agentConfig = agentRunnerConfigObject.getAgentConfigs()
				.getAgentConfigList().get(0);
		this.setPoolSize(agentConfig, poolSize);
		XMLUtil.toFileFromBean(configFilePath, agentRunnerConfigObject,
				oneapm.synthetic.agent.runner.config.Config.class);
	}

	public Config getAgentRunnerConfig(String configFilePath) {
		try {
			return XMLUtil.toBeanFromFile("", configFilePath, Config.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
