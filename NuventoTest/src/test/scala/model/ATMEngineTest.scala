package model
import org.junit._
import Assert._
import model.ATMEngine
class ATMEngineTest {
  val engine: ATMEngine = new ATMEngine
  @Test
  def testInitialise(): Unit={
    engine.initialise()
    engine.userLogin("001")
    engine.depositMoney(20.00, 1)
    engine.withdrawMoney(10.10, 2)
    engine.checkBalance()
    engine.quitApp()
  }
  @Test(expected = classOf[Exception])
  def testIncorrectUserLogin(): Unit={
    engine.initialise()
    engine.userLogin("006")
  }
  @Test
  def testNonExistentAccount(): Unit = {
    engine.initialise()
    engine.userLogin("003")
    engine.checkBalance()
  }
@Test(expected = classOf[Exception])
  def testIncorrectAccount(): Unit = {
    engine.initialise()
    engine.userLogin("001")
    engine.withdrawMoney(20.00,0)
  }
  @Test(expected = classOf[Exception])
  def testWithdrawnOverLimit(): Unit = {
    engine.initialise()
    engine.userLogin("001")
    engine.withdrawMoney(10000.00,1)
  }
}
