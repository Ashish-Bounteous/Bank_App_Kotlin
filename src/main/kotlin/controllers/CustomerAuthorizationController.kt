package controllers

import services.CustomerServices
import utils.CustomMessage

class CustomerAuthorizationController {

    private val customerServices = CustomerServices()

    fun signUp(){
        while(!customerServices.createCustomer()){}
        CustomMessage.displayPrompt("-------------------------------", "Customer created successfully.", "-------------------------------", "")

    }

    fun logIn(){
        if(customerServices.logInCustomer()){
            CustomMessage.displayPrompt("-------------------------------", "Log In successfully.", "-------------------------------", "")
        }
        println()
    }

}