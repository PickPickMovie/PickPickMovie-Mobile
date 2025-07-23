package com.dothebestmayb.pickpickmovie.core.validation


class InputFieldValidator : Validator {
    override fun getRule(type: InputFieldType): ValidationRule {
        return InputPolicy.getRule(type)
    }
}
