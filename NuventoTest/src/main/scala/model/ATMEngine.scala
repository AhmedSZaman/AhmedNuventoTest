package model

import scala.collection.mutable.ArrayBuffer
import model.Account
import model.AccountSaving
import model.AccountCheque
import model.User

object ATMEngine {
  var accSavings = ArrayBuffer[AccountSaving]
  var accCheques = ArrayBuffer[AccountCheque]
  var currUser = new User
  var currAccSaving = new AccountSaving
  var currAccCheque = new AccountCheque

  def intialise(): Unit = {

  }

  def userLogin(): Unit = {

  }

  def depositMoney(userDepositInput: Int, userSelectedAccount: Int): Unit={

  }

  def getAccountTypeBalance(userSelectedAccount: Int):Int = {
    return 0
  }

  def withdrawMoney(userWithdrawInput: Int, userSelectedAccount: Int): Unit = {

  }

  def checkBalance():(Int,Int) = {
    return (2,2)
  }
}
