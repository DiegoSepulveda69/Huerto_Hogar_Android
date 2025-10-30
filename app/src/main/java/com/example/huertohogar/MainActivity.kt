package com.example.huertohogar

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val emailEditText = findViewById<EditText>(R.id.txt_lg_correo)
        val passWordEditText = findViewById<EditText>(R.id.txt_lg_contrasena)
        val loginButton = findViewById<Button>(R.id.btn_login)

        val email_ficticio = "test@huerto.com"
        val contrasena_ficticio = "12345"

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passWordEditText.text.toString()

            if (email == email_ficticio && password == contrasena_ficticio) {
                Toast.makeText(this, "Bienvenido/a/e a Huerto Hogar!", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MenuPrincipalActivity::class.java)
                startActivity(intent)

                finish()
            } else {
                Toast.makeText(this,"Nombre de usuario o contraseña incorrectos", Toast.LENGTH_LONG).show()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}