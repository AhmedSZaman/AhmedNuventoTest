package Model

class Account {
  var ownerID: Int = -1
  var accNumber: Int = -1
  var balance: Float = -1.00

  def Account(newOwnerID: Int, newAccNumber: Int, newBalance: Float): Unit = {
    ownerID = newOwnerID
    accNumber = newAccNumber
    balance = newBalance
  }

  def getOwnerID(): Int = {
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
