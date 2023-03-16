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
import com.example.app_mxh_manga.component.Notification
import com.example.app_mxh_manga.module.User
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class Activity_signup : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var textInputEmail: TextInputLayout
    private lateinit var textInputpass: TextInputLayout
    private lateinit var textInputname: TextInputLayout
    private lateinit var edt_birthday: EditText
    private lateinit var rg_sex: RadioGroup
    private lateinit var btn_signup: Button
    private var birthdayText = ""
    private lateinit var notification: Notification



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        toolbar = findViewById(R.id.toolbar)
        textInputEmail = findViewById(R.id.textInput_email)
        textInputpass = findViewById(R.id.textInput_pass)
        textInputname = findViewById(R.id.textInput_name)
        edt_birthday = findViewById(R.id.edt_birthday)
        btn_signup = findViewById(R.id.btn_signup)
        rg_sex = findViewById(R.id.rg_sex)
        notification = Notification(this)

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
            if( isEmptyEmail() && isEmptyPass() && isEmptyName() && isEmptyBirthday() ){
                val email = textInputEmail.editText?.text.toString().trim()
                val pass = textInputpass.editText?.text.toString().trim()
                val name = textInputname.editText?.text.toString().trim()
                val dialog = notification.dialogLoading("save...")
                dialog.show()
                GetData().getUserByEmail(email){ userGet ->
                    dialog.dismiss()
                    if (userGet != null) {
                        textInputEmail.error = "Email đã tồn tại"
                        textInputEmail.isErrorEnabled = true
                    }else{
                        val user = User(name, birthdayText, sex, email, pass)
                        AddData().newUser(user){b ->
                            if (b){
                                notification.toastCustom("Tạo tài khoản thành công").show()
                                finish()
                            }else{
                                notification.toastCustom("Tạo tài khoản thất bại!").show()
                            }
                        }
                    }
                }

            }
        }



    }

    private fun isEmptyEmail(): Boolean{
        val emailInput = textInputEmail.editText?.text.toString().trim()
        if (emailInput.isEmpty()){
            textInputEmail.error = "Email chưa được nhập!"
            return false
        }else{
            textInputEmail.error = null
            textInputEmail.isErrorEnabled = false
            return true
        }
    }
    private fun isEmptyPass(): Boolean{
        val passInput = textInputpass.editText?.text.toString().trim()
        if (passInput.isEmpty()){
            textInputpass.error = "Mật khẩu chưa được nhập!"
            return false
        }else{
            textInputpass.error = null
            textInputpass.isErrorEnabled = false
            return true
        }
    }
    private fun isEmptyName(): Boolean{
        val nameInput = textInputname.editText?.text.toString().trim()
        if (nameInput.isEmpty()){
            textInputname.error = "Tên chưa được nhập!"
            return false
        }else{
            textInputname.error = null
            textInputname.isErrorEnabled = false
            return true
        }
    }
    private fun isEmptyBirthday(): Boolean{
        val birthday = edt_birthday.text.toString().trim()
        if (birthday.isEmpty()){
            edt_birthday.error = "Chưa chọn ngày sinh!"
            notification.toastCustom("Chưa chọn ngày sinh").show()
            return false
        }else{
            edt_birthday.error = null
            return true
        }
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