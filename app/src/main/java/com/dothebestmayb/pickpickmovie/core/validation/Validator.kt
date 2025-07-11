package com.dothebestmayb.pickpickmovie.core.validation

interface Validator {
    fun getRule(type: InputFieldType): ValidationRule
}
