# tasuku

`tasuku` （タスク） is a task execution system implemented in Java. Think of it as a tool similar to [GNU Make](https://www.gnu.org/software/make/) but you have to implement the command line interface yourself.

## What does it do?

`tasuku` allows you to define "tasks." A **task** is a piece of computation that you want to run like like compiling some code, linking some programs, creating/removing files, and so on. A task can be dependent on other tasks, which means that the dependent task can only be executed only after all of its dependencies have been executed. In this way, you can create a dependency graph between tasks in which tasks are vertices, and you draw a directed edge from a dependency to each task that depends on it. `tasuku` ensures that the graph is well formed; that is, the graph has no loops. When you use `tasuku` to execute a task, it takes care to traverse the dependency graph and execute tasks in the right topological order.

### Types of Tasks

Similar to Make, there two main types of tasks.

1. A **command tasks** is a task that is always executed when invoked or when one of its dependencies need to be executed.
2. A **file tasks** is a task that produces a file. It is executed if (1) the output file does not exist, (2) the output file's timestamp is older than one of its (transitive) dependency file tasks, or (3) one of its dependency was invoked. The idea is that a file task is only executed when it is needed to be updated. 

It is not advisable to make a file task dependent on a command task because the file task will be always be executed regardless of the file's timestamp.

### Task Names

A task name is similar to a path name of files. The path is always relative to the current directory. For example, you can have

* `a.txt`
* `b/c.txt`
* `b/d/e.txt`
* `b/create_all`
* `b/remove_all`

The first three tasks are supposed to be file tasks (which generally take the name of their output files), and the last two command tasks. We can see that tasks can form directory structures like files, and this is a nice way to organize tasks when there's a mixture of file and command tasks.

### Task-Picking UI

Executing a tasks requires you to know its name. Remembering a task's name when you have create several tens of them can be daunting. `tasuku` comes with a UI that helps you navigate the task directory structure in order to pick one task to execute.

## Installation

I have not implemented any type of automatic installation. Just copy the code you want to use into your repository. See specific instructions inside each language's directory.

## Why `tasuku`?

`tasuku` is one of my self-created software libraries that I always rely on when I have to manage multi-step computation. As an example, in machine learning research, your workflow might look like the following.

1. Download some raw data from the web.
1. Split the data into training, validation, and test datasets.
1. Train ML models on the training dataset with under several hyperparameter settings.
1. Evaluate the models using the validation dataset.
1. Pick the best model according to some metrics.
1. Evaluate the best model using the test dataset.

You can see that each step (except for the first one) depends on those that come before it. Moreover, some of the steps (like Step 3) can take a very long time to complete.

It does not make sense to implement the steps in a single program that runs them sequentially. Some of the steps can fail (e.g., because of bugs in your code, or because a blackout while you are training your models), and you might want to retry them again. A sequential program would redo everything from scratch, not just the only parts that you want to retry. `tasuku` allows you to take advantage of "cached" results, pretty much like Make would only build only parts of a program that need to be changed when you modify a source file.

I created `tasuku` instead of using other build tools such as Make, [Rake](https://ruby.github.io/rake/), [Gradle](https://gradle.org/), or [Bazel](https://bazel.build/) because I would like to have more control on the system. This gives me freedom to define what tasks are, dicate the format or task names, and build the task-picking UI without having to study existing systems in details.

## Release History

* (2023/01/05) Removed the Python implementation. It has been refactored into [pytasuku](https://github.com/pkhungurn/pytasuku).
* (2023/01/01) First release.