package oneapm.synthetic.agent.test.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;


public class ProcessConfig {

	@XStreamAlias("processAddress")
	private String processAddress ;
	
	@XStreamAlias("cmdLine")
	private String cmdLine ;
	
	@XStreamAlias("workDirectory")
	private String workDirectory ;
	
	public String getProcessAddress() {
		return processAddress;
	}

	public void setProcessAddress(String processAddress) {
		this.processAddress = processAddress;
	}

	public String getCmdLine() {
		return cmdLine;
	}

	public void setCmdLine(String cmdLine) {
		this.cmdLine = cmdLine;
	}
	
	public String getWorkDirectory() {
		return workDirectory;
	}

	public void setWorkDirectory(String workDirectory) {
		this.workDirectory = workDirectory;
	}

}
