package controller
import model.Account
import model.AccountSaving
import model.ATMEngine
import view.ATMDisplay
object ATMController {

  //Any Exception thrown will restarted from main re-intialising everything, have to use quit option to end program
  //Using MVC Pattern where ATMEngine holds all calculation code, ATMDisplay holds all code to update the user
  //and ATMContoller gets the input from user and acts as the middle man between the two other classes.
  def main(args: Array[String]): Unit = {
    val engine = new ATMEngine
    val display = new ATMDisplay

    try {
      engine.initialise()

      display.displayLogin()
      val userIDInput = scala.io.StdIn.readLine()
      engine.userLogin(userIDInput)

      mainMenu(display, engine)
    } catch{
      case e: Exception => println("Error Occurred: "+ e)
        main(args)
      }
  }

  private def mainMenu(display: ATMDisplay, engine: ATMEngine): Unit = {

    var isAppEnded: Boolean = false
    while (!isAppEnded) {
      display.displayMenu(engine.currUser.getUserName())
      val userOptionInput = scala.io.StdIn.readLine()

      userOptionInput match
        case "1" => depositOption(display, engine)
        case "2" => withdrawOption(display, engine)
        case "3" => display.displayBalance(engine.checkBalance())
        case "q" =>
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


