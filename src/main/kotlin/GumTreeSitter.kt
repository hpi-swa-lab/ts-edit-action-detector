import com.github.gumtreediff.gen.treesitter.JavaTreeSitterTreeGenerator
import com.github.gumtreediff.matchers.Mapping
import com.github.gumtreediff.matchers.Matchers
import com.github.gumtreediff.tree.Tree
import java.io.File

object GumTreeSitter {
    fun run(f1: String, f2: String, treeSitterPath: String) {
        val matcher = Matchers.getInstance().matcher
        System.setProperty("gt.ts.path", treeSitterPath)

        val ctx = JavaTreeSitterTreeGenerator().generateFrom().file(f1)
        val r1 = ctx.root
        val r2 = JavaTreeSitterTreeGenerator().generateFrom().file(f2).root

        val c1 = File(f1).readText()
        val c2 = File(f2).readText()

        val mappings = matcher.match(r1, r2)

        println(" - Actions from Node Insertions / Deletions - ")
        val nodeActions = GranularScriptGenerator().computeActions(mappings).asList()
        for (action in nodeActions) {
            println(action.displayName)
            // println(action)
            // println(action.node.text(c1))
        }
        println("#############################")

        val treeActions = SimpleScriptGenerator().computeActions(mappings).asList()
        println(" - Actions from Tree Insertions / Deletions - ")
        for (action in treeActions) {
            println(action.displayName)
            // println(action)
            // println(action.node.text(c1))
        }

        println()

        // println(ActionsIoUtils.toText(ctx, actions, mappings))
    }

}

fun Tree.text(content: String)
        = content.substring(pos, pos + length)

fun treeEquals(text1: String, text2: String): (Mapping) -> Boolean {
    return { (t1, t2) ->
        t1.pos == t2.pos &&
                t1.length == t2.length &&
                t1.text(text1) == t2.text(text2)
    }
}

private operator fun Mapping.component1() = first
private operator fun Mapping.component2() = second
