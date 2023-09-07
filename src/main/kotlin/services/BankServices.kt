package services

import customExceptions.*
import customExceptions.AccountNotFoundException
import customExceptions.InSufficientBalanceException
import customExceptions.NegativeAmountException
import dao.AppData
import models.Transaction
import repositories.AccountRepositories
import repositories.BankRepositories
import utils.CustomInput
import utils.CustomMessage
import utils.TransactionType
import utils.Utils

class BankServices {
    private val appData = AppData.getAppData()
    private val bankRepositories = BankRepositories()
    private val accountRepositories = AccountRepositories()

    fun checkIfAccountExist(): Boolean{
        val bankId = appData.getCurrentBank()?.id ?: return false
        return appData.getCurrentCustomer()?.accounts?.any{
            try {
                bankRepositories.findAccountByNo(it.id, bankId, appData.banks)
                true
            }catch (e: AccountNotFoundException){
                false
            }
        }?: false
    }
    //================================================================================================================

    fun createAccount(): Boolean{

        CustomMessage.displayPrompt("", " Create Account ", "-------------------")
        val balance = CustomInput.getUserPromptInput(0..Int.MAX_VALUE, "Credit Amount: ").toDouble()
        val pin = CustomInput.getUserPromptInput(1000..9999, "New Pin: ")

        return try{
            val currentCustomer = appData.getCurrentCustomer() ?: return false
            val currentBank = appData.getCurrentBank() ?: return false

            val newAccount = accountRepositories.create(balance, pin, currentCustomer)
            currentCustomer.accounts.add(newAccount)
            bankRepositories.addAccount(newAccount, currentBank)

            CustomMessage.displayPrompt("", newAccount.toString())
            true
        }catch (e: Exception){
            e.message?.let { CustomMessage.displayPrompt("-------------------------------",it, "-------------------------------")}
            false
        }
    }
    //===========================================================================================================

    fun logInAccount(): Boolean{
        CustomMessage.displayPrompt("", " Log In ", "---------")
        val accountNo = CustomInput.getUserPromptInput(1..Int.MAX_VALUE, "Account No: ")
        val pin = CustomInput.getUserPromptInput(1000..9999, "Pin: ")

        return try{
            val currentBank = appData.getCurrentBank() ?: return false
            val account = bankRepositories.findAccountByNo(accountNo, currentBank.id, appData.banks)

            check(account.pin == pin){"Wrong pin"}
            appData.setCurrentAccount(account)
            true
        }catch (e: Exception){
            e.message?.let { CustomMessage.displayPrompt("-------------------------------",it, "-------------------------------")}
            false
        }

    }
    //===========================================================================================================

    fun depositToAccount(): Boolean{
        CustomMessage.displayPrompt("", " Deposit Money", "---------------")
        return try{
            val fromAccount = appData.getCurrentAccount()?: return false
            val fromAccountNo = fromAccount.id
            val fromBankId = appData.getCurrentBank()?.id ?: return false
            val fromPin = CustomInput.getUserPromptInput(1000..9999, "Pin: ")
            val depositAmount = CustomInput.getUserPromptInput(1..Int.MAX_VALUE, "Deposit Amount: ")

            val toAccountNo = CustomInput.getUserPromptInput(0..Int.MAX_VALUE, "To Account no: ")
            val toBankId  = CustomInput.getUserPromptInput(0..Int.MAX_VALUE, "To Bank ID: ")

            bankRepositories.deposit(fromAccountNo, fromBankId, fromPin, toAccountNo, toBankId, depositAmount, appData.banks)

            val toAccount = bankRepositories.findAccountByNo(toAccountNo, toBankId, appData.banks)
            val time = Utils.currentDateTimeString()

            fromAccount.transactions.add(Transaction(fromAccount.transactions.size+1, depositAmount, TransactionType.DEBITED, time, fromAccount.balance, toAccountNo))
            toAccount.transactions.add(Transaction(toAccount.transactions.size+1, depositAmount, TransactionType.CREDITED, time, toAccount.balance, fromAccountNo))
            CustomMessage.displayPrompt("Deposit Successful to ${toAccount.customerDetails.firstName} of Rs ${depositAmount}.", "")
            true
        }
        catch(e: InvalidPinException){
            e.message?.let { CustomMessage.displayPrompt("-------------------------------",it, "-------------------------------")}
            false
        }
        catch(e: NegativeAmountException){
            e.message?.let { CustomMessage.displayPrompt("-------------------------------",it, "-------------------------------")}
            false
        }
        catch(e: InSufficientBalanceException){
            e.message?.let { CustomMessage.displayPrompt("-------------------------------",it, "-------------------------------")}
            false
        }
        catch(e: AccountNotFoundException){
            e.message?.let { CustomMessage.displayPrompt("-------------------------------",it, "-------------------------------")}
            false
        }
    }
    //===========================================================================================================

    fun withdrawFromAccount(): Boolean{
        CustomMessage.displayPrompt("", " Withdraw Money", "----------------")
        return try{
            val currentAccount = appData.getCurrentAccount()?: return false
            val accountNo = currentAccount.id
            val bankId = appData.getCurrentBank()?.id ?: return false
            val pin = CustomInput.getUserPromptInput(1000..9999, "Pin: ")
            val withdrawAmount = CustomInput.getUserPromptInput(1..Int.MAX_VALUE, "Withdraw Amount: ")

            bankRepositories.withdraw(accountNo, bankId, pin, withdrawAmount, appData.banks)
            currentAccount.transactions.add(Transaction(currentAccount.transactions.size+1, withdrawAmount, TransactionType.DEBITED, Utils.currentDateTimeString(), currentAccount.balance, accountNo))
            CustomMessage.displayPrompt("Successful Withdraw of Rs $withdrawAmount", "")
            true
        }
        catch(e: AccountNotFoundException){
            e.message?.let { CustomMessage.displayPrompt("-------------------------------",it, "-------------------------------")}
            false
        }
        catch(e: InvalidPinException){
            e.message?.let { CustomMessage.displayPrompt("-------------------------------",it, "-------------------------------")}
            false
        }
        catch(e: NegativeAmountException){
            e.message?.let { CustomMessage.displayPrompt("-------------------------------",it, "-------------------------------")}
            false
        }
        catch(e: InSufficientBalanceException){
            e.message?.let { CustomMessage.displayPrompt("-------------------------------",it, "-------------------------------")}
            false
        }
    }
    //=====================================================================================================================================================================

    fun displayAllTransactions(){
        println()
        println(" Transactions")
        println("==============")

        val currentAccount = appData.getCurrentAccount()
        if (currentAccount != null && currentAccount.transactions.size > 0){
            currentAccount.transactions.forEach{ println(it)}
        }else println(" no transaction yet")
        println()
    }
}