# Edit Action Detection
## Setup and Run
Clone [Gumtree](https://github.com/GumTreeDiff/gumtree/tree/194393c98caa0a360c3f604786b6c552b714ef9c)

Clone this repository and execute the following command in its root directory to compare two files named `ExampleOld.java` and `ExampleNew.java`

`./gradlew run --args="gumtree local/old/Example.java local/new/Example.java /home/paul/dev/uni/tree-sitter-parser/tree-sitter-parser.py"`

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

## What are Changes?

### Manual Changes:

#### https://github.com/tsantalis/RefactoringMiner/commit/6ea5f15e5e1f3131ccd062c660f7832f760b348a
- UMLOperationBodyMapper#2157:
  - Replace Expr1 with Expr2: if(a) -> if(b)
  - Encapsulate Expr1 into Expr2: if(a) -> if(b(a))
  - Encapsulate Expr1 into Operation1 with Expr2: if(a) -> if(or(a, b))
- VariableReplacementAnalysis#1957
  - Remove Statement1, add Statement2
  - Encapsulate Stat1 into Stat2

#### https://github.com/danilofes/refactoring-toy-example/commit/36287f7c3b09eff78395267a3ac0d7da067863fd
- Refactroring Miner Output:
  - Pull Up Attribute	private age : int from class org.animals.Labrador to private age : int from class org.animals.Dog
  - Pull Up Attribute	private age : int from class org.animals.Poodle to private age : int from class org.animals.Dog
  - Pull Up Method	public getAge() : int from class org.animals.Labrador to public getAge() : int from class org.animals.Dog
  - Pull Up Method	public getAge() : int from class org.animals.Poodle to public getAge() : int from class org.animals.Dog
