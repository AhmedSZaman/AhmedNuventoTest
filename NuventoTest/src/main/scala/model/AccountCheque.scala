package model

class AccountCheque extends Account {
  override def accToString(): String = {
    val accString: String = s"$ownerID ||| $accNumber ||| Cheque ||| ${"%.2f".format(balance)}"
    return accString
  }
}
