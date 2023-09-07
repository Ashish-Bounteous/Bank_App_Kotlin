package utils

internal object IdGenerator {
    private var bankIdCounter = 1
    private var customerIdCounter = 1
    private var accountIdCounter = 1

    fun generateBankID() = bankIdCounter++
    fun generateCustomerID() = customerIdCounter++
    fun generateAccountID() = accountIdCounter++
}