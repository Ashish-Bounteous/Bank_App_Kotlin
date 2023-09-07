package utils

import customExceptions.InvalidInputException

internal object CustomInput {

    fun getUserPromptInput(range: IntRange, label: String): Int{
        var input = -1

         while(true){
             print(label)
            readlnOrNull()?.let { userInput ->
                try {
                    input = userInput.toInt()

                } catch (e: NumberFormatException) {
                    println("Invalid input. Please enter a valid number.")
                }
            } ?: println("No input provided.")
             if(input in range)return input
             println("PIN or PASSWORD should 4 DIGIT")
        }

    }
    //===============================================================================================

    fun getUserPrompt(label: String): String {
        var input = "";
        while (true) {
            print(label)
            readlnOrNull()?.let { userInput ->
                try {
                    input = userInput
                    if(input.trim().isEmpty())throw InvalidInputException()
                    userInput
                } catch (e: InvalidInputException) {
                    println(e.message)
                }
            } ?: println("No input provided.")
            if(input.trim().isNotEmpty())return input
        }
    }
}