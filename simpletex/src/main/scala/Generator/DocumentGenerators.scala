package simpletex.generator
import simpletex.generator.LatexDocument
import simpletex.compiler.{
  SimpleTexCompiler,
  SimpleTexCompilationError,
  SimpleTexGeneratorError
}
import simpletex.parser.{SimpleTexAST, Content}

object Generator {
  def generateAST(node: SimpleTexAST)
  def generateContent(node: Content)
  def apply(node: SimpleTexAST)
}
object DocumentGenerator {
  def apply(
      ast: SimpleTexAST,
      layout: List[Layout]
  ): Either[SimpleTexCompilationError, LatexDocument] = {
    val doc = LatexDocument(layout)
    Left(SimpleTexGeneratorError("not implemented"))
  }

  /* def bold ..
   * def italics ...
   *
   *
   */
  /* def section(...) match
 *
 * bold:  -> bold(content) or produce here \bf ...
 * italics: -> \it...
 *
 *
 *
 *
 */
}
