package model

class  Account {
  private var ownerID: String = "Null"
  private var accNumber: Int = -1
  private var balance: Float = -1.00

  def Account(accountDetails: (String, Int, Float)): Unit = {
    this.ownerID = accountDetails(0)
    this.accNumber = accountDetails(1)
    this.balance = accountDetails(2)
  }

  def getOwnerID(): String = {
    return ownerID
  }

  def getNumber(): Int = {
    return accNumber
  }

  def getBalance(): Float = {
    return balance
  }

  def setBalance(newBalance: Float): Unit = {
    balance = newBalance
  }

  def accToString(): String = {
    return "account details not found"
  }

}
