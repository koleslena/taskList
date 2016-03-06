package code.model

import java.util.Date

/**
  * Created by elenko on 06.03.16.
  */
case class Task(id: Option[BigInt], name:String, text: String, dateTo: Date)

trait Tasks {
  def list():List[Task]

  def addTask(task: Task)

  def removeTask(task: Task)

}
