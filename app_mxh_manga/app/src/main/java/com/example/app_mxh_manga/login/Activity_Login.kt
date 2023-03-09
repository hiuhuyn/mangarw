package com.example.app_mxh_manga.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.GetData_id
import com.example.app_mxh_manga.component.ModeDataSaveSharedPreferences
import com.example.app_mxh_manga.login.component.Activity_signup

class Activity_Login : AppCompatActivity() {
    private lateinit var edt_email: EditText
    private lateinit var edt_pass: EditText
    private lateinit var btn_signup: Button
    private lateinit var btn_login: Button
    private lateinit var btn_google: Button
    private lateinit var tv_forgot_pass: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        edt_email = findViewById(R.id.edt_email)
        edt_pass = findViewById(R.id.edt_pass)
        btn_signup = findViewById(R.id.btn_signup)
        btn_login = findViewById(R.id.btn_login)
        btn_google = findViewById(R.id.btn_google)
        tv_forgot_pass = findViewById(R.id.tv_forgot_pass)
        addEvent()
    }

    private fun addEvent() {
        btn_login.setOnClickListener {
            if(edt_email.text.isEmpty()){
                Toast.makeText(this,"Hãy nhập email", Toast.LENGTH_SHORT).show()
            }else if(edt_pass.text.isEmpty()){
                Toast.makeText(this,"Hãy nhập mật khẩu", Toast.LENGTH_SHORT).show()
            }else{
                val user = GetData_id().getUser_form_email_pass(edt_email.text.toString(), edt_pass.text.toString())

                if (user != null){
                    ModeDataSaveSharedPreferences(this).setLogin(true, user.id_user)

                }else{
                    Toast.makeText(this, "Email hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show()
                }
            }
        }
        btn_signup.setOnClickListener {
            startActivity(Intent(this, Activity_signup::class.java))
        }
    }
}