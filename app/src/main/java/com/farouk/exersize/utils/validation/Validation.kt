package com.farouk.exersize.utils.validation

import com.farouk.exersize.utils.validation.Validation.validateEmail
import com.shehata.movies_kmp.util.validation.patterns.EMAIL_ADDRESS_PATTERN


sealed class ValidationMessageType(val validationType: ValidationType? = null) {
    data object EmptyField : ValidationMessageType()
    data class Invalid(val type: ValidationType?) : ValidationMessageType(type)
    data object Valid : ValidationMessageType()
}

object Validation {
    fun String.validateEmail(): ValidationMessageType {
        return if (this.isEmpty()) ValidationMessageType.EmptyField
        else if (EMAIL_ADDRESS_PATTERN.matches(this.trim()).not())
            ValidationMessageType.Invalid(ValidationType.Email)
        else ValidationMessageType.Valid
    }

    fun String.validateText(): ValidationMessageType {
        return if (this.isEmpty()) ValidationMessageType.EmptyField
        else ValidationMessageType.Valid
    }

    fun String.isValidPhone(): ValidationMessageType {
        val phoneRegex = """^(\+20|0)(10|11|12|15)[0-9]{8,10}$""".toRegex()
        if (this.isEmpty()) ValidationMessageType.EmptyField
        if ((this.length == 11) || (this.length == 13 && this[0] == '+')) {
            if (phoneRegex.matches(this))
                return ValidationMessageType.Valid
        }
        return ValidationMessageType.Invalid(ValidationType.Phone)
    }
}