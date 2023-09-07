package models

import utils.IdGenerator

internal data class Customer (
    val id: Int = IdGenerator.generateCustomerID(),
    var firstName: String,
    var lastName: String,
    var email: String,
    val password: Int,
    val accounts: MutableList<Account> = mutableListOf()
){
    override fun toString(): String {
        return "\nCustomer ID: $id,\nFull Name: $firstName $lastName,\nEmail: $email,\nAccounts Linked: ${accounts.size}"
    }
}
