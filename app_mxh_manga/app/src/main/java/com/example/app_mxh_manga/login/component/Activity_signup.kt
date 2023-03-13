package com.example.app_mxh_manga.login.component

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.AddData
import com.example.app_mxh_manga.component.GetData
import com.example.app_mxh_manga.module.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class Activity_signup : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var edt_email: EditText
    private lateinit var edt_pass: EditText
    private lateinit var edt_name: EditText
    private lateinit var edt_birthday: EditText
    private lateinit var rg_sex: RadioGroup
    private lateinit var btn_signup: Button
    private var birthdayText = ""
    private lateinit var progressBar: ProgressBar




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
        progressBar = findViewById(R.id.progressBar)

        edt_birthday.showSoftInputOnFocus = false
        toolbar.setNavigationOnClickListener {
            finish()
        }
        var sex = true
        rg_sex.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.rb_male -> {
                    sex = true
                }
                R.id.rb_female -> {
                    sex = false
                }
            }
        }
        edt_birthday.setOnClickListener {
            showDatePickerDialog()
        }

        btn_signup.setOnClickListener {
            if(isEmptyView()){
                progressBar.visibility = View.VISIBLE
                GetData().getUserByEmail(edt_email.text.toString()){userGet ->
                    if (userGet != null) {
                        progressBar.visibility = View.GONE
                        if (userGet.id_user != null){
                            Toast.makeText(this@Activity_signup, "Email đã tồn tại!", Toast.LENGTH_SHORT).show()
                        }else{
                            val user = User(edt_name.text.toString(), birthdayText, sex, edt_email.text.toString(), edt_pass.text.toString())
                            progressBar.visibility = View.VISIBLE
                            AddData().newUser(user){b ->
                                progressBar.visibility = View.GONE
                                if (b){
                                    Toast.makeText(this@Activity_signup, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show()
                                    finish()
                                }else{
                                    Toast.makeText(this@Activity_signup, "Tạo tài khoản thất bại!", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
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
    private fun showDatePickerDialog() {
        // Lấy ngày hiện tại để thiết lập cho DatePickerDialog
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)

        // Khởi tạo DatePickerDialog
        val datePickerDialog = DatePickerDialog(this, R.style.Them_datePickerDialog, { _, selectedYear, selectedMonth, selectedDay ->
            // Lấy ngày được chọn và hiển thị trên TextView
            val selectedDate = "${selectedDay}/${selectedMonth + 1}/${selectedYear}"
            birthdayText = selectedDate
            edt_birthday.setText(birthdayText)
        }, year, month, day)
        // Hiển thị DatePickerDialog
        datePickerDialog.show()
    }



}