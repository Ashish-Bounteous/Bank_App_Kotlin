package repositories

import customExceptions.InvalidPinException
import customExceptions.NegativeAmountException
import models.Account
import models.Customer

internal class AccountRepositories {

    @Throws(InvalidPinException::class, NegativeAmountException::class)
    fun create(balance: Double, pin: Int, customerDetails: Customer): Account {
        if(balance < 0) throw NegativeAmountException()
        if (pin !in 1000..9999)throw InvalidPinException()

        return Account(
            balance = balance, pin = pin, customerDetails = customerDetails
        )
    }


}