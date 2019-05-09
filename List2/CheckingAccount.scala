class BankAccount(initialBalance : Double) {
  private[this]var balance = initialBalance
  def checkBalance = balance
  def deposit(amount : Double) = { balance += amount; balance}
  def withdraw(amount : Double) = { balance -= amount; balance}
}

class CheckingAccount(initialBalance: Double) extends BankAccount(initialBalance) {
  override def deposit(amount: Double) = super.deposit(amount - 1.0)
  override def withdraw(amount: Double) = super.withdraw(amount + 1.0)
}

class SavingsAccount(initialBalance: Double) extends BankAccount(initialBalance) {
  private var transactions: Int = 0

  override def deposit(amount: Double) = {
    super.deposit(if (transactions > 3) amount - 1.0 else { transactions += 1; amount })
  }

  override def withdraw(amount: Double) = {
    super.withdraw(if (transactions > 3) amount + 1.0 else { transactions += 1; amount })
  }

  def earnMonthlyInterest(): Unit = {
    transactions = 0
    super.deposit(0.01 * checkBalance)
  }
}

object Main extends App {
  var checkingAccount = new CheckingAccount(10.0)
  var savingsAccount = new SavingsAccount(10.0)

  println(checkingAccount.deposit(2.0))
  println(checkingAccount.withdraw(4.0))

  1 to 4 foreach(_ => println(savingsAccount.deposit(1.0)))
  println({
    savingsAccount.earnMonthlyInterest()
    savingsAccount.checkBalance
  })
}