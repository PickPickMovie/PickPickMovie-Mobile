package com.dothebestmayb.pickpickmovie.shared.core.validation

interface Validator {
    fun getRule(type: InputFieldType): ValidationRule
}
