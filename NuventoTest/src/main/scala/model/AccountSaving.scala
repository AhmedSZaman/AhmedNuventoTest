package model

class AccountSaving extends Account {
  override def accToString(): String = {
    val accString: String = s"$ownerID ||| $accNumber ||| Savings ||| ${"%.2f".format(balance)}"
    return accString
  }
}
