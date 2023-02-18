package model

class AccountSaving extends Account {
  override def accToString(): String = {
    var accString: String = (s"$ownerID ||| $accNumber ||| Savings ||| $balance")
    return accString
  }
}
