package model

import org.junit._
import Assert._
import model.User
class UserTest {
  var user: User = new User
  user.User( ("Daniel", "Riccardo", "04123456", "232"))
  @Test
  def testGetUserName(): Unit = {
    val expected: String="Daniel Riccardo"
    val actual: String = user.getUserName()
    assertEquals(expected, actual)
  }

  @Test
  def testGetOwnerID(): Unit = {
    val expected: String = "232"
    val actual: String = user.getOwnerID
    assertEquals(expected, actual)
  }

  @Test
  def testUserToString(): Unit = {
    val expected: String = "Daniel,Riccardo,04123456,232"
    val actual: String = user.userToString()
    assertEquals(expected, actual)
  }
}



