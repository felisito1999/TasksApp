package com.example.tasksapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_login.setOnClickListener {
            if(txt_password.length() < 5 && txt_emailAddress.length() < 5){
                Toast.makeText(this, "El e-mail o la contraseña no son válidos", Toast.LENGTH_LONG).show();
            }
            else {
                val passwordHandler = PasswordHandler().checkLogin(txt_emailAddress.text.toString(), txt_password.text.toString());
                if (passwordHandler){
                    val homeTasksActivity = Intent(this, HomeTasksActivity::class.java);
                    startActivity(homeTasksActivity);
                }
                else {
                    Toast.makeText(this, "La contraseña o el correo son incorrectos", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}

class PasswordHandler() {
    fun checkLogin(email: String, password: String): Boolean {
        return email == "fp2017-2867@uce.edu.do" && password == "passwordHermosa"
    }
}