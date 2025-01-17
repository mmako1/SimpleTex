/* Document representation
 *
 * The document contains a set of layouts. Exposes a way to update a specific cell in a specific layout, which is used by the code generating functions. Eventually, the user calls `generateDocument` to generate the latex document.
 */
package simpletex.generator

import collection.mutable.Map
import collection.mutable.Queue

//TODO define layout class that has map: cell -> string
class Document(layout: List[Layout]) {
  override def toString = "Some document place holder"

  def update(layoutID: String, cellID: String, content: String): Unit = {
    layouts(layoutID)(cellID) = content
    accessQueue.addOne(layoutID)
  }
  def generateDocument(): Either[String, String] = { Left("not implemented") }

  private def processLayout(layout: String): Either[String, String] = {
    Left("not implemented")
  }
  private val accessQueue: Queue[String] = Queue()
  private val layouts: Map[String, Map[String, String]] = Map()
}
