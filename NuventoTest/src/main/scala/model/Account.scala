package model

class  Account {
  private var ownerID: String = "NULL"
  private var accNumber: String = "ERROR"
  private var balance: Float = 0.00

  def Account(accountDetails: (String, String, Float)): Unit = {
    this.ownerID = accountDetails(0)
    this.accNumber = accountDetails(1)
    this.balance = accountDetails(2)
  }

  def getOwnerID(): String = {
    return ownerID
  }

  def getNumber(): String = {
    return accNumber
  }

  def getBalance(): Float = {
    return balance
  }

  def setBalance(newBalance: Float): Unit = {
    balance = newBalance
  }

  //Gets overriden in child objects
  def accToString(): String = {
    return "account details not found"
  }

}
