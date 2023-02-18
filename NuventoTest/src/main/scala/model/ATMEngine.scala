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
  var accSavings = ArrayBuffer[AccountSaving]()
  var accCheques = ArrayBuffer[AccountCheque]()
  var users = ArrayBuffer[User]()
  var currUser = new User
  var currAccSaving = new AccountSaving
  var currAccCheque = new AccountCheque

  def intialise(): Unit = {
    val openingAccData = Source.fromFile("src/main/resources/data/OpeningAccountsData.txt")
    for (line <- openingAccData.getLines()){
      val dataLine = line.split("\\|\\|\\|")
      println(dataLine(3).toFloat)
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
    println(accCheques(1).getOwnerID())
    openingAccData.close()
    val openingUserData = Source.fromFile("src/main/resources/data/UserInfo.txt")
    for (line <- openingUserData.getLines()) {
      val userDataLine = line.split(",")
      val tempUser = new User()
      tempUser.User(userDataLine(0),userDataLine(1),userDataLine(2).toInt,userDataLine(3))
      users.append(tempUser)
      println("users length" + users.length)

    }

  }

  def userLogin(userInput: String): Unit = {
    for(tempUser <- 0 until users.length){
      if (users(tempUser).getOwnerID.matches((userInput))){
        currUser = users(tempUser)
        println(currUser.getOwnerID)
      }
    }
    if(currUser.getOwnerID.matches("NULL")){
      //THROW ERROR
    }
    else{
      for(accSaving <- 0 until accSavings.length){
        val accOwneerID = accSavings(accSaving).getOwnerID()
        if(currUser.getOwnerID.matches(accOwneerID)){
          currAccSaving = accSavings(accSaving)
        }
      }
      for (accCheque <- 0 until accCheques.length) {
        val accOwneerID = accCheques(accCheque).getOwnerID()
        if (currUser.getOwnerID.matches(accOwneerID)) {
          currAccCheque = accCheques(accCheque)
        }
      }
    }
  }

  def depositMoney(userDepositInput: Int, userSelectedAccount: Int): Unit={
    if(userSelectedAccount == 1) currAccCheque.balance += userDepositInput
    else if (userSelectedAccount == 2) currAccSaving.balance += userDepositInput
    else println("ERROR") //CHANGE THIS
  }

  def getAccountTypeBalance(userSelectedAccount: Int):Float = {
    var balanceReturned: Float = 0
    if(userSelectedAccount ==1) balanceReturned = currAccCheque.getBalance()
    else if (userSelectedAccount == 2) balanceReturned = currAccSaving.getBalance()
    else println("ERROR")//FIX DIS

    return balanceReturned
  }

  def withdrawMoney(userWithdrawInput: Float, userSelectedAccount: Int): Unit = {
    var currBalance = getAccountTypeBalance(userSelectedAccount)

    if(currBalance >= userWithdrawInput){
      if(userSelectedAccount == 1) {
        val newBalance: Float = currAccCheque.getBalance() - userWithdrawInput
        currAccCheque.setBalance(newBalance)}
      else {
        val newBalance: Float = currAccSaving.getBalance() - userWithdrawInput
        currAccSaving.setBalance(newBalance)
      }
    }
    else println("NOT ENUFF MONIES") //CHANGE
  }

  def checkBalance():(Float,Float) = {
    return (currAccCheque.getBalance(), currAccSaving.getBalance())
  }

  def quitApp(): Unit = {
    val f = new FileWriter("outputFile.txt", true);
    val b = new BufferedWriter(f);

    for (user: User<- users){
      println(user.userToString())
    }
    for (savingAcc: AccountSaving <- accSavings) {
      println(savingAcc.accToString())
    }
    for (chequeAcc: AccountCheque <- accCheques) {
      println(chequeAcc.accToString())
    }
    b.write("LOL")
    b.close()

  }
}
