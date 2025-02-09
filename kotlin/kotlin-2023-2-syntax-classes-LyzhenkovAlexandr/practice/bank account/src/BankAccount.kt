class BankAccount {
    var balance: Int
        private set(value) {
            logTransaction(field, value)
            field = value
        }

    constructor(amount: Int) {
        require(amount > 0) { "The client's initial amount must be positive" }
        this.balance = amount
    }

    fun deposit(amount: Int) {
        require(amount > 0) { "The deposit must be positive" }
        balance += amount
    }

    fun withdraw(amount: Int) {
        require(amount > 0) { "The amount to withdraw must be positive" }
        require(balance >= amount) { "The withdrawal amount is greater than the invoice amount" }
        balance -= amount
    }
}
