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

//ATM Engine hold all code regarding calculations and object manipulation
class ATMEngine {
  private val accSavings = new ArrayBuffer[AccountSaving]()
  private val accCheques = new ArrayBuffer[AccountCheque]()
  private val users = ArrayBuffer[User]()
  var currUser = new User
  private val accountsInUseArray = new Array[Account](2)

  case class ErrorHandling(ErrorMsg: String) extends Exception(ErrorMsg){}
  def initialise(): Unit = {
    loadAccounts()
    loadUsers()
  }

  private def loadAccounts(): Unit={
    val openingAccData = Source.fromFile("src/main/resources/data/OpeningAccountsData.txt")
    for ((line,lineNumber) <- openingAccData.getLines().zipWithIndex) {
      if (lineNumber != 0) {
        val dataLine = line.split("\\|\\|\\|")
        //ACCOUNT = (OwnerID, AccountNumber, AccountType, AccountBalance)
        val accDetails = (dataLine(0), dataLine(1), dataLine(3).toFloat)

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
    }
    openingAccData.close()

  }

  private def loadUsers(): Unit ={
    val openingUserData = Source.fromFile("src/main/resources/data/UserInfo.txt")
    for ((line, lineNumber) <- openingUserData.getLines().zipWithIndex) {
      if (lineNumber != 0) {
        val userDataLine = line.split(",")
        //USER = (firstname, lastname, MobileNumber, OwnerID)
        val userDetails = (userDataLine(0), userDataLine(1), userDataLine(2), userDataLine(3))

        val tempUser = new User()
        tempUser.User(userDetails)
        users.append(tempUser)
      }
    }
    openingUserData.close()
  }

  def userLogin(userInput: String): Unit = {
    for(tempUser <- users.indices){
      if (users(tempUser).getOwnerID.matches(userInput)) currUser = users(tempUser)
    }
    if(currUser.getOwnerID.matches("NULL")) throw ErrorHandling(s"UserID $userInput Not found")
    else setActiveAccounts(userInput)
  }

  //Sets the accounts of the logged in User for easy access
  private def setActiveAccounts(userInput: String): Unit={
    var currAccSaving = new AccountSaving
    var currAccCheque = new AccountCheque

    for (accCheque <- accCheques.indices) {
      val accOwnerID = accCheques(accCheque).getOwnerID()
      if (currUser.getOwnerID.matches(accOwnerID)) currAccCheque = accCheques(accCheque)
    }

    for (accSaving <- accSavings.indices) {
      val accOwnerID = accSavings(accSaving).getOwnerID()
      if (currUser.getOwnerID.matches(accOwnerID)) currAccSaving = accSavings(accSaving)
    }

    accountsInUseArray(0) = currAccCheque
    accountsInUseArray(1) = currAccSaving
  }
  private def getAccountInUse(userSelectedAccount: Int): Account = {
    val accountInUse = accountsInUseArray(userSelectedAccount - 1)
    accountInUse
  }

  def depositMoney(userDepositInput: Float, userSelectedAccount: Int): Unit={
    val accountInUse = getAccountInUse(userSelectedAccount)
    val newBalance = accountInUse.getBalance() + userDepositInput
    accountInUse.setBalance(newBalance)
  }

  def getAccountTypeBalance(userSelectedAccount: Int):Float = {
    val accountInUse = getAccountInUse(userSelectedAccount)
    return accountInUse.getBalance()
  }

  def withdrawMoney(userWithdrawInput: Float, userSelectedAccount: Int): Unit = {
    val currBalance = getAccountTypeBalance(userSelectedAccount)

    if(currBalance >= userWithdrawInput){
      val accountInUse = getAccountInUse(userSelectedAccount)
      val newBalance: Float = accountInUse.getBalance() - userWithdrawInput
      accountInUse.setBalance(newBalance)
    }
    else throw ErrorHandling(s"Amount entered $userWithdrawInput is greater than amount in the account $currBalance") //CHANGE
  }

  def userInputAccCheck(userSelectedAccount: Int): Boolean={
    val accountInUse = getAccountInUse(userSelectedAccount)
    if (accountInUse.getOwnerID().matches("NULL")) throw ErrorHandling(s"Account $userSelectedAccount Not found")
    else true
  }

  def checkBalance():(Float,Float) = {
    val balanceArray = new Array[Float](2)
    for ((account, i) <- accountsInUseArray.zipWithIndex){
      balanceArray(i) = account.getBalance()
    }
    return (balanceArray(0), balanceArray(1))
  }
  def getAccountNumbers(): (String,String) ={
    return (accountsInUseArray(0).getNumber(), accountsInUseArray(1).getNumber())
  }

  def dataToString(): String = {
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
  
  //Loads final data back into files
  def quitApp(): Unit = {
    val userFile = new FileWriter("src/main/resources/data/UserInfo.txt")
    userFile.write("FirstName,Surname,Mobile,AccountOwnerID\n")
    for (user: User <- users) {
      userFile.write(user.userToString() + "\n")
    }
    userFile.close()

    val accountsFile = new FileWriter("src/main/resources/data/OpeningAccountsData.txt")
    accountsFile.write("AccountOwnerID|||AccountNumber|||AccountType|||OpeningBalance\n")
    for (savingAcc: AccountSaving <- accSavings) {
      accountsFile.write(savingAcc.accToString() + "\n")
    }
    for (chequeAcc: AccountCheque <- accCheques) {
      accountsFile.write(chequeAcc.accToString() + "\n")
    }
    accountsFile.close()

  }
}
