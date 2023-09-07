package repositories

import customExceptions.AccountNotFoundException
import customExceptions.InSufficientBalanceException
import customExceptions.InvalidPinException
import customExceptions.NegativeAmountException
import models.Account
import models.Bank

internal class BankRepositories {

    fun addAccount(newAccount: Account, bank: Bank) = bank.accounts.put(newAccount.id, newAccount)

    //==========================================================================================================
    @Throws(AccountNotFoundException::class)
    fun findAccountByNo(accountNo: Int, bankId: Int, banks: Array<Bank>): Account {
        return banks.find { it.id == bankId }
            ?.accounts?.get(accountNo)?: throw AccountNotFoundException()
    }

    //==========================================================================================================
    @Throws(InvalidPinException::class, NegativeAmountException::class, InSufficientBalanceException::class)
    fun deposit(fromAccNo: Int, fromBankId: Int, fromPin: Int,
                toAccNo: Int, toBankId: Int, depositAmount: Int, banks: Array<Bank>){

        val toAccount = findAccountByNo(toAccNo, toBankId, banks)
        withdraw(fromAccNo, fromBankId, fromPin, depositAmount, banks)

        toAccount.balance += depositAmount
    }

    //==========================================================================================================
    @Throws(InvalidPinException::class, NegativeAmountException::class, InSufficientBalanceException::class)
    fun withdraw(accNo: Int, bankId: Int, pin: Int, withdrawAmount: Int, banks: Array<Bank>){
        if (withdrawAmount < 0)throw  NegativeAmountException()

        with(findAccountByNo(accNo, bankId, banks)){
            if (withdrawAmount > balance)throw InSufficientBalanceException()
            if (this.pin != pin)throw InvalidPinException()
            balance -= withdrawAmount
        }
    }
}