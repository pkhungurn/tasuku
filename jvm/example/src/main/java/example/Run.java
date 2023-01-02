package example;

import tasuku.Workspace;

public class Run {
  public static void main(String[] args) {
    Workspace workspace = Workspace.builder().build();
    Tasks.define(workspace);
    workspace.startSession();
    for (String taskname : args) {
      workspace.run(taskname);
    }
    workspace.endSession();
  }
}
