package oneapm.synthetic.agent.test.config ;

import com.thoughtworks.xstream.annotations.XStreamAlias;



@XStreamAlias("config")
public class Config {
    @XStreamAlias("resultPath")
    private String resultPath;
    
    @XStreamAlias("checkFrequency")
    private int checkFrequency;
    
    @XStreamAlias("processConfig")
    private ProcessConfig processConfig ;

    @XStreamAlias("resultReportPath")
    private String resultReportPath ;
    
	public String getResultPath() {
		return resultPath;
	}

	public void setResultPath(String resultPath) {
		this.resultPath = resultPath;
	}

	public ProcessConfig getProcessConfig() {
		return processConfig;
	}

	public void setProcessConfig(ProcessConfig processConfig) {
		this.processConfig = processConfig;
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

	
	
}			
