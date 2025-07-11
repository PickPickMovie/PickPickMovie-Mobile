package com.dothebestmayb.pickpickmovie.core.validation

import javax.inject.Inject

class InputFieldValidator @Inject constructor(): Validator {
    override fun getRule(type: InputFieldType): ValidationRule {
        return InputPolicy.getRule(type)
    }
}
