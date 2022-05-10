# Edit Action Detection
## Setup and Run
Clone this repository and execute the following command in its root directory to compare two files named `ExampleOld.java` and `ExampleNew.java`

`./gradlew run --args="gumtree local/old/Example.java local/new/Example.java"`

or run

`./gradlew run --args="refactoringminer local/old local/new"`

## Interesting links
- [RefactoringMiner](https://github.com/tsantalis/RefactoringMiner): Action Detector for Java/Python
- [TreeSitter](https://tree-sitter.github.io/tree-sitter/): Parser
- [GumTree](https://github.com/GumTreeDiff/gumtree): Tree Differ

## TODO
- Brainstorm possible actions
- When should snapshots of changes be made to detect best actions
- How to handle retroactive reclassification of actions

## Goals
- actions should be complete: describe all changes
- set of actions should be minimal while being language agnostic
- actions should have high abstraction

## Possible applications
- Create summary of edit operations to reduce clutter in PRs
- Merge branches based on actions
- Recognize sequences that can be executed with shortcuts
- Preemptive recognition of sequences

