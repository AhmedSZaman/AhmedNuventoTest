package controller
import model.Account
import model.AccountSaving
import model.ATMEngine
import view.ATMDisplay
object ATMContoller {

  def main(args: Array[String]): Unit = {
    val engineAtm = new ATMEngine
    val displayATM = new ATMDisplay
    try {
      engineAtm.initialise()
      displayATM.displayLogin()
      val userIDInput = scala.io.StdIn.readLine()
      engineAtm.userLogin(userIDInput)
      mainMenu(displayATM, engineAtm)
    } catch{
      case e: Exception => println("Error Occurred: "+ e)
        main(args)
      }
  }
  private def mainMenu(display: ATMDisplay, engine: ATMEngine): Unit = {
    var isAppEnded: Boolean = false
    while (!isAppEnded) {
      display.displayMenu(engine.currUser.getUserName())
      val userOptionInput = scala.io.StdIn.readInt()
      userOptionInput match
        case 1 => depositOption(display, engine)
        case 2 => withdrawOption(display, engine)
        case 3 => display.displayBalance(engine.checkBalance())
        case 4 =>
          engine.quitApp()
          display.displayEndApp(engine.dataToString())
          isAppEnded = true
    }
  }

  private def depositOption(display: ATMDisplay, engine: ATMEngine): Unit = {
    display.displayAccountSelect("Deposit too", engine.getAccountNumbers())
    val accSelectedInput = scala.io.StdIn.readInt()
    engine.userInputAccCheck(accSelectedInput)

    display.displayDeposit()
    val depositInput = scala.io.StdIn.readFloat()
    engine.depositMoney(depositInput, accSelectedInput)
  }

  private def withdrawOption(display: ATMDisplay, engine: ATMEngine): Unit ={
    display.displayAccountSelect("Withdraw from", engine.getAccountNumbers())
    val accSelectedInput = scala.io.StdIn.readInt()
    engine.userInputAccCheck(accSelectedInput)

    display.displayWithdraw(engine.getAccountTypeBalance(accSelectedInput))
    val withdrawInput = scala.io.StdIn.readFloat()
    engine.withdrawMoney(withdrawInput, accSelectedInput)
  }
}


