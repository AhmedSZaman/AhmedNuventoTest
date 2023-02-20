package model

class AccountSaving extends Account {
  override def accToString(): String = {
    val accString: String = s"${getOwnerID()} ||| ${getNumber()} ||| Savings ||| ${"%.2f".format(getBalance())}"
    return accString
  }
}
