import controllers.BankController
import controllers.CustomerAuthorizationController
import dao.AppData
import utils.CustomInput
import utils.CustomMessage

fun main() {
    val appData = AppData.getAppData()
    val customerAuth = CustomerAuthorizationController()
    val bankController = BankController()

    MAIN_LOOP@ while(true){
        CustomMessage.displayPrompt(" WELCOME TO KOTLIN BANK APP", "------------------------", "1. Create Customer", "2. Login as Customer", "", "0. Exit")
        val input1 = CustomInput.getUserPromptInput(0..2, "> ")

        when(input1){
            0 -> break@MAIN_LOOP
            1 -> customerAuth.signUp()
            2 -> customerAuth.logIn()
        }

        Bank_Loop@while(appData.getCurrentCustomer() != null){
            CustomMessage.displayPrompt("SELECT A BANK", "------------------------", "1. AXIS BANK", "2. INDIAN BANK", "3. HDFC BANK", "", "0. Back")
            val input2= CustomInput.getUserPromptInput(0..3, "> ")
            if(input2 in 1..3){
                appData.setCurrentBank(appData.banks[input2-1])
                CustomMessage.displayPrompt("--------------------------------------------------------------", "${appData.getCurrentBank()?.name} Selected Successfully", "--------------------------------------------------------------", "")
            }
            else appData.setCurrentCustomer(null)

            Account_Auth_Loop@while(appData.getCurrentBank() != null && appData.getCurrentAccount() == null){
                if(bankController.hasAccount()){

                    for(i in 2 downTo 0){
                        bankController.logIn()
                        if(appData.getCurrentAccount() != null)break@Account_Auth_Loop
                        println("$i attempts Left")
                    }
                    CustomMessage.displayPrompt("", "Maximum login attempts reached. Access denied", "")
                    break

                }else bankController.signUp()
            }
            val currentAccount = appData.getCurrentAccount()
            Account_Menu_Loop@while(currentAccount != null){
                //deposit, withdraw, show transaction, select different bank, Logout
                CustomMessage.displayPrompt(" Welcome ${currentAccount.customerDetails.firstName} to your Account", "----------------------------------------------------------------------",
                    "1. Deposit", "2. Withdraw", "3. Show Transactions", "4, View Account Details", "5. Select a different Bank", "0. Logout")
                val input3= CustomInput.getUserPromptInput(0..5, "> ")
                when(input3){
                    0 -> {
                        appData.setCurrentCustomer(null)
                        appData.setCurrentBank(null)
                        appData.setCurrentAccount(null)
                        break@Bank_Loop
                    }
                    1 -> bankController.deposit()
                    2 -> bankController.withdraw()
                    3 -> bankController.showTransactions()
                    4 -> CustomMessage.displayPrompt(" Account Details", "---------------------------", currentAccount.toString(), "---------------------------", "")
                    5 -> {
                        appData.setCurrentBank(null)
                        appData.setCurrentAccount(null)
                        break@Account_Menu_Loop
                    }
                }
            }
        }
    }
    CustomMessage.displayPrompt("", "=================================", "Thanks for using Kotlin Bank App", "=================================")
}