package view

object ATMDisplay {

  def displayLogin(): Unit ={
    println( "Please enter User ID")
  }

  def displayMenu(userName: String): Unit = {
    println("Welcome " + userName + "Choose Option")
    println("Option1")
    println("Option2")
    println("Option3")
  }

  def displayAccountSelect(optionSelected: String, chequeNumber: Int, savingNumber: Int): Unit ={
    println("Which Account would you like to " + optionSelected)
    println("1 For " + chequeNumber + " (Cheque)")
    println("2 For " + savingNumber + " (Saving)")
  }

  def displayDeposit(): Unit= {
    println("Please enter money to deposit")
  }

  def displayWithdraw(accountBalance: Int): Unit = {
    println("How much do you wish to withdraw? Balance =" + accountBalance)
  }

  def displayBalance(accountBalance: (Int,Int)): Unit = {
    println("Cheque Balance = $" + accountBalance(0))
    println("Saving Balance = $" + accountBalance(1))
  }

  def displayError(errorMsg: String): Unit = {
    println("ERROR -" + errorMsg)
  }
  def displayEndApp():Unit = {
    // PRINT ALL ACCOUTNS AND USERS
  }


}
