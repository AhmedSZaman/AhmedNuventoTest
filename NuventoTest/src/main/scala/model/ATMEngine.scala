package model

import scala.collection.mutable.ArrayBuffer
import model.Account
import model.AccountSaving
import model.AccountCheque
import model.User

import java.util.Scanner
import java.io.*
import scala.io.Source
import scala.language.postfixOps

class ATMEngine {
  private val accSavings = new ArrayBuffer[AccountSaving]()
  private val accCheques = new ArrayBuffer[AccountCheque]()
  private val users = ArrayBuffer[User]()
  var currUser = new User
  var currAccSaving = new AccountSaving
  var currAccCheque = new AccountCheque

  private val accountArray = new Array[Account](2)
  case class ErrorHandling(ErrorMsg: String) extends Exception(ErrorMsg){}
  def initialise(): Unit = {
    loadAccounts()
    loadUsers()
  }

  private def loadAccounts(): Unit={
    val openingAccData = Source.fromFile("src/main/resources/data/OpeningAccountsData.txt")
    for (line <- openingAccData.getLines()) {
      val dataLine = line.split("\\|\\|\\|")
      val accDetails = (dataLine(0), dataLine(1).toInt, dataLine(3).toFloat)

      if (dataLine(2).matches("Cheque")) {
        val tempChequeAcc = new AccountCheque()
        tempChequeAcc.Account(accDetails)
        accCheques.append(tempChequeAcc)
      }
      else {
        val tempSavingAcc = new AccountSaving()
        tempSavingAcc.Account(accDetails)
        accSavings.append(tempSavingAcc)
      }
    }
    openingAccData.close()

  }

  private def loadUsers(): Unit ={
    val openingUserData = Source.fromFile("src/main/resources/data/UserInfo.txt")
    for (line <- openingUserData.getLines()) {
      val userDataLine = line.split(",")
      val userDetails = (userDataLine(0), userDataLine(1), userDataLine(2).toInt, userDataLine(3))

      val tempUser = new User()
      tempUser.User(userDetails)
      users.append(tempUser)
    }
    openingUserData.close()

  }

  def userLogin(userInput: String): Unit = {

    for(tempUser <- users.indices){
      if (users(tempUser).getOwnerID.matches(userInput)){
        currUser = users(tempUser)
      }
    }

    if(currUser.getOwnerID.matches("NULL")) throw ErrorHandling(s"UserID $userInput Not found")
    else{
      for (accCheque <- accCheques.indices) {
        val accOwnerID = accCheques(accCheque).getOwnerID()
        if (currUser.getOwnerID.matches(accOwnerID)) {
          currAccCheque = accCheques(accCheque)
          accountArray(0) = currAccCheque
        }
      }
      if !currAccCheque.getOwnerID().matches(userInput) then currAccCheque.Account("NULL",-1,0.00.toFloat)

      for (accSaving <- accSavings.indices) {
        val accOwnerID = accSavings(accSaving).getOwnerID()
        if (currUser.getOwnerID.matches(accOwnerID)) {
          currAccSaving = accSavings(accSaving)
          accountArray(1) = currAccSaving
        }
      }
      if !currAccSaving.getOwnerID().matches(userInput) then currAccSaving.Account("NULL", -1, 0.00.toFloat)}
  }

  private def getAccountType(selectedAccount: Int): Account = {
    return accountArray(selectedAccount - 1)
  }

  def depositMoney(userDepositInput: Float, userSelectedAccount: Int): Unit={
/*    if(userSelectedAccount == 1) {
      val newBalance = currAccCheque.getBalance() + userDepositInput
      currAccCheque.setBalance(newBalance)
    }
    else {
      val newBalance = currAccSaving.getBalance() + userDepositInput
      currAccSaving.setBalance(newBalance)

    }*/
    val accountInUse = getAccountType(userSelectedAccount)
    val newBalance = accountInUse.getBalance() + userDepositInput
    accountInUse.setBalance(newBalance)
  }

  def getAccountTypeBalance(userSelectedAccount: Int):Float = {
    val accountInUse = getAccountType(userSelectedAccount)
    return accountInUse.getBalance()
  }

  def withdrawMoney(userWithdrawInput: Float, userSelectedAccount: Int): Unit = {
    val currBalance = getAccountTypeBalance(userSelectedAccount)

    if(currBalance >= userWithdrawInput){
      /*if(userSelectedAccount == 1) {
        val newBalance: Float = currAccCheque.getBalance() - userWithdrawInput
        currAccCheque.setBalance(newBalance)}
      else {
        val newBalance: Float = currAccSaving.getBalance() - userWithdrawInput
        currAccSaving.setBalance(newBalance)
      }*/
      val accountInUse = getAccountType(userSelectedAccount)
      val newBalance: Float = accountInUse.getBalance() - userWithdrawInput
      accountInUse.setBalance(newBalance)

    }
    else throw ErrorHandling(s"Amount entered $userSelectedAccount is greater than amount in the account $currBalance") //CHANGE
  }

  def userInputAccCheck(userSelectedAccount: Int): Unit={
    if (userSelectedAccount != 1 && userSelectedAccount != 2 )
      {throw ErrorHandling(s"Account $userSelectedAccount is an invalid option")}
  }

  def checkBalance():(Float,Float) = {
    var chequeBalance: Float = 0.00
    var savingBalance: Float = 0.00
    var accountBalance: Float = 0.00
    var balanceArray = new Array[Float](2)

/*
    if (currAccCheque.getOwnerID().matches("NULL")) chequeBalance = 0.00
    else chequeBalance = currAccCheque.getBalance()

    if (currAccSaving.getOwnerID().matches("NULL")) savingBalance = 0.00
    else savingBalance = currAccSaving.getBalance()
*/
    for ((account, i) <- accountArray.zipWithIndex){
      if (account.getOwnerID().matches("NULL")) balanceArray(i) = 0.00
      else balanceArray(i) = account.getBalance()
    }
    return (balanceArray(0), balanceArray(1))
  }

  def quitApp(): Unit = {
    val f = new FileWriter("outputFile1.txt")
    val b = new BufferedWriter(f)
    for (user: User<- users){
      f.write(user.userToString() + "\n")
    }
    b.close()
    f.close()

    val f2 = new FileWriter("outputFile2.txt")
    val b2 = new BufferedWriter(f2)

    for (savingAcc: AccountSaving <- accSavings) {
      f2.write(savingAcc.accToString() + "\n")
    }
    for (chequeAcc: AccountCheque <- accCheques) {
      b2.write(chequeAcc.accToString() + "\n")
    }
    b2.close()
    f2.close()

  }

  def dataToString (): String = {
    var superString: String = ""
    for (user: User <- users) {
      superString += (user.userToString() + "\n")
    }
    for (savingAcc: AccountSaving <- accSavings) {
      superString += (savingAcc.accToString() + "\n")
    }
    for (chequeAcc: AccountCheque <- accCheques) {
      superString += (chequeAcc.accToString() + "\n")
    }
    return superString
  }

  def getAccountNumbers(): (Int,Int) ={
    return (accountArray(0).getNumber(), accountArray(1).getNumber())
  }
}
