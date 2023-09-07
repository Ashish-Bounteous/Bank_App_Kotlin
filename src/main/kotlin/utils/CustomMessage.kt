package utils

internal object CustomMessage {
    fun displayPrompt(vararg messages: String) = messages.forEach { println(it) }

}