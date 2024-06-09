package com.example.healthprotectorapplication

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    private lateinit var editTextId: EditText
    private lateinit var btnCheckId : Button
    private lateinit var editTextPassword: EditText
    private lateinit var editTextName: EditText
    private lateinit var editTextGender: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextBirth: EditText
    private lateinit var btnSignIn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        editTextId = findViewById(R.id.editTextId)
        btnCheckId = findViewById(R.id.btnCheckId)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextName = findViewById(R.id.editTextName)
        editTextGender = findViewById(R.id.editTextGender)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextBirth = findViewById(R.id.editTextBirth)
        btnSignIn = findViewById(R.id.btnSignIn)

        btnCheckId.setOnClickListener {
            checkLoginId()
        }

        btnSignIn.setOnClickListener {
            signUpUser()
        }
    }

    private fun checkLoginId() {
        val loginId = editTextId.text.toString()

        val call = RetrofitClient.apiService.checkId(loginId)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody == "success") {
                        Log.d("CheckId", "Login ID is available: $responseBody")
                        Toast.makeText(this@SignUpActivity, "사용 가능한 ID 입니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.d("CheckId", "Login ID is not available: $responseBody")
                        Toast.makeText(this@SignUpActivity, "이미 존재하는 ID 입니다.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.d("CheckId", "Failure: ${response.errorBody()?.string()}")
                    Toast.makeText(this@SignUpActivity, "Failed to check ID", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("SignUp", "Error: ${t.message}")
                Toast.makeText(this@SignUpActivity, "Error checking ID", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun signUpUser() {
        val id = 0
        val loginId = editTextId.text.toString()
        val password = editTextPassword.text.toString()
        val username = editTextName.text.toString()
        val gender = editTextGender.text.toString()
        val email = editTextEmail.text.toString()
        val birthday = editTextBirth.text.toString()

        val call = RetrofitClient.apiService.join(id, loginId, password, username, gender, email, birthday)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody == "success") {
                        Log.d("SignUp", "Success: $responseBody")
                        val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Log.d("SignUp", "Failure: $responseBody")
                    }
                } else {
                    Log.d("SignUp", "Failure: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("SignUp", "Error: ${t.message}")
            }
        })
    }
}
