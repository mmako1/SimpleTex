/**
  * This file contains the parser
  */
package simpletex.parser
import scala.util.parsing.combinator.Parsers
import scala.util.parsing.input.{NoPosition, Position, Reader}
import simpletex.lexer._

object SimpleTexParser extends Parsers {
  override type Elem = SimpleTexToken
  class SimpleTexTokenReader(tokens: Seq[SimpleTexToken])
      extends Reader[SimpleTexToken] {
    override def first: SimpleTexToken = tokens.head
    override def atEnd: Boolean = tokens.isEmpty
    override def pos: Position = NoPosition
    override def rest: Reader[SimpleTexToken] =
      new SimpleTexTokenReader(tokens.tail)
  }

  def apply(tokens: Seq[SimpleTexToken]): Either[String, SimpleTexAST] = {
    val reader = new SimpleTexTokenReader(tokens)
    document(reader) match {
      case NoSuccess(msg, next)  => Left(msg)
      case Success(result, next) => Right(result)
    }
  }

  def document: Parser[SimpleTexAST] = {
    phrase(block)
  }

  def block: Parser[SimpleTexAST] = {
    rep1(sections) ^^ { case stmtList => stmtList } //TODO reduce/fold to something that is an SimpletexAST type
  }

  def sections: Parser[SimpleTexAST] = {
    val mainSection =
      section ~ rep(mainSubsection) ~ rep(content) ^^ {
        case SECTION(title) ~ subsections ~ contents =>
          Section(title, subsections, contents)
      }
    val sectionLayout = layout ~ mainSection ^^ {
      case LAYOUT(layout) ~ mainSection =>
        LayoutSection(layout, mainSection)
    }

    val content =???

    val mainSubsection = subsection ~ rep(content) ^^ {
      case SUBSECTION(title) ~ contents => Subsection(title, contents)
    }

    mainSubsection
  }

  private def text: Parser[CONTENT] = { // TODO should consider renaming content and plaintext ==> same idea but different names
    accept("plaintext", { case s @ CONTENT(name) => s })
  }
  private def bold: Parser[BOLD] = {
    accept("bold", { case s @ BOLD(name) => s })
  }
  private def italics: Parser[ITALICS] = {
    accept("italics", { case s @ ITALICS(name) => s })
  }
  private def bolditalics: Parser[BOLDITALICS] = {
    accept("bolditalics", { case s @ BOLDITALICS(name) => s })
  }
  private def citation: Parser[CITATION] = {
    accept("citation", { case s @ CITATION(name) => s })
  }
  private def reference: Parser[REFERENCE] = {
    accept("reference", { case s @ REFERENCE(name) => s })
  }
  private def image: Parser[IMAGE] = {
    accept("image", { case s @ IMAGE(name, caption, path) => s })
  }

  private def section: Parser[SECTION] = {
    accept("section", { case s @ SECTION(name) => s })
  }
  private def subsection: Parser[SUBSECTION] = {
    accept("subsection", { case s @ SUBSECTION(name) => s })
  }
  private def layout: Parser[LAYOUT] = {
    accept("layout", { case l @ LAYOUT(layout) => l })
  }

}
