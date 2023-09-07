package repositories

import models.Account
import models.Transaction
import utils.TransactionType

internal class TransactionRepositories {

    fun create(
        transactionAmount: Int,
        transactionType: TransactionType,
        dateOfTransaction: String,
        accountBalance: Double,
        accountNo: Int,
        account: Account
    ) = Transaction(findAll(account).size, transactionAmount, transactionType, dateOfTransaction, accountBalance, accountNo)

    //========================================================================================================================
    fun findAll(account: Account) = account.transactions
}