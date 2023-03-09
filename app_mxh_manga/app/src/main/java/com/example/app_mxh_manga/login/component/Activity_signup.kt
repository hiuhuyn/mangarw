package com.example.app_mxh_manga.login.component

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isEmpty
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.GetData_id

class Activity_signup : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var edt_email: EditText
    private lateinit var edt_pass: EditText
    private lateinit var edt_name: EditText
    private lateinit var edt_birthday: EditText
    private lateinit var rg_sex: RadioGroup
    private lateinit var btn_signup: Button




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        toolbar = findViewById(R.id.toolbar)
        edt_email = findViewById(R.id.edt_email)
        edt_pass = findViewById(R.id.edt_pass)
        edt_name = findViewById(R.id.edt_name)
        edt_birthday = findViewById(R.id.edt_birthday)
        btn_signup = findViewById(R.id.btn_signup)
        rg_sex = findViewById(R.id.rg_sex)

        toolbar.setNavigationOnClickListener {
            finish()
        }
        btn_signup.setOnClickListener {
            if(isEmptyView()){
                if (GetData_id().isCheckEmail(edt_email.text.toString())){
                    Toast.makeText(this, "Email đã tồn tại", Toast.LENGTH_SHORT).show()
                }else{
                    // thêm vào csdl

                    finish()

                }
            }
        }



    }
    fun isEmptyView(): Boolean{
        if (edt_email.text.isEmpty()){
            Toast.makeText(this, "Hãy nhập email!", Toast.LENGTH_SHORT).show()
            return false
        }
        if (edt_pass.text.isEmpty()){
            Toast.makeText(this, "Hãy nhập mật khẩu!", Toast.LENGTH_SHORT).show()
            return false
        }
        if (edt_name.text.isEmpty()){
            Toast.makeText(this, "Hãy nhập họ và tên!", Toast.LENGTH_SHORT).show()
            return false
        }
        if (edt_birthday.text.isEmpty()){
            Toast.makeText(this, "Hãy nhập ngày sinh!", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }




}