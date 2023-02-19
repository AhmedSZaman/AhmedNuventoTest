package controller
import model.Account
import model.AccountSaving
import model.ATMEngine
import view.ATMDisplay
object ATMContoller {
  val engineAtm = ATMEngine
  val displayATM = ATMDisplay
  def main(args: Array[String]): Unit = {


    engineAtm.intialise()
    try {
      displayATM.displayLogin()
      val userIDInput = scala.io.StdIn.readLine()
      engineAtm.userLogin(userIDInput)
      mainMenu()
    } catch{
      case e: Exception => println("Error Occurred: "+ e)
        main(args)
      }
  }
  def mainMenu(): Unit = {
    var isAppEnded: Boolean = false
    while (isAppEnded == false) {
      displayATM.displayMenu(engineAtm.currUser.getUserName())
      val userOptionInput = scala.io.StdIn.readInt()
      userOptionInput match
        case 1 => depositOption()
        case 2 => withdrawOption()
        case 3 => displayATM.displayBalance(engineAtm.checkBalance())
        case 4 =>
          engineAtm.quitApp()
          displayATM.displayEndApp()
          isAppEnded = true
    }
  }

  def depositOption(): Unit = {
    displayATM.displayAccountSelect("Deposit too", engineAtm.currAccCheque.getNumber(), engineAtm.currAccSaving.getNumber())
    val accSelectedInput = scala.io.StdIn.readInt()
    engineAtm.userInputAccCheck(accSelectedInput)
    displayATM.displayDeposit()
    val depositInput = scala.io.StdIn.readFloat()
    engineAtm.depositMoney(depositInput, accSelectedInput)
  }

  def withdrawOption(): Unit ={
    displayATM.displayAccountSelect("Withdraw from", engineAtm.currAccCheque.getNumber(), engineAtm.currAccSaving.getNumber())
    val accSelectedInput = scala.io.StdIn.readInt()
    engineAtm.userInputAccCheck(accSelectedInput)
    displayATM.displayWithdraw(engineAtm.getAccountTypeBalance(accSelectedInput))
    val withdrawInput = scala.io.StdIn.readFloat()
    engineAtm.withdrawMoney(withdrawInput, accSelectedInput)
  }
}


