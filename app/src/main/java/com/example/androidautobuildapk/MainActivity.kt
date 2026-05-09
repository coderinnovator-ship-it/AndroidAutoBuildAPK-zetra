package com.example.androidautobuildapk

import android.content.Context
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.abs
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    lateinit var usernameInput: EditText
    lateinit var createBtn: Button
    lateinit var resultText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usernameInput = findViewById(R.id.usernameInput)
        createBtn = findViewById(R.id.createBtn)
        resultText = findViewById(R.id.resultText)

        val prefs = getSharedPreferences("zetra", Context.MODE_PRIVATE)

        // Load saved identity
        val savedName = prefs.getString("username", null)
        val savedId = prefs.getString("id", null)

        if (savedName != null && savedId != null) {
            resultText.text = "Welcome back $savedName\nZetra ID: $savedId"

            // 🔒 LOCK IDENTITY
            createBtn.isEnabled = false
            createBtn.text = "ID LOCKED"
        }

        createBtn.setOnClickListener {

            val username = usernameInput.text.toString().trim()

            if (username.isEmpty()) {
                resultText.text = "Enter username first"
                return@setOnClickListener
            }

            val id = generateId()

            // Save identity
            prefs.edit()
                .putString("username", username)
                .putString("id", id.toString())
                .apply()

            resultText.text = "User: $username\nZetra ID: $id"

            // 🔒 LOCK AFTER CREATION
            createBtn.isEnabled = false
            createBtn.text = "ID LOCKED"
        }
    }

    private fun generateId(): Long {
        val min = 10000000000L
        val max = 99999999999L
        return min + abs(Random.nextLong() % (max - min))
    }
}
