package controllers

import services.BankServices
import utils.CustomInput
import utils.CustomMessage

class BankController {
    private val bankServices = BankServices()

    fun signUp(){
        while(!bankServices.createAccount()){}
        CustomMessage.displayPrompt("-------------------------------", "Account created successfully.", "-------------------------------", "")
    }

    fun logIn(){
        if(bankServices.logInAccount()){
            CustomMessage.displayPrompt("-------------------------------", "Log In successfully.", "-------------------------------", "")
        }
    }

    fun hasAccount(): Boolean{
        return bankServices.checkIfAccountExist()
    }

    fun deposit() {
        while(!bankServices.depositToAccount()){
            CustomMessage.displayPrompt(" Do you want to try again?", "------------------------", "1. Yes", "2. no, go back to account menu")
            val input = CustomInput.getUserPromptInput(1..2, "> ")
            if(input == 2)break
        }
    }

    fun withdraw() {
        while(!bankServices.withdrawFromAccount()){
            CustomMessage.displayPrompt(" Do you want to try again?", "------------------------", "1. Yes", "2. no, go back to account menu")
            val input = CustomInput.getUserPromptInput(1..2, "> ")
            if(input == 2)break
        }
    }

    fun showTransactions() {
        bankServices.displayAllTransactions()
    }
}