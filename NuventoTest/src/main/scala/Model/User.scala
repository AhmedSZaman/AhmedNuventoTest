package Model

object User {
  var firstName: String = "NULL"
  var lastName: String = "NULL"
  var ownerID: Int = -1
  var mobile: Int = -1

  def User(newfirstName: String,newlastName: String, 
           newaccountOwnerID: Int, newMobile: Int) ={
    firstName = newfirstName
    lastName = newlastName
    ownerID = newaccountOwnerID
    mobile = newMobile
  }


  def getUserName(): String = {
    var userName: String = s"$firstName  $lastName"
    return userName
  }
  
  def getAccountOwnerID: Int ={
    return ownerID
  }
  def getMobile: Int={
    return mobile
  }
  

}
