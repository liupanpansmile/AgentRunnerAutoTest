package oneapm.synthetic.agent.test.config ;

import oneapm.synthetic.script.config.ScriptConfig;

import com.thoughtworks.xstream.annotations.XStreamAlias;



@XStreamAlias("config")
public class Config {
    @XStreamAlias("resultPath")
    private String resultPath;
    
    @XStreamAlias("checkFrequency")
    private int checkFrequency;
  
    @XStreamAlias("resultReportPath")
    private String resultReportPath ;
    
    @XStreamAlias("scriptConfig")
    private ScriptConfig scriptConfig ;

  
    
    @XStreamAlias("agentRunnerConfig")
    private AgentRunnerConfig agentRunnerConfig ;



	public String getResultPath() {
		return resultPath;
	}



	public void setResultPath(String resultPath) {
		this.resultPath = resultPath;
	}



	public int getCheckFrequency() {
		return checkFrequency;
	}



	public void setCheckFrequency(int checkFrequency) {
		this.checkFrequency = checkFrequency;
	}



	public String getResultReportPath() {
		return resultReportPath;
	}



	public void setResultReportPath(String resultReportPath) {
		this.resultReportPath = resultReportPath;
	}



	public ScriptConfig getScriptConfig() {
		return scriptConfig;
	}



	public void setScriptConfig(ScriptConfig scriptConfig) {
		this.scriptConfig = scriptConfig;
	}



	public AgentRunnerConfig getAgentRunnerConfig() {
		return agentRunnerConfig;
	}



	public void setAgentRunnerConfig(AgentRunnerConfig agentRunnerConfig) {
		this.agentRunnerConfig = agentRunnerConfig;
	}
    


	
	
}			
