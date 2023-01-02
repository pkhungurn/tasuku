package example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tasuku.TaskSelectorUi;
import tasuku.Workspace;

public class RunUi {
  private static final Logger logger = LoggerFactory.getLogger(RunUi.class);

  public static void main(String[] args) {
    Workspace workspace = Workspace.builder().build();
    Tasks.define(workspace);
    TaskSelectorUi.run(workspace).ifPresent(name -> {
      logger.info(String.format("Running %s ...", name));
      workspace.startSession();
      workspace.run(name);
      workspace.endSession();
      logger.info(String.format("Finished %s", name));
    });
  }
}
