package services

import customExceptions.AccountNotFoundException
import customExceptions.CustomerNotFoundException
import dao.AppData
import repositories.CustomerRepositories
import utils.CustomInput
import utils.CustomMessage

class CustomerServices {
    private val appData = AppData.getAppData()
    private val customerRepositories = CustomerRepositories()

    fun createCustomer(): Boolean{

        CustomMessage.displayPrompt("", " Sign Up ", "---------")
        val firstName = CustomInput.getUserPrompt("First Name: ")
        val lastName = CustomInput.getUserPrompt("Last Name: ")
        val email = CustomInput.getUserPrompt("Email: ")
        val password = CustomInput.getUserPromptInput(1000..9999, "New Password: ")

        return try{
            val newCustomer = customerRepositories.create(fName = firstName, lName = lastName, email = email, pwd = password)
            appData.customers.add(newCustomer)
            println(newCustomer)
            true
        }catch (e: IllegalArgumentException){
            e.message?.let {CustomMessage.displayPrompt("-------------------------------",it, "-------------------------------")}
            false
        }
    }
    //===========================================================================================================

    fun logInCustomer(): Boolean{
        CustomMessage.displayPrompt("", " Log In ", "---------")
        val email = CustomInput.getUserPrompt("Email: ")
        val password = CustomInput.getUserPromptInput(1000..9999, "Password: ")

        return try{
            val customer = customerRepositories.find(email, appData.customers)
            check(customer.password == password){"Wrong Password"}
            appData.setCurrentCustomer(customer)
            true
        }catch (e: CustomerNotFoundException){
            e.message?.let {CustomMessage.displayPrompt("-------------------------------",it, "-------------------------------")}
            false
        }catch (e:  IllegalStateException){
            e.message?.let {CustomMessage.displayPrompt("-------------------------------",it, "-------------------------------")}
            false
        }

    }
}