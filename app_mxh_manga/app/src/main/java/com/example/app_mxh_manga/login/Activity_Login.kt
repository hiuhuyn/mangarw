package com.example.app_mxh_manga.login

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.GetData
import com.example.app_mxh_manga.component.ModeDataSaveSharedPreferences
import com.example.app_mxh_manga.component.Notification
import com.example.app_mxh_manga.homePage.Activity_homePage
import com.example.app_mxh_manga.login.component.Activity_signup

class Activity_Login : AppCompatActivity() {
    private lateinit var edtUsername: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btn_signup: Button
    private lateinit var btn_login: Button
    private lateinit var btn_google: Button
    private lateinit var tv_forgot_pass: Button
    private lateinit var dialog:Dialog
    private lateinit var notification: Notification
    private var passwordVisible = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        edtUsername = findViewById(R.id.edtUsername)
        edtPassword = findViewById(R.id.edtPassword)
        btn_signup = findViewById(R.id.btn_signup)
        btn_login = findViewById(R.id.btn_login)
        btn_google = findViewById(R.id.btn_google)
        tv_forgot_pass = findViewById(R.id.tv_forgot_pass)
        notification = Notification(this)
        addEvent()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun addEvent() {

        // TODO: Toggle password visibility

        // TODO: Toggle password visibility
        edtPassword.setOnTouchListener(OnTouchListener { v, event ->
            val Right = 2
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= edtPassword.right - edtPassword.compoundDrawables[Right].bounds.width()) {
                    if (passwordVisible) {
                        edtPassword.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_baseline_visibility_off_24,
                            0
                        )
                        edtPassword.inputType = 129
                        passwordVisible = false
                    } else {
                        edtPassword.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_baseline_remove_red_eye_24,
                            0
                        )
                        edtPassword.inputType = 145
                        passwordVisible = true
                    }
                    return@OnTouchListener true
                }
            }
            false
        })

        btn_login.setOnClickListener {
            if (isEmptyEmail() && isEmptyPass()){
                val emailInput = edtUsername.text.toString().trim()
                val passInput = edtPassword.text.toString().trim()
                dialog =  notification.dialogLoading("Loading...")
                dialog.show()
                GetData().getUserByEmail(emailInput) { user ->
                    dialog.dismiss()
                    if (user != null){
                        if (user.user.password == passInput){

                            notification.toastCustom("Đăng nhập thành công").show()
                            ModeDataSaveSharedPreferences(this@Activity_Login).setLogin(user.id_user)
                            startActivity(Intent(this@Activity_Login, Activity_homePage::class.java))
                            finish()
                        }else{
                            notification.toastCustom("Mật khẩu không chính xác").show()
                        }
                    }else{
                        Toast.makeText(this@Activity_Login, "", Toast.LENGTH_SHORT).show()
                        notification.toastCustom("Email không tồn tại").show()
                    }
                }
            }
        }
        btn_signup.setOnClickListener {
            startActivity(Intent(this, Activity_signup::class.java))
        }

    }

    private fun isEmptyEmail(): Boolean{
        val emailInput = edtUsername.text.toString().trim()
        return if (emailInput.isEmpty()){
            notification.toastCustom("Bạn chưa nhập email!").show()
            false
        }else{
            true
        }
    }

    private fun isEmptyPass(): Boolean{
        val passInput = edtPassword.text.toString().trim()
        return if (passInput.isEmpty()){
            notification.toastCustom( "Bạn chưa nhập mật khẩu").show()
            false
        }else{
            true
        }
    }

}