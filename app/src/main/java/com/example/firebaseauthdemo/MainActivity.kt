package com.example.firebaseauthdemo

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var txtUserId : TextView
    lateinit var txtEmail : TextView
    lateinit var btnLogout :  Button
    lateinit var txtHello : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtUserId = findViewById(R.id.txtUserId)
        txtEmail = findViewById(R.id.txtEmail)
        btnLogout = findViewById(R.id.btnLogout)
        txtHello = findViewById(R.id.txtHello)


        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.colorTool)
        }


        val userId = intent.getStringExtra("user_id")
        val emailId = intent.getStringExtra("email_id")

            txtUserId.text = "User Id :: $userId "
        loadData()

        btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            startActivity(Intent(this@MainActivity,LoginActivity::class.java))
            finish()
        }
    }
    private fun loadData(){
        val sharedPreferences: SharedPreferences = getSharedPreferences("users",Context.MODE_PRIVATE)
        val savedName : String? = sharedPreferences.getString("username",null)

        val savedPhone : String? = sharedPreferences.getString("phone",null)
        val savedEmail : String? = sharedPreferences.getString("email",null)

        txtEmail.text = "Email Id :: $savedEmail"
        txtHello.text = "Hello $savedName, You have successfully logged In. Your number is $savedPhone"

    }
}