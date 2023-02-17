package controller
import model.Account
import model.AccountSaving
object ATMContoller {
  def main(args: Array[String]): Unit = {
    println("Hello World!")
    var acc = new Account
    acc.Account(1,1,1.00)
    println(acc.getOwnerID())
  }
}
