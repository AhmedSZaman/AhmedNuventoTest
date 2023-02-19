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

  def intialise(): Unit = {
    val openingAccData = Source.fromFile("src/main/resources/data/OpeningAccountsData.txt")
    for (line <- openingAccData.getLines()){
      val dataLine = line.split("\\|\\|\\|")
      val tempAccount = new Account()
      if (dataLine(2).matches("Cheque")){
        val tempChequeAcc = new AccountCheque()
        //case class chequeData (ownerID: String, AccNumber: Int, Balance: Float)
        //val tempChequeData = (dataLine(0),dataLine(1).toInt,dataLine(3).toFloat)
        tempChequeAcc.Account(dataLine(0),dataLine(1).toInt,dataLine(3).toFloat)
        accCheques.append(tempChequeAcc)
      }
      else{
        val tempSavingAcc = new AccountSaving()
        case class savingsData (ownerID: String, AccNumber: Int, Balance: Float)
        val tempSavingsData = savingsData(dataLine(0), dataLine(1).toInt, dataLine(3).toFloat)
        tempSavingAcc.Account(dataLine(0), dataLine(1).toInt, dataLine(3).toFloat)
        accSavings.append(tempSavingAcc)

      }

    }
    openingAccData.close()
    val openingUserData = Source.fromFile("src/main/resources/data/UserInfo.txt")
    for (line <- openingUserData.getLines()) {
      val userDataLine = line.split(",")
      val tempUser = new User()
      tempUser.User(userDataLine(0),userDataLine(1),userDataLine(2).toInt,userDataLine(3))
      users.append(tempUser)

    }

  }

  case class ErrorHandling(ErrorMsg: String) extends Exception(ErrorMsg){}
  def userLogin(userInput: String): Unit = {
    for(tempUser <- 0 until users.length){
      if (users(tempUser).getOwnerID.matches((userInput))){
        currUser = users(tempUser)
      }
    }
    if(currUser.getOwnerID.matches("NULL")){
      throw new ErrorHandling(s"UserID $userInput Aint found ma guy")
    }
    else{
      for(accSaving <- 0 until accSavings.length){
        val accOwneerID = accSavings(accSaving).getOwnerID()
        if(currUser.getOwnerID.matches(accOwneerID)) currAccSaving = accSavings(accSaving)
        else currAccSaving.Account("NULL",-1,0.00)
      }
      for (accCheque <- 0 until accCheques.length) {
        val accOwneerID = accCheques(accCheque).getOwnerID()
        if (currUser.getOwnerID.matches(accOwneerID)) currAccCheque = accCheques(accCheque)
        else currAccCheque.Account("NULL",-1,0.00)

      }
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
    else throw new ErrorHandling(s"Amount entered $userSelectedAccount is greater than amount in the account $currBalance") //CHANGE
  }

  def userInputAccCheck(userSelectedAccount: Int): Unit={
    if (userSelectedAccount != 1 && userSelectedAccount != 2 )
      {throw new ErrorHandling(s"Account $userSelectedAccount is an invalid option")}
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
    val b = new BufferedWriter(f);
    for (user: User<- users){
      f.write(user.userToString() + "\n")
    }
    b.close()
    f.close()

    val f2 = new FileWriter("outputFile2.txt");
    val b2 = new BufferedWriter(f2);
    for (savingAcc: AccountSaving <- accSavings) {
      f2.write(savingAcc.accToString() + "\n")
    }
    for (chequeAcc: AccountCheque <- accCheques) {
      b2.write(chequeAcc.accToString() + "\n")
    }
    b2.close()
    b2.close()

  }
}
