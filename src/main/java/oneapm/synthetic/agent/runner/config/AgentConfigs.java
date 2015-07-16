package oneapm.synthetic.agent.runner.config;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;


public class AgentConfigs {
    @XStreamImplicit(itemFieldName = "agentConfig")
    private List<AgentConfig> agentConfigList;

    public List<AgentConfig> getAgentConfigList() {
        return agentConfigList;
    }

    public void setAgentConfigList(List<AgentConfig> agentConfigList) {
        this.agentConfigList = agentConfigList;
    }
}