package nl.sempeters.impl;

import nl.sempeters.api.DeployableAction;
import nl.sempeters.other.ActionUtils;

public class DeployableActionImpl implements DeployableAction {

    private String result;

    @Override
    public void run() {
        result = "I have run!";
        ActionUtils.runUtilMethod();
    }

    @Override
    public String getResult() {
        return result;
    }
}
