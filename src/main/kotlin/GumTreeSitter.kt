import com.github.gumtreediff.client.Run
import com.github.gumtreediff.gen.treesitter.JavaTreeSitterTreeGenerator
import com.github.gumtreediff.matchers.Mapping
import com.github.gumtreediff.matchers.Matchers
import com.github.gumtreediff.tree.Tree
import java.io.File

object GumTreeSitter {
    fun run(f1: String, f2: String, treeSitterPath: String) {
        val matcher = Matchers.getInstance().matcher
        System.setProperty("gt.ts.path", treeSitterPath)
        val r1 = JavaTreeSitterTreeGenerator().generateFrom().file(f1).root
        val r2 = JavaTreeSitterTreeGenerator().generateFrom().file(f2).root

        val c1 = File(f1).readText()
        val c2 = File(f2).readText()

        val mappings = matcher.match(r1, r2)
        for ((tree1, tree2) in mappings) {
            println(tree1.toTreeString())
            println(tree2.toTreeString())
            println()
        }
    }

    private fun Tree.text(content: String)
        = content.substring(pos, pos + length)
}

private operator fun Mapping.component1() = first
private operator fun Mapping.component2() = second
