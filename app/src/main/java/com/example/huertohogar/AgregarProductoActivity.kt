package com.example.huertohogar

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable // Para simular el almacenamiento simple

private const val REQUEST_IMAGE_CAPTURE = 1

data class Producto(
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val stock: Int,
    val fotoBitmap: Bitmap?
) : Serializable

class AgregarProductoActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var priceEditText: EditText
    private lateinit var stockEditText: EditText
    private lateinit var productImageView: ImageView
    private lateinit var generateDescriptionButton: Button
    private lateinit var capturePhotoButton: Button
    private lateinit var saveButton: Button

    private var capturedImage: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_producto)
        nameEditText = findViewById(R.id.edit_text_nombre)
        descriptionEditText = findViewById(R.id.edit_text_descripcion)
        priceEditText = findViewById(R.id.edit_text_precio)
        stockEditText = findViewById(R.id.edit_text_stock)
        productImageView = findViewById(R.id.image_view_foto)
        generateDescriptionButton = findViewById(R.id.button_generate_description)
        capturePhotoButton = findViewById(R.id.button_capture_photo)
        saveButton = findViewById(R.id.button_save_product)

        capturePhotoButton.setOnClickListener {
            dispatchTakePictureIntent()
        }

        saveButton.setOnClickListener {
            saveProductForTest()
        }

        supportActionBar?.title = "Agregar Nuevo Producto"
    }

    private fun dispatchTakePictureIntent() {

        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: Exception) {
            Toast.makeText(this, "No se encontró una aplicación de cámara.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // La cámara devuelve una imagen pequeña (thumbnail) en 'data.extras.get("data")'
            val imageBitmap = data?.extras?.get("data") as? Bitmap

            if (imageBitmap != null) {
                capturedImage = imageBitmap
                productImageView.setImageBitmap(imageBitmap)
                Toast.makeText(this, "Foto capturada y mostrada.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Error al capturar la foto.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveProductForTest() {
        // 1. Recolección y validación de datos
        val nombre = nameEditText.text.toString()
        val descripcion = descriptionEditText.text.toString()
        val precioStr = priceEditText.text.toString()
        val stockStr = stockEditText.text.toString()

        if (nombre.isBlank() || precioStr.isBlank() || stockStr.isBlank()) {
            Toast.makeText(this, "Por favor, completa los campos requeridos.", Toast.LENGTH_LONG).show()
            return
        }

        val precio = precioStr.toDoubleOrNull() ?: 0.0
        val stock = stockStr.toIntOrNull() ?: 0

        val nuevoProducto = Producto(
            nombre = nombre,
            descripcion = descripcion,
            precio = precio,
            stock = stock,
            fotoBitmap = capturedImage
        )

        Log.d("ProductoApp", "Producto Guardado (Test): $nuevoProducto")
        Log.d("ProductoApp", "Nombre: ${nuevoProducto.nombre}")
        Log.d("ProductoApp", "Precio: ${nuevoProducto.precio}")
        Log.d("ProductoApp", "Foto: ${if (nuevoProducto.fotoBitmap != null) "Sí" else "No"} (${nuevoProducto.fotoBitmap?.byteCount ?: 0} bytes)")

        Toast.makeText(this, "Producto '$nombre' guardado de forma temporal (solo en Logcat).", Toast.LENGTH_LONG).show()

        finish()
    }
}