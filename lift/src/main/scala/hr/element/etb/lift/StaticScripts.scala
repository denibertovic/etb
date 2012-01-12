package hr.element.etb.lift.snippet

import scala.xml.NodeSeq
import net.liftweb.util.Props
import net.liftweb.http.S
import net.liftweb.common._

trait StaticScripts {
  val root = "static"
  val section = "js"

  def serveScript(typ: String, ver: String, min: String) = {
    val suffix = min match {
      case "all" =>
        ".min"
      case "prod" if Props.productionMode =>
        ".min"
      case _ =>
        ""
    }

    val path = "/%s/%s/%s%s%s.js" format (
      root,
      section,
      typ,
      ver,
      suffix
    )

    <script type="text/javascript" src={ path }/>
  }

  def render(n: NodeSeq): NodeSeq = {
    val ver = S.attr("version").map("-"+).openOr("")
    val min = S.attr("min").openOr("none")
    val typ = S.attr("type").ensuring(_.isDefined, "Scripts type attribute not specified!").open_!
    serveScript(typ, ver, min)
  }
}
