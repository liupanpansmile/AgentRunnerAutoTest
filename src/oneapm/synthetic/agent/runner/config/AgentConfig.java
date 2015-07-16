package oneapm.synthetic.agent.runner.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by liujian on 2015/6/19 21:03.
 */
@XStreamAlias("agentConfig")
public class AgentConfig {
    @XStreamAlias("agentCmd")
    private String agentCmd;
    @XStreamAlias("agentId")
    private String agentId;
    @XStreamAlias("agentProfilePath")
    private String agentProfilePath;
    @XStreamAlias("profileSize")
    private int profileSize;
    @XStreamAlias("maxPoolSize")
    private int maxPoolSize;
    @XStreamAlias("poolSize")
    private int poolSize;
    @XStreamAlias("initThreadPoolSize")
    private int initThreadPoolSize;
    @XStreamAlias("extendThreadPoolSize")
    private int extendThreadPoolSize;
    @XStreamAlias("startPort")
    private int startPort;
    @XStreamAlias("endPort")
    private int endPort;
    @XStreamAlias("socketConnetRetryCount")
    private int socketConnetRetryCount;

    public String getAgentCmd() {
        return agentCmd;
    }

    public void setAgentCmd(String agentCmd) {
        this.agentCmd = agentCmd;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getAgentProfilePath() {
        return agentProfilePath;
    }

    public void setAgentProfilePath(String agentProfilePath) {
        this.agentProfilePath = agentProfilePath;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    public int getInitThreadPoolSize() {
        return initThreadPoolSize;
    }

    public void setInitThreadPoolSize(int initThreadPoolSize) {
        this.initThreadPoolSize = initThreadPoolSize;
    }

    public int getExtendThreadPoolSize() {
        return extendThreadPoolSize;
    }

    public void setExtendThreadPoolSize(int extendThreadPoolSize) {
        this.extendThreadPoolSize = extendThreadPoolSize;
    }

    public int getStartPort() {
        return startPort;
    }

    public void setStartPort(int startPort) {
        this.startPort = startPort;
    }

    public int getEndPort() {
        return endPort;
    }

    public void setEndPort(int endPort) {
        this.endPort = endPort;
    }

    public int getSocketConnetRetryCount() {
        return socketConnetRetryCount;
    }

    public void setSocketConnetRetryCount(int socketConnetRetryCount) {
        this.socketConnetRetryCount = socketConnetRetryCount;
    }

    public int getProfileSize() {
        return profileSize;
    }

    public void setProfileSize(int profileSize) {
        this.profileSize = profileSize;
    }
}