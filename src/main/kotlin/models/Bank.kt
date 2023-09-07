package models

import utils.IdGenerator

internal data class Bank (
    val id: Int = IdGenerator.generateBankID(),
    val name: String,
    val accounts: HashMap<Int, Account> = HashMap()
)