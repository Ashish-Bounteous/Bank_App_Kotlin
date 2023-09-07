package models

import utils.IdGenerator

internal data class Account (
    val id: Int = IdGenerator.generateAccountID(),
    var balance: Double,
    val pin: Int,
    val customerDetails: Customer,
    val transactions: MutableSet<Transaction> = mutableSetOf()
){
    override fun toString(): String {
        return """
            Account no: $id,
            Full Name: ${customerDetails.firstName + " " + customerDetails.lastName},
            Balance: $balance,
            Total Transactions: ${transactions.size}
        """.trimIndent()
    }
}