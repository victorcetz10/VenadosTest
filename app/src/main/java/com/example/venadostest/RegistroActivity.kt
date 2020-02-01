package com.example.venadostest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

import kotlinx.android.synthetic.main.activity_registro.*

class RegistroActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        auth = FirebaseAuth.getInstance()

        btn_sign_up.setOnClickListener {
            signUpUser()
        }

    }

    private fun signUpUser() {
        if (tv_username.text.toString().isEmpty()) {
            tv_username.error = "Por favor ingresa tu correo."
            tv_username.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(tv_username.text.toString()).matches()) {
            tv_username.error = "Por favor ingresa un correo valido."
            tv_username.requestFocus()
            return
        }

        if (tv_password.text.toString().isEmpty()) {
            tv_password.error = "Por favor ingresa tu contraseña."
            tv_password.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(tv_username.text.toString(), tv_password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val user:FirebaseUser?=auth.currentUser
                    verificarEmail(user)

                } else {
                    Toast.makeText(baseContext, "Registro fallido, Intentelo de nuevo mas tarde.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun verificarEmail (user: FirebaseUser?){
        user?.sendEmailVerification()?.addOnCompleteListener(this){
                task ->
            if(task.isComplete){
                Toast.makeText(this, "Email Enviado", Toast.LENGTH_LONG).show()
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }else{
                Toast.makeText(this, "Error Email no enviado", Toast.LENGTH_LONG).show()
            }
        }
    }
}