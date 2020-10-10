package com.jagoqui.capripicnic.auth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View.OnFocusChangeListener
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jagoqui.capripicnic.R
import com.jagoqui.capripicnic.pages.HomeActivity
import kotlinx.android.synthetic.main.activity_login.*
import java.util.regex.Pattern


class LoginActivity : AppCompatActivity() {

    private var email:String = ""
    private var password: String = ""
    private var isValidEmail : Boolean = false
    private var isValidPassword: Boolean = false
    private var emailOnFirstLeaveFocus: Boolean = false
    private var passwordOnFirstLeaveFocus: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

        //Setup
        setup()
    }

    private fun setup() {
        email_editText.addTextChangedListener(textWatcher)
        password_editText.addTextChangedListener(textWatcher)

        forgotPassword_textView.setOnClickListener{
            val passwordRecovery = Intent(this, PasswordRecoveryActivity::class.java)
            startActivity(passwordRecovery)
        }

        signIn_button.setOnClickListener {
            email = email_editText.text.toString()
            isValidEmail = onValidEmail(email)
            password = password_editText.text.toString()
            isValidPassword = onValidPassword(password)

            if(isValidEmail && isValidPassword){
                login()
            }
        }

        singUp_textView.setOnClickListener {
            email = email_editText.text.toString()
            password = password_editText.text.toString()

            if(email.isEmpty() && password.isEmpty()){
                startActivity(Intent(this, RegisterActivity::class.java))
            }else{
                MaterialAlertDialogBuilder(this)
                    .setMessage("Are you sure?")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Yes"){ _, _ ->
                        startActivity(Intent(this, RegisterActivity::class.java))
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
            email_editText.onFocusChangeListener =
                OnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        if(!emailOnFirstLeaveFocus) {
                            password = password_editText.text.toString()
                            isValidPassword = onValidPassword(password)
                        }
                        emailOnFirstLeaveFocus = true
                    }
                }

            password_editText.onFocusChangeListener =
                OnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        if(!passwordOnFirstLeaveFocus){
                            email = email_editText.text.toString()
                            isValidEmail = onValidEmail(email)
                        }
                        passwordOnFirstLeaveFocus = true
                    }
                }

            if(emailOnFirstLeaveFocus){
                email = email_editText.text.toString()
                isValidEmail = onValidEmail(email)
            }
            if(passwordOnFirstLeaveFocus){
                password = password_editText.text.toString()
                isValidPassword = onValidPassword(password)
            }
        }
    }

    private fun onValidEmail(email: String): Boolean {
        val isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

        if(email.isEmpty()){
            email_textFieldLoyout.error = "Email is required!"
        }else if (!isValid){
            email_textFieldLoyout.error = "You must enter a valid email!"
        }else{
            email_textFieldLoyout.error = null
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
            password_textFieldLayout.error = "Password is required!"
        }else if (!isValid){
            password_textFieldLayout.error = "The password must be at least 1 digit, 1 lower case " +
                    "letter, 1 upper case letter, least 1 special character, least 8 characters, no " +
                    "white spaces, any letter!" //TODO: Investigar como obtener el error
        }else{
            password_textFieldLayout.error = null
        }
        return isValid
    }

    private fun login(){
        val dataUser = email
        val home =  Intent (this, HomeActivity::class.java)
        home.putExtra("username",dataUser)
        startActivity(home)
    }
}