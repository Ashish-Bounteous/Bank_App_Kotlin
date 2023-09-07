package dao

import models.Account
import models.Bank
import models.Customer

internal class AppData {
    val banks: Array<Bank> = arrayOf(
        Bank(name = "Axis Bank"),
        Bank(name = "Indian Bank"),
        Bank(name = "HDFC Bank")
    )
    val customers: MutableList<Customer> = mutableListOf()

    private var currentCustomer: Customer? = null
    private var currentBank: Bank? = null
    private var currentAccount: Account? = null

    companion object{
        private val appData = AppData();
        fun getAppData() = appData;
    }

    fun getCurrentCustomer() = currentCustomer
    fun getCurrentAccount() = currentAccount
    fun getCurrentBank() = currentBank

    fun setCurrentCustomer(customer: Customer?) {
        currentCustomer = customer
    }
    fun setCurrentBank(bank: Bank?) {
        currentBank = bank
    }
    fun setCurrentAccount(account: Account?) {
        currentAccount = account
    }
}