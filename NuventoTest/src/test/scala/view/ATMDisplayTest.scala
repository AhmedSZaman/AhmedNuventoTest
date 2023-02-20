package view
import org.junit._
import Assert._
import model.ATMEngine
class ATMDisplayTest {
  var display: ATMDisplay = new ATMDisplay

  @Test
  def testDisplay(): Unit = {
    display.displayLogin()
    display.displayMenu("Daniel Riccardo")
    display.displayAccountSelect("Withdraw From", ("123312","76574"))
    display.displayDeposit()
    display.displayWithdraw(500.00)
    display.displayBalance((400,200))
    display.displayEndApp("TEST DATA")
  }
}
