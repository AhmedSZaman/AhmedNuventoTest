package model

class AccountCheque extends Account {
  override def accToString(): String = {
    var accString: String = (s"$ownerID ||| $accNumber ||| Cheque ||| $balance")
    return accString
  }
}
