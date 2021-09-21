package com.example.firebaseauthdemo

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {
    lateinit var txtEmail : EditText
    lateinit var btnSubmit : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        btnSubmit = findViewById(R.id.btnSubmit)
        txtEmail = findViewById(R.id.txtEmail)

        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.colorTool)
        }

        btnSubmit.setOnClickListener {
            val email : String = txtEmail.text.toString().trim(){it <= ' '}
            if(email.isEmpty()){
                Toast.makeText(
                    this,
                    "Please enter email address",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else{
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener{task->
                        if(task.isSuccessful){
                            Toast.makeText(
                                this,
                                "Email sent successfully to reset your password",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        }
                        else{
                            Toast.makeText(
                                this,
                                task.exception!!.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }

    }
}