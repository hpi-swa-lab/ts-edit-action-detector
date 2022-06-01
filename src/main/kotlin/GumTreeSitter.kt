import com.github.gumtreediff.client.Run
import com.github.gumtreediff.gen.treesitter.JavaTreeSitterTreeGenerator
import com.github.gumtreediff.matchers.Matchers

object GumTreeSitter {
    fun run(f1: String, f2: String, treeSitterPath: String) {
        val matcher = Matchers.getInstance().matcher
        System.setProperty("gt.ts.path", treeSitterPath)
        val r1 = JavaTreeSitterTreeGenerator().generateFrom().file(f1).root
        val r2 = JavaTreeSitterTreeGenerator().generateFrom().file(f2).root
        val mappings = matcher.match(r1, r2)
        for (mapping in mappings) {
            println(mapping.toString())
        }
    }
}
