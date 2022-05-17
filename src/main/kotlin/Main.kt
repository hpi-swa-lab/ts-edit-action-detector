import com.github.gumtreediff.actions.ChawatheScriptGenerator
import com.github.gumtreediff.actions.EditScript
import com.github.gumtreediff.actions.EditScriptGenerator
import com.github.gumtreediff.actions.SimplifiedChawatheScriptGenerator
import com.github.gumtreediff.client.Run
import com.github.gumtreediff.gen.TreeGenerators
import com.github.gumtreediff.io.TreeIoUtils
import com.github.gumtreediff.matchers.MappingStore
import com.github.gumtreediff.matchers.Matcher
import com.github.gumtreediff.matchers.Matchers
import gr.uom.java.xmi.UMLModelASTReader
import org.apache.log4j.BasicConfigurator
import org.refactoringminer.api.Refactoring
import org.refactoringminer.api.RefactoringHandler
import org.refactoringminer.rm1.GitHistoryRefactoringMinerImpl
import org.refactoringminer.util.GitServiceImpl
import java.io.File


fun main(args: Array<String>) {
//    BasicConfigurator.configure()

    val others = args.sliceArray(1 until args.size)
    if (args[0] == "gumtree") {
        useGumtree(others)
    } else if (args[0] == "refactoringminer" && others.isEmpty()) {
        useRefMinerOnOwnRepo()
    } else {
        useRefactoringMiner(others)
    }
}

private fun useGumtree(args: Array<String>) {
    println("Files to load: ${args.joinToString()}")

    println("TODO: Do cool stuff here")

    // Example similar to those [on this Gumtree wiki page](https://github.com/GumTreeDiff/gumtree/blob/main/build.gradle)
    Run.initGenerators() // registers the available parsers

    val contexts = args.map { TreeGenerators.getInstance().getTree(it) } // retrieves and applies the default parser for the files

    contexts.forEach { context ->
        println(context.root.toTreeString())

        println("\n============ JSON ============")
        println(TreeIoUtils.toJson(context).toString()) // displays the tree in JSON syntax
    }

    val defaultMatcher: Matcher = Matchers.getInstance().matcher // retrieves the default matcher

    if (contexts.size > 1) {
        val mappings: MappingStore = defaultMatcher.match(contexts[0].root, contexts[1].root) // computes the mappings between the trees

        println("\n============ Mappings ============")
        mappings
            .filterNot { it.first.pos == it.second.pos && it.first.endPos == it.second.endPos }
            .forEach { println("${it.first} : ${it.second}") }

        println("\n============ Tree Actions ============")
        val extendedEditScriptGenerator: EditScriptGenerator = ChawatheScriptGenerator()
        val fullActions: EditScript = extendedEditScriptGenerator.computeActions(mappings)
        fullActions.forEach { println("${it.name}: ${it.node}") }

        println("\n============ Simplified Tree Actions ============")
        val editScriptGenerator: EditScriptGenerator = SimplifiedChawatheScriptGenerator()
        val actions: EditScript = editScriptGenerator.computeActions(mappings)
        actions.forEach { println("${it.name}: ${it.node}") }
    }
}

private fun useRefactoringMiner(args: Array<String>) {
    println("Files to load: ${args.joinToString()}")

    val model1 = UMLModelASTReader(File(args[0]).absoluteFile).umlModel
    val model2 = UMLModelASTReader(File(args[1]).absoluteFile).umlModel
    val modelDiff = model1.diff(model2)
    val refactorings = modelDiff.refactorings
    println(refactorings.toString())

}

private fun useRefMinerOnOwnRepo() {
    val gitService = GitServiceImpl()
    val miner = GitHistoryRefactoringMinerImpl()

    val repo = gitService.cloneIfNotExists(
            "tmp/ref-toy-example2",
        "https://github.com/danilofes/refactoring-toy-example.git")

    val delAttribs = "f3b628f8f9c71a2cdfa052025c4a1ed78ee4c45d"
    val pullUp = "36287f7c3b09eff78395267a3ac0d7da067863fd"
    val reateToString = "5849e143567474f037950f005d994729de0775fc"

    miner.detectAll(repo, "master", object: RefactoringHandler() {
        override fun handle(commitId: String, refactorings: List<Refactoring>) {
            if (refactorings.isNotEmpty()) {
                println("\n####    Refactorings at $commitId")
            }
            for (ref in refactorings) {
                println(ref.toString())
            }
            println()
        }
    })
}
