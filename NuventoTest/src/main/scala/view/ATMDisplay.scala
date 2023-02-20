package view

class ATMDisplay {

  def displayLogin(): Unit ={
    println( "Please enter User ID")
  }

  def displayMenu(userName: String): Unit = {
    println("Welcome " + userName + ", Choose Option")
    println("1 - Deposit Money")
    println("2 - Withdraw Money")
    println("3 - Check Balance")
    println("4 - Quit App")
  }

  def displayAccountSelect(optionSelected: String, accoutNumber: (Int, Int)): Unit ={
    println("Which Account would you like to " + optionSelected)
    println("1 For " + accoutNumber(0) + " (Cheque)")
    println("2 For " + accoutNumber(1) + " (Saving)")
  }

  def displayDeposit(): Unit= {
    println("Please enter money to deposit")
  }

  def displayWithdraw(accountBalance: Float): Unit = {
    println("How much do you wish to withdraw? Balance =" + accountBalance)
  }

  def displayBalance(accountBalance: (Float,Float)): Unit = {
    println("Cheque Balance = $" + accountBalance(0))
    println("Saving Balance = $" + accountBalance(1))
  }

  def displayEndApp(atmData: String):Unit = {
    println(atmData)
  }




}
