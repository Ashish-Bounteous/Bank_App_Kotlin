package repositories

import customExceptions.AccountNotFoundException
import customExceptions.CustomerNotFoundException
import models.Account
import models.Bank
import models.Customer

internal class CustomerRepositories {

    @Throws(IllegalArgumentException::class)
    fun create(fName: String, lName: String, email: String, pwd: Int): Customer{

        require((fName.trim()+lName.trim()).length >= 6){"Name should more than 5 characters"}
        require(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$").containsMatchIn(email)){"Invalid Email"}
        require(pwd in 1000..9999){"Password must 4 Digit"}

        return Customer(firstName = fName, lastName = lName, email = email, password = pwd)
    }
    //==========================================================================================================

    @Throws(CustomerNotFoundException::class)
    fun find(email: String, customers: MutableList<Customer>): Customer {
        return customers.find { it.email == email}
                            ?: throw CustomerNotFoundException()
    }
}
