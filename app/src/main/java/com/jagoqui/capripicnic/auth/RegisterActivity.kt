package com.jagoqui.capripicnic.auth

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jagoqui.capripicnic.R
import kotlinx.android.synthetic.main.activity_register.*
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {

    private var name: String = ""
    private var email: String = ""
    private var password: String = ""
    private var passwordVerified: String = ""
    private var isValidName: Boolean = false
    private var isValidEmail : Boolean = false
    private var isValidPassword: Boolean = false
    private var isValidPasswordVerified: Boolean = false
    private var nameOnFirstLeaveFocus: Boolean = false
    private var emailOnFirstLeaveFocus: Boolean = false
    private var passwordOnFirstLeaveFocus: Boolean = false
    private var passwordVerifiedOnFirstLeaveFocus: Boolean = false
    private var isAcceptTerms: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out)

        //Setup
        setup()
    }

    private fun setup(){
//        nameRegister_editText.addTextChangedListener(textWatcher)
//        emailRegister_editText.addTextChangedListener(textWatcher)
//        passwordRegister_editText.addTextChangedListener(textWatcher)
//        passwordVerifiedRegister_editText.addTextChangedListener(textWatcher)

        termsRegister_textView.setOnClickListener{
            acceptTermsRegister_checkbox.isClickable= true
            acceptTermsRegister_checkbox.isEnabled= true
            termsRegister_textView.setTextColor(Color.parseColor("#ffffff")) //White
            MaterialAlertDialogBuilder(this)
                .setMessage("Are you sure?")
                .setNeutralButton("Cancel", null)
                .setNegativeButton("No"){ _, _ ->
                    acceptTermsRegister_checkbox.isChecked = false
                }
                .setPositiveButton("Yes"){ _, _ ->
                    acceptTermsRegister_checkbox.isChecked = true
                }
                .show()
        }

        register_button.setOnClickListener {
            name = nameRegister_editText.text.toString()
            isValidName = onValidName(name)
            email = emailRegister_editText.text.toString()
            isValidEmail = onValidEmail(email)
            password = passwordRegister_editText.text.toString()
            isValidPassword = onValidPassword(password)
            passwordVerified = passwordVerifiedRegister_editText.text.toString()
            isValidPasswordVerified = onValidPasswordVerified(passwordVerified)

            isAcceptTerms = if(!acceptTermsRegister_checkbox.isEnabled){
                termsRegister_textView.setTextColor(ContextCompat.getColor(applicationContext,R.color.colorAccent))
                false
            }else true

            if(isValidName && isValidEmail && isValidPassword && isValidPasswordVerified && isAcceptTerms){
                register()
            }
        }

        singInRegister_textView.setOnClickListener {
            name = nameRegister_editText.text.toString()
            email = emailRegister_editText.text.toString()
            password = passwordRegister_editText.text.toString()
            passwordVerified = passwordVerifiedRegister_editText.text.toString()

            if(name.isEmpty() && email.isEmpty() && password.isEmpty() && passwordVerified.isEmpty()){
                startActivity(Intent(this, LoginActivity::class.java))
            }else{
                MaterialAlertDialogBuilder(this)
                    .setMessage("Are you sure?")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Yes"){ _, _ ->
                        startActivity(Intent(this, LoginActivity::class.java))
                    }
                    .show()
            }
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            //TODO: Obtener el textView que llamó al envento, crear bandera para que simular onDirty despues de activar un onfocusLeave
            nameRegister_editText.onFocusChangeListener =
                View.OnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        if (!nameOnFirstLeaveFocus) {
                            email = emailRegister_editText.text.toString()
                            password = passwordRegister_editText.text.toString()
                            passwordVerified = passwordVerifiedRegister_editText.text.toString()
                            isValidEmail = onValidEmail(email)
                            isValidPassword = onValidPassword(password)
                            isValidPasswordVerified = onValidPasswordVerified(passwordVerified)
                        }
                        nameOnFirstLeaveFocus = true
                    }
                }

            emailRegister_editText.onFocusChangeListener =
                View.OnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        if (!emailOnFirstLeaveFocus) {
                            name = nameRegister_editText.text.toString()
                            password = passwordRegister_editText.text.toString()
                            passwordVerified = passwordVerifiedRegister_editText.text.toString()
                            isValidName = onValidName(name)
                            isValidPassword = onValidPassword(password)
                            isValidPasswordVerified = onValidPasswordVerified(passwordVerified)
                        }
                        emailOnFirstLeaveFocus = true
                    }
                }

            passwordRegister_editText.onFocusChangeListener =
                View.OnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        if (!passwordOnFirstLeaveFocus) {
                            name = nameRegister_editText.text.toString()
                            email = emailRegister_editText.text.toString()
                            passwordVerified = passwordVerifiedRegister_editText.text.toString()
                            isValidName = onValidName(name)
                            isValidEmail = onValidEmail(password)
                            isValidPasswordVerified = onValidPasswordVerified(passwordVerified)
                        }
                        passwordOnFirstLeaveFocus = true
                    }
                }

            passwordVerifiedRegister_editText.onFocusChangeListener =
                View.OnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        if (!passwordVerifiedOnFirstLeaveFocus) {
                            name = nameRegister_editText.text.toString()
                            email = emailRegister_editText.text.toString()
                            password = passwordRegister_editText.text.toString()
                            isValidName = onValidName(name)
                            isValidEmail = onValidEmail(password)
                            isValidPassword = onValidPassword(password)
                        }
                        passwordOnFirstLeaveFocus = true
                    }
                }

            if(nameOnFirstLeaveFocus){
                name = nameRegister_editText.text.toString()
                isValidName = onValidName(name)
            }
            if(emailOnFirstLeaveFocus){
                email = emailRegister_editText.text.toString()
                isValidEmail = onValidEmail(email)
            }
            if(passwordOnFirstLeaveFocus){
                password = passwordRegister_editText.text.toString()
                isValidPassword = onValidPassword(password)
            }
            if(passwordVerifiedOnFirstLeaveFocus){
                passwordVerified = passwordVerifiedRegister_editText.text.toString()
                isValidPassword = onValidPasswordVerified(passwordVerified)
            }
        }
    }

    private fun onValidName(name: String): Boolean{
        val usernamePattern = Pattern.compile(
            "^" +
//                    "(?=\\.*[0-9])" +         //no digits
//                    "(?=\\.*[@#$%^&+=])" +    //no special character
                    ".{10,}" +               //at least 8 characters
                    "$",
        )

        val isValid = usernamePattern.matcher(name).matches()

        if(name.isEmpty()){
            passwordRegister_textFieldLayout.error = "Password is required!"
        }else if (!isValid){
            nameRegister_textFieldLoyout.error = "The name no must be digits, specials characters, " +
                    "and least 10 characters!" //TODO: Investigar como obtener el error
        }else{
            nameRegister_textFieldLoyout.error = null
        }
        return isValid
    }

    private fun onValidEmail(email: String): Boolean {
        val isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

        if(email.isEmpty()){
            emailRegister_textFieldLoyout.error = "Email is required!"
        }else if (!isValid){
            emailRegister_textFieldLoyout.error = "You must enter a valid email!"
        }else{
            emailRegister_textFieldLoyout.error = null
        }
        return isValid
    }

    private fun onValidPassword(password: String): Boolean {
        val passwordPattern = Pattern.compile(
            "^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{8,}" +               //at least 8 characters
                    "$",
        )
        val isValid = passwordPattern.matcher(password).matches()

        if(password.isEmpty()){
            passwordRegister_textFieldLayout.error = "Password is required!"
        }else if (!isValid){
            passwordRegister_textFieldLayout.error = "The password must be at least 1 digit, 1 lower case " +
                    "letter, 1 upper case letter, least 1 special character, least 8 characters, no " +
                    "white spaces, any letter!" //TODO: Investigar como obtener el error
        }else{
            passwordRegister_textFieldLayout.error = null
        }
        return isValid
    }

    private fun onValidPasswordVerified(passwordV: String): Boolean {
        val isValid = passwordV == password

        if (password.isNotEmpty()) {
            if (!isValid){
                passwordVerifiedRegister_textFieldLayout.error = "Passwords do not match"
            }else{
                passwordVerifiedRegister_textFieldLayout.error = null
            }
        } else {
            passwordVerifiedRegister_textFieldLayout.error = "Passwords verified es required!"
        }
        return isValid
    }

    private fun register(){
        MaterialAlertDialogBuilder(this)
            .setMessage("Data user\n\n" +
                    "Name: $name\n" +
                    "Email: $email\n" +
                    "Password: $password\n\n")
            .setNegativeButton("OK", null)
            .show()
    }


}