package com.example.healthprotectorapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : AppCompatActivity() {

    private lateinit var editTextId: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var signInButton : Button
    private lateinit var signUpButton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        editTextId = findViewById(R.id.editTextId)
        editTextPassword = findViewById(R.id.editTextPassword)
        signInButton = findViewById(R.id.btnSignIn)
        signUpButton = findViewById(R.id.btnSignUp)

        signInButton.setOnClickListener {
            loginUser()
        }
        signUpButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser() {
        val loginId = editTextId.text.toString()
        val password = editTextPassword.text.toString()

        val call = RetrofitClient.apiService.login(loginId, password)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody == "success") {
                        Log.d("Login", "Success: $responseBody")
                        val intent = Intent(this@SignInActivity, MenuActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Log.d("Login", "API Response is not 'success': $responseBody")
                        Toast.makeText(this@SignInActivity, "Login failed: $responseBody", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.d("Login", "Failure: ${response.errorBody()?.string()}")
                    Toast.makeText(this@SignInActivity, "Login failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("Login", "Error: ${t.message}")
                Toast.makeText(this@SignInActivity, "Error logging in", Toast.LENGTH_SHORT).show()
            }
        })
    }
}