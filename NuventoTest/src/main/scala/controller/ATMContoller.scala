package controller
import model.Account
import model.AccountSaving
import model.ATMEngine
object ATMContoller {
  def main(args: Array[String]): Unit = {
    println("Hello World!")
    var acc = new AccountSaving
    acc.Account("01",1,1.00)
    println(acc.getOwnerID())
    var engineAtm = ATMEngine


    engineAtm.intialise()
    engineAtm.userLogin("001")
    engineAtm.getAccountTypeBalance(4)
    engineAtm.quitApp()
  }
}
