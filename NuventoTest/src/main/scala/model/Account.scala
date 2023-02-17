package model

class  Account {
  var ownerID: String = "Null"
  var accNumber: Int = -1
  var balance: Float = -1.00

  def Account(newOwnerID: String, newAccNumber: Int, newBalance: Float): Unit = {
    this.ownerID = newOwnerID
    this.accNumber = newAccNumber
    this.balance = newBalance
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

}
