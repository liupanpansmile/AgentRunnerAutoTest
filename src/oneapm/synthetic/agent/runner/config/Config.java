package oneapm.synthetic.agent.runner.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by liujian on 2015/6/19 11:10.
 */




@XStreamAlias("config")
public class Config {
    @XStreamAlias("agentConfigs")
    private AgentConfigs agentConfigs;
    @XStreamAlias("jobFilePath")
    private String jobFilePath;
    @XStreamAlias("resultFilePath")
    private String resultFilePath;
    @XStreamAlias("redisFilePath")
    private String redisFilePath;
    @XStreamAlias("redisOn")
    private boolean redisOn;
    @XStreamAlias("redisPort")
    private int redisPort;
    public AgentConfigs getAgentConfigs() {
        return agentConfigs;
    }

    public void setAgentConfigs(AgentConfigs agentConfigs) {
        this.agentConfigs = agentConfigs;
    }

    public String getJobFilePath() {
        return jobFilePath;
    }

    public void setJobFilePath(String jobFilePath) {
        this.jobFilePath = jobFilePath;
    }

    public String getResultFilePath() {
        return resultFilePath;
    }

    public void setResultFilePath(String resultFilePath) {
        this.resultFilePath = resultFilePath;
    }

    public String getRedisFilePath() {
        return redisFilePath;
    }

    public void setRedisFilePath(String redisFilePath) {
        this.redisFilePath = redisFilePath;
    }

    public boolean isRedisOn() {
        return redisOn;
    }

    public void setRedisOn(boolean redisOn) {
        this.redisOn = redisOn;
    }

    public int getRedisPort() {
        return redisPort;
    }

    public void setRedisPort(int redisPort) {
        this.redisPort = redisPort;
    }
    

}