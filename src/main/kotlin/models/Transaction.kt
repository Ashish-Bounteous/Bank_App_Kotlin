package models

import utils.TransactionType

internal data class Transaction(
    val id: Int,
    val amount: Int,
    val type: TransactionType,
    val date: String,
    val balance: Double,
    val accountNo: Int
){
    override fun toString(): String {
        return "Transaction Id: $id, Date: $date, Type: $type, ${if(type === TransactionType.CREDITED)"From Account no: $accountNo, " else ""}Amount: $amount, Balance: $balance"
    }
}
