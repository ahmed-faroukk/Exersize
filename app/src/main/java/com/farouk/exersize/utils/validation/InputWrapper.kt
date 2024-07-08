package com.farouk.exersize.utils.validation


import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import com.farouk.exersize.R
import com.farouk.exersize.theme.ExersizeTheme
import com.farouk.exersize.utils.validation.Validation.isValidPhone
import com.farouk.exersize.utils.validation.Validation.validateEmail
import com.farouk.exersize.utils.validation.Validation.validateText


enum class ValidationType {
    Text, Email, Phone
}

data class InputWrapper(

    var text: MutableState<String> = mutableStateOf(""),

    var isValid: MutableState<Boolean> = mutableStateOf(false),

    var borderColor: Color = Color.Gray,

    val validationType: ValidationType? = ValidationType.Text

) {

    var validationMessageResId: Int = R.string.empty_label

    fun onValueChange(input: String) {
        text.value = input
        validationMessageResId = when (validationType) {
            ValidationType.Phone -> input.isValidPhone().toMessageRes()
            ValidationType.Text -> input.validateText().toMessageRes()
            ValidationType.Email -> input.validateEmail().toMessageRes()
            else -> input.validateText().toMessageRes()
        }
        borderColor = if (isValid.value) {
            Color.Green
        } else {
            Color.Red
        }
        isValid.value = validationMessageResId == R.string.empty_label && text.value.isNotEmpty()
    }

    fun safeRequest(input: String): Boolean {
        when (validationType) {
            ValidationType.Phone -> return input.isValidPhone() == ValidationMessageType.Valid
            ValidationType.Text ->  return input.validateText() == ValidationMessageType.Valid
            ValidationType.Email -> return input.validateEmail() == ValidationMessageType.Valid
            else -> {}
        }
        return false
    }

}

private fun ValidationMessageType.toMessageRes(): Int {
    return when (this) {
        ValidationMessageType.EmptyField -> R.string.empty_label
        is ValidationMessageType.Invalid -> {
            when (this.validationType) {
                ValidationType.Email -> R.string.invalid_mail
                ValidationType.Phone -> R.string.invalid_phone
                ValidationType.Text -> R.string.invalid_mail
                else -> R.string.invalid_phone
            }
        }

        ValidationMessageType.Valid -> R.string.valid
    }
}