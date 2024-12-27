package nl.sempeters.api;

public interface DeployableAction {
    void run();
    String getResult();
}
