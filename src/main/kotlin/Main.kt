import com.github.gumtreediff.client.Run
import com.github.gumtreediff.gen.TreeGenerators
import com.github.gumtreediff.io.TreeIoUtils
import com.github.gumtreediff.matchers.MappingStore
import com.github.gumtreediff.matchers.Matcher
import com.github.gumtreediff.matchers.Matchers

fun main(args: Array<String>) {
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

        println("TODO: Experiment with mappings here")
    }
}