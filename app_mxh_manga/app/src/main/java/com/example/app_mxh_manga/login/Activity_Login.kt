package com.example.app_mxh_manga.login

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.GetData
import com.example.app_mxh_manga.component.ModeDataSaveSharedPreferences
import com.example.app_mxh_manga.homePage.Activity_homePage
import com.example.app_mxh_manga.login.component.Activity_signup
import com.example.app_mxh_manga.module.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Activity_Login : AppCompatActivity() {
    private lateinit var edt_email: EditText
    private lateinit var edt_pass: EditText
    private lateinit var btn_signup: Button
    private lateinit var btn_login: Button
    private lateinit var btn_google: Button
    private lateinit var tv_forgot_pass: TextView
    private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        edt_email = findViewById(R.id.edt_email)
        edt_pass = findViewById(R.id.edt_pass)
        btn_signup = findViewById(R.id.btn_signup)
        btn_login = findViewById(R.id.btn_login)
        btn_google = findViewById(R.id.btn_google)
        tv_forgot_pass = findViewById(R.id.tv_forgot_pass)
        progressBar = findViewById(R.id.progressBar)
        addEvent()
    }

    private fun addEvent() {
        btn_login.setOnClickListener {
            if(edt_email.text.isEmpty()){
                Toast.makeText(this,"Hãy nhập email", Toast.LENGTH_SHORT).show()
            }else if(edt_pass.text.isEmpty()){
                Toast.makeText(this,"Hãy nhập mật khẩu", Toast.LENGTH_SHORT).show()
            }else{
                progressBar.visibility = View.VISIBLE
                val email = edt_email.text.toString()
                GetData().getUserByEmail(email) { user ->
                    progressBar.visibility = View.GONE
                    if (user != null){
                        if (user.user.password == edt_pass.text.toString()){
                            Toast.makeText(this@Activity_Login, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                            ModeDataSaveSharedPreferences(this@Activity_Login).setLogin(user.id_user)
                            startActivity(Intent(this@Activity_Login, Activity_homePage::class.java))
                            finish()
                        }else{
                            Toast.makeText(this@Activity_Login, "Mật khẩu không chính xác", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(this@Activity_Login, "Email hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        btn_signup.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            startActivity(Intent(this, Activity_signup::class.java))
            progressBar.visibility = View.GONE

        }
    }
}