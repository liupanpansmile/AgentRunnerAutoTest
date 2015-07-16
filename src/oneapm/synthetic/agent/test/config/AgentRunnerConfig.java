package oneapm.synthetic.agent.test.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class AgentRunnerConfig {
	
	@XStreamAlias("poolSizeMin")
    private int poolSizeMin;
	
	@XStreamAlias("poolSizeMax")
    private int poolSizeMax;
	
	@XStreamAlias("configFilePath")
    private String configFilePath;
	
	@XStreamAlias("pidFile")
    private String pidFile;

	@XStreamAlias("doRegress")
    private boolean doRegress;
	
	public boolean isDoRegress() {
		return doRegress;
	}

	public void setDoRegress(boolean doRegress) {
		this.doRegress = doRegress;
	}

	public int getPoolSizeMin() {
		return poolSizeMin;
	}

	public void setPoolSizeMin(int poolSizeMin) {
		this.poolSizeMin = poolSizeMin;
	}

	public int getPoolSizeMax() {
		return poolSizeMax;
	}

	public void setPoolSizeMax(int poolSizeMax) {
		this.poolSizeMax = poolSizeMax;
	}

	public String getConfigFilePath() {
		return configFilePath;
	}

	public void setConfigFilePath(String configFilePath) {
		this.configFilePath = configFilePath;
	}

	public String getPidFile() {
		return pidFile;
	}

	public void setPidFile(String pidFile) {
		this.pidFile = pidFile;
	}
	
	

}
