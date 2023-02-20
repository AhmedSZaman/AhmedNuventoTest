package model

class AccountCheque extends Account {
  override def accToString(): String = {
    val accString: String = s"${getOwnerID()} ||| ${getNumber()} ||| Cheque ||| ${"%.2f".format(getBalance())}"
    return accString
  }
}
