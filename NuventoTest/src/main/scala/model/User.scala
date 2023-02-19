package model

class User{
  private var firstName: String = "NULL"
  private var lastName: String = "NULL"
  private var ownerID: String = "NULL"
  private var mobile: Int = -1

  def User(firstName: String, lastName: String, mobile: Int, ownerID: String) ={
    this.firstName = firstName
    this.lastName = lastName
    this.ownerID = ownerID
    this.mobile = mobile
  }


  def getUserName(): String = {
    var userName: String = s"$firstName $lastName"
    return userName
  }

  def getOwnerID: String ={
    return ownerID
  }
  def getMobile: Int={
    return mobile
  }

  def userToString(): String = {
    var userString: String = (s"$firstName,$lastName,$ownerID,$mobile")
    return userString
  }

}
