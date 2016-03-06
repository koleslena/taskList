package code.model

import java.text.SimpleDateFormat
import net.liftweb.common.Logger

/**
  * Created by elenko on 06.03.16.
  */
object SimpleTasks extends Logger with Tasks {
   def formatter = new SimpleDateFormat("dd.mm.yyyy")

  var tasks = List(Task(Some(0), "name1", "text1", formatter.parse("09.08.2016")))

  override def list(): List[Task] = tasks

  override def addTask(task: Task): Unit = {
    info("add " + task)
    task.id match {
      case Some(x) =>     synchronized(tasks = task::tasks)
      case None =>     synchronized(tasks = Task(Some(tasks.size), task.name, task.text, task.dateTo)::tasks)
    }
  }

  override def removeTask(task: Task): Unit = {
    info("del " + task)
    synchronized(
      tasks = tasks.filter(!_.id.equals(task.id))
    )
  }
}
