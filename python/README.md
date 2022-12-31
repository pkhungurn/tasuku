# pytasuku

`pytasuku` is a Python implementation of `tasuku`.

## Requirements

The code should work with Python version 3.8 or later. The UI is implemented with [`tkinter`](https://docs.python.org/3/library/tkinter.html), which should come automatically with your Python distribution.

## Installation

Just copy the `src/pytasuku` to your source code repository. All the code is available in the `pytasuku` package.

## Running the Example

There's example code in the `src/example` directory. To run it, create a Python environmet first. I used [Anaconda](https://www.anaconda.com/) to do the job. After installing Anaconda, I ran the following command in my shell.

```
> conda create -n pytasuku python=3.8
```

Then, you can activate the environment by running the command below.

```
> conda activate pytasuku
```

Change the directory to the one where this file is located. Then, run

```
python src/example/run_ui.py
```

to run the task-picking UI. To execute a task directly, run a command like

```
python src/example/run.py <task-name>
```

For example, to create all the files prepared as parts the example run:

```
python src/example/run.py data/create_all
```

To delete all the files to start the process over, run:

```
python src/example/run.py data/delete_all
```

## Usage Guide

TODO

## Organzing the Tasks

TODO