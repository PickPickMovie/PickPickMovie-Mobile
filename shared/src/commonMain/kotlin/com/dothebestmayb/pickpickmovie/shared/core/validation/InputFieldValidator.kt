package com.dothebestmayb.pickpickmovie.shared.core.validation


class InputFieldValidator : Validator {
    override fun getRule(type: InputFieldType): ValidationRule {
        return InputPolicy.getRule(type)
    }
}
