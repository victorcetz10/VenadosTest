package com.example.venadostest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Toast
import android.util.Patterns
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()

        btn_sign_up.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
            finish()
        }

        btn_log_in.setOnClickListener {
            doLogin()
        }


    }

    private fun doLogin() {
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

        auth.signInWithEmailAndPassword(tv_username.text.toString(), tv_password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {

                    updateUI(null)
                }
            }
    }

    public override fun onStart() {
        super.onStart()
     //   val currentUser = auth.currentUser
       // updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {

        if (currentUser != null) {
            if(currentUser.isEmailVerified) {
                startActivity(Intent(this, MenuActivity::class.java))
                finish()
            }else{
                Toast.makeText(
                    baseContext, "Por favor verifica tu correo.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(
                baseContext, "Error al iniciar sesion.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


}

