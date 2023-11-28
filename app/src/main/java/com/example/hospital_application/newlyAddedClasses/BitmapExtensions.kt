package com.example.hospital_application.newlyAddedClasses

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import java.io.ByteArrayOutputStream

fun Bitmap.toUri(context: Context): Uri {
    val bytes = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, bytes)

    // Insert the image into the MediaStore and get the path
    val path = MediaStore.Images.Media.insertImage(
        context.contentResolver,
        this,
        "Title",
        null
    )

    // Check if the path is null before attempting to parse it into Uri
    return if (path != null) {
        Uri.parse(path)
    } else {
        // Provide a default Uri or handle the situation as needed
        Uri.EMPTY
    }
}