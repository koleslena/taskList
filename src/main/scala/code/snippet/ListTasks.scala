package code.snippet

import java.text.SimpleDateFormat
import java.util.Date

import code.model.Task
import code.session.Config
import net.liftweb.common.Logger
import net.liftweb.http.js.JE.ValById
import net.liftweb.http.{RequestVar, S, SHtml}
import net.liftweb.http.js.{JsCmds, JsCmd}
import net.liftweb.util.Helpers._

import _root_.net.liftweb.http._
import util._
import _root_.scala.xml.{Text}

/**
  * Created by elenko on 05.03.16.
  */
object ListTasks extends Logger {
  def formatter = new SimpleDateFormat("dd.mm.yyyy")

  private object taskName extends RequestVar("")
  private object taskText extends RequestVar("")
  private object taskDate extends RequestVar("")

  def render = {
    val tasks = Config.getTasks

    def addTask(): JsCmd = {
      S.clearCurrentNotices
      parseDate(taskDate.get,
        tasks.addTask(Task(None, taskName.get, taskText.get, formatter.parse(taskDate.get))),
        S.error("date", "not valid date"))
      JsCmds.SetHtml("tlist", renderList.applyAgain())
    }

    def delTask(task: Task): JsCmd = {
      tasks.removeTask(task)
      JsCmds.SetHtml("tlist", renderList.applyAgain())
    }

    lazy val renderList =
      SHtml.idMemoize(
        tlist =>
          "tbody *" #> {
            "tr" #> tasks.list().map {
              task => {
                " @task_name *" #> Text(task.name) &
                  " @task_text *" #> Text(task.text) &
                  " @task_date *" #> Text(formatter.format(task.dateTo)) &
                " @task_del *" #> SHtml.a(() => delTask(task), Text("del this TODO"))
              }
            }
          }
      )

    def parseDate(x: String, suc: => Unit, fail: => Unit) = {
      Try (formatter.parse(x)) match {
        case Success(date) => suc
        case Failure(e) => fail
      }
    }

    def processDate(x: String) = {
      S.clearCurrentNotices
      parseDate(x, {}, S.error("date", "not valid date"))
      taskDate(x)
      JsCmds.Noop
    }

    "id=tlist" #> renderList &
    "name=name" #> SHtml.ajaxText("", (x:String) => {taskName(x); JsCmds.Noop}, "id" -> "name")  &
      "name=text" #> SHtml.ajaxText("", (x:String) => {taskText(x); JsCmds.Noop}, "id" -> "text")  &
      "name=date" #> SHtml.ajaxText("", (x:String) => processDate(x), "id" -> "date")  &
    "@add_task [onClick]" #> SHtml.ajaxCall(ValById("name"), (x:String) => addTask())

  }

}
