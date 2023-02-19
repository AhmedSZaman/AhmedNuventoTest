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

object ATMEngine {
  private val accSavings = ArrayBuffer[AccountSaving]()
  private val accCheques = ArrayBuffer[AccountCheque]()
  private val users = ArrayBuffer[User]()
  var currUser = new User
  var currAccSaving = new AccountSaving
  var currAccCheque = new AccountCheque

  def initialise(): Unit = {
    val openingAccData = Source.fromFile("src/main/resources/data/OpeningAccountsData.txt")
    for (line <- openingAccData.getLines()){

      val dataLine = line.split("\\|\\|\\|")
      if (dataLine(2).matches("Cheque")){
        val tempChequeAcc = new AccountCheque()
        val accChequeDetails = (dataLine(0),dataLine(1).toInt,dataLine(3).toFloat)
        tempChequeAcc.Account(accChequeDetails)
        accCheques.append(tempChequeAcc)
      }

      else{
        val tempSavingAcc = new AccountSaving()
        val accSavingDetails = (dataLine(0), dataLine(1).toInt, dataLine(3).toFloat)
        tempSavingAcc.Account(accSavingDetails)
        accSavings.append(tempSavingAcc)
      }
    }
    openingAccData.close()

    val openingUserData = Source.fromFile("src/main/resources/data/UserInfo.txt")
    for (line <- openingUserData.getLines()) {
      val userDataLine = line.split(",")
      val tempUser = new User()
      val userDetails = (userDataLine(0),userDataLine(1),userDataLine(2).toInt,userDataLine(3))
      tempUser.User(userDetails)
      users.append(tempUser)
    }
    openingUserData.close()
  }

  case class ErrorHandling(ErrorMsg: String) extends Exception(ErrorMsg){}

  def userLogin(userInput: String): Unit = {

    for(tempUser <- users.indices){
      if (users(tempUser).getOwnerID.matches(userInput)){
        currUser = users(tempUser)
      }
    }

    if(currUser.getOwnerID.matches("NULL")){
      throw ErrorHandling(s"UserID $userInput Aint found ma guy")
    }
    else{

      for(accSaving <- accSavings.indices){

        val accOwnerID = accSavings(accSaving).getOwnerID()
        if(currUser.getOwnerID.matches(accOwnerID)) currAccSaving = accSavings(accSaving)

      }
      if !currAccSaving.getOwnerID().matches(userInput) then currAccSaving.Account("NULL", -1, 0.00.toFloat)

      for (accCheque <- accCheques.indices) {
        val accOwneerID = accCheques(accCheque).getOwnerID()
        if (currUser.getOwnerID.matches(accOwneerID)) currAccCheque = accCheques(accCheque)

      }
      if !currAccCheque.getOwnerID().matches(userInput) then currAccCheque.Account("NULL",-1,0.00.toFloat)
    }
  }

  def depositMoney(userDepositInput: Float, userSelectedAccount: Int): Unit={
    if(userSelectedAccount == 1) currAccCheque.balance += userDepositInput
    else currAccSaving.balance += userDepositInput
  }

  def getAccountTypeBalance(userSelectedAccount: Int):Float = {
    var balanceReturned: Float = 0
    if(userSelectedAccount ==1) balanceReturned = currAccCheque.getBalance()
    else balanceReturned = currAccSaving.getBalance()

    return balanceReturned
  }

  def withdrawMoney(userWithdrawInput: Float, userSelectedAccount: Int): Unit = {
    val currBalance = getAccountTypeBalance(userSelectedAccount)

    if(currBalance >= userWithdrawInput){
      if(userSelectedAccount == 1) {
        val newBalance: Float = currAccCheque.getBalance() - userWithdrawInput
        currAccCheque.setBalance(newBalance)}
      else {
        val newBalance: Float = currAccSaving.getBalance() - userWithdrawInput
        currAccSaving.setBalance(newBalance)
      }
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

    if (currAccCheque.getOwnerID().matches("NULL")) chequeBalance = 0.00
    else chequeBalance = currAccCheque.getBalance()

    if (currAccSaving.getOwnerID().matches("NULL")) savingBalance = 0.00
    else savingBalance = currAccSaving.getBalance()

    return (chequeBalance, savingBalance)
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
    b2.close()

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
    superString
  }
}
