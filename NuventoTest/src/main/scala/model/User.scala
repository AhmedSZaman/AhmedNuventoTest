package model

class User{
  private var firstName: String = "NULL"
  private var lastName: String = "NULL"
  private var mobile: Int = -1
  private var ownerID: String = "NULL"
  
  
  def User(userDetails: (String, String, Int, String)): Unit ={
    this.firstName = userDetails(0)
    this.lastName = userDetails(1)
    this.mobile = userDetails(2)
    this.ownerID = userDetails(3)
    
  }


  def getUserName(): String = {
    val userName: String = s"$firstName $lastName"
    return userName
  }

  def getOwnerID: String ={
    return ownerID
  }

  def userToString(): String = {
    val userString: String = s"$firstName,$lastName,$ownerID,$mobile"
    return userString
  }

}
