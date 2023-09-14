package com.example.shopp.Views.Fragement

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.shopp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

class ProductFragment : Fragment() {

    private val REQUEST_CODE_PICK_IMAGE = 1

    private lateinit var imageView: ImageView
    private lateinit var typeEditText: EditText
    private lateinit var quantiteEditText: EditText
    private lateinit var prixEditText: EditText
    private lateinit var saveButton: Button

    private var imageUri: Uri? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.addproduit, container, false)

        // Initialize views
        imageView = view.findViewById(R.id.imgproduit)
        typeEditText = view.findViewById(R.id.edt_Type)
        quantiteEditText = view.findViewById(R.id.edt_quantite)
        prixEditText = view.findViewById(R.id.edt_prixx)
        saveButton = view.findViewById(R.id.BtnSaveAddProduit)

        // Set click listener for image view
        imageView.setOnClickListener {
            pickImageFromGallery()
        }

        // Set click listener for save button
        saveButton.setOnClickListener {
            saveProduct()
            println("save cliqued")
        }

        return view
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_PICK_IMAGE) {
            data?.data?.let {
                imageUri = it
                try {
                    val inputStream: InputStream? =
                        requireContext().contentResolver.openInputStream(it)
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    imageView.setImageBitmap(bitmap)
                } catch (e: FileNotFoundException) {
                    Log.e("ProductFragment", "Error loading image from URI: $e")
                }
            }
        }
    }

    private fun saveProduct() {
        val type = typeEditText.text.toString().trim()
        val quantite = quantiteEditText.text.toString().trim()
        val prix = prixEditText.text.toString().trim()

        if (type.isEmpty() || quantite.isEmpty() || prix.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val imageFile = imageUri?.let { getFileFromUri(it) }
        if (imageFile == null) {
            Toast.makeText(requireContext(), "Please select an image", Toast.LENGTH_SHORT).show()
            return
        }

        // Build the request body
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("type", type)
            .addFormDataPart("quantite", quantite)
            .addFormDataPart("prix", prix)
            .addFormDataPart(
                "image",
                imageFile.name,
                RequestBody.create("image/*".toMediaTypeOrNull(), imageFile)
            )
            .build()
        // Build the request
        val request = Request.Builder()
            .url("https://shopapp.onrender.com/stocks/addStock")
            .post(requestBody)
            .build()

        // Make the request using OkHttpClient
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build()
                    .newCall(request)
                    .execute()

                withContext(Dispatchers.Main) {
                    val jsonObject = JSONObject(response.body?.string())
                    val message = jsonObject.optString("message")
                    if (message == "add successfuly") {
                        Toast.makeText(
                            requireContext(),
                            "Product added successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        // Clear input fields and image view
                        typeEditText.setText("")
                        quantiteEditText.setText("")
                        prixEditText.setText("")
                        imageView.setImageResource(android.R.color.transparent)

                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Failed to add product",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: SocketTimeoutException) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Request timed out", Toast.LENGTH_SHORT).show()
                }
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(),
                        "Failed to connect to server",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun getFileFromUri(uri: Uri): File {
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        val file = File(requireContext().cacheDir, "temp_image")
        inputStream?.use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        return file
    }
}
