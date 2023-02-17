package model

import scala.collection.mutable.ArrayBuffer
import model.Account
import model.AccountSaving
import model.AccountCheque
import model.User
import java.util.Scanner
import java.io._
import scala.io.Source

object ATMEngine {
  var accSavings = ArrayBuffer[AccountSaving]()
  var accCheques = ArrayBuffer[AccountCheque]()
  var currUser = new User
  var currAccSaving = new AccountSaving
  var currAccCheque = new AccountCheque

  def intialise(): Unit = {
    val openingAccData = Source.fromFile("src/main/resources/data/OpeningAccountsData.txt")
    for (line <- openingAccData.getLines()){
      val dataLine = line.split("\\|\\|\\|")
      println(dataLine(3).toFloat)
      if (dataLine(2).matches("Cheque")){
        val tempChequeAcc = new AccountCheque()
        val InputData = (dataLine(0),dataLine(1).toInt,dataLine(3).toFloat)
        tempChequeAcc.Account(InputData(0),InputData(1),InputData(2))
        accCheques.append(tempChequeAcc)
      }
      else{
        val tempSavingAcc = new AccountSaving()
        val InputData = (dataLine(0), dataLine(1).toInt, dataLine(3).toFloat)
        tempSavingAcc.Account(InputData(0), InputData(1), InputData(2))
        accSavings.append(tempSavingAcc)

      }

    }
    println(accCheques(1).getOwnerID())
    openingAccData.close()


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
