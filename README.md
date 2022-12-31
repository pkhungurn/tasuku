# tasuku

A task execution system implemented in a few languages. Think of it as a tool similar to [GNU Make](https://www.gnu.org/software/make/) but you have to implement the command line interface yourself.

## What does it do?

`tasuku` allows you to define "tasks." A task is a piece of computation that you want to run like like compiling some code, linking some programs, creating/removing files, and so on. A task can be dependent on other tasks, which means that the dependent task can only be executed only after all of its dependencies have been executed. In this way, you can create a dependency graph between tasks in which tasks are vertices, and you draw a directed edge from a dependency to each task that depends on it. `tasuku` ensures that the graph is well formed; that is, the graph has no loops. When you use `tasuku` to execute a task, it takes care to traverse the dependency graph and execute tasks in the right topological order.

## Types of Tasks

Similar to Make, there two main types of tasks.

1. A **command tasks** is a task that is always executed when invoked or when one of its dependencies need to be executed.
2. A **file tasks** is a task that produces a file. It is executed if (1) the output file does not exist, (2) the output file's timestamp is older than one of its (transitive) dependency file tasks, or (3) one of its dependency was invoked. The idea is that a file task is only executed when it is needed to be updated. 

It is not advisable to make a file task dependent on a command task because the file task will be always be executed regardless of the file's timestamp.

## Task Names

## Task-Picking UI
