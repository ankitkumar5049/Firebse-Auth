package com.example.firebaseauthdemo

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterActivity : AppCompatActivity() {
    lateinit var btn_register : Button
    lateinit var txt_email : EditText
    lateinit var txt_password : EditText
    lateinit var txt_Login : TextView
    lateinit var txtName : EditText
    lateinit var txtPhone : EditText
    lateinit var txtConfirmPsd : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btn_register = findViewById(R.id.btn_register)
        txt_Login = findViewById(R.id.tv_login)
        txt_email = findViewById(R.id.txt_email)
        txt_password = findViewById(R.id.txt_password)
        txtConfirmPsd = findViewById(R.id.txt__confirm_password)
        txtName = findViewById(R.id.txtName)
        txtPhone = findViewById(R.id.txtPhone)


        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.colorTool)
        }

        txt_Login.setOnClickListener {
                onBackPressed()
        }

        btn_register.setOnClickListener {
            when{
                TextUtils.isEmpty(txt_email.text.toString().trim(){ it <= ' '})->{
                    Toast.makeText(this@RegisterActivity,"Please enter email.",
                    Toast.LENGTH_SHORT
                        ).show()
                }
                TextUtils.isEmpty(txtName.text.toString().trim(){ it <= ' '})->{
                    Toast.makeText(this@RegisterActivity,"Please enter name.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(txtPhone.text.toString().trim(){ it <= ' '})->{
                    Toast.makeText(this@RegisterActivity,"Please enter phone number.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(txt_password.text.toString().trim(){ it <= ' '})->{
                    Toast.makeText(this@RegisterActivity,"Please enter password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(txtConfirmPsd.text.toString().trim(){ it <= ' '})->{
                    Toast.makeText(this@RegisterActivity,"Please confirm password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else->{

                    val name : String = txtName.text.toString().trim(){it <= ' '}
                    val phone : String = txtPhone.text.toString().trim(){it <= ' '}
                    val email : String = txt_email.text.toString().trim(){it <= ' '}
                    val password : String = txt_password.text.toString().trim(){it <= ' '}
                    val confirmPassword : String = txtConfirmPsd.text.toString().trim(){it <= ' '}




                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener { task ->

                            if(password == confirmPassword) {

                                if (task.isSuccessful) {
                                    val firebaseUser: FirebaseUser = task.result!!.user!!

                                    saveData()

                                    Toast.makeText(
                                        this@RegisterActivity,
                                        "you are registered user",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    val intent =
                                        Intent(this@RegisterActivity, MainActivity::class.java)
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    intent.putExtra("user_id", firebaseUser.uid)
                                    intent.putExtra("email_id", email)
                                    startActivity(intent)
                                    finish()

                                } else {
                                    Toast.makeText(
                                        this@RegisterActivity,
                                        task.exception!!.message.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }else{
                                Toast.makeText(
                                    this@RegisterActivity,
                                    "Password Mismatch",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }
        }
    }
    private fun saveData(){
        val sharedPreferences: SharedPreferences = getSharedPreferences("users",Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.apply {
            putString("username",txtName.text.toString())
            putString("phone",txtPhone.text.toString())
            putString("email",txt_email.text.toString())
        }.apply()


    }


}