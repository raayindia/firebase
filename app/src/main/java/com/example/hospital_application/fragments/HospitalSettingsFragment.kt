package com.example.hospital_application.fragments

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.VectorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.example.hospital_application.R
import com.example.hospital_application.newlyAddedClasses.toUri
import com.example.hospital_application.welcomeActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.PermissionListener
import de.hdodenhof.circleimageview.CircleImageView

class HospitalSettingsFragment : Fragment() {
    private lateinit var profileImageView: ImageView
    private lateinit var profile: CircleImageView
    private lateinit var back: ImageView
    val SELECT_IMAGE = 200
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_hospital_settings, container, false)
        profileImageView = v.findViewById(R.id.camera_hosp)
        profile = v.findViewById(R.id.circle_image_hosp)
        back = v.findViewById(R.id.back_)

        back.setOnClickListener {
            val intent = Intent(requireContext(), welcomeActivity::class.java)
            startActivity(intent)
        }
        profileImageView.setOnClickListener {
//        checkAndRequestPermissions()
            requestPermissions()
        }
        return v
    }
    private fun requestPermissions() {
        Dexter.withContext(context)
            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    // Permission is granted, open the gallery.
                    openGallery()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    if (response.isPermanentlyDenied) {
                        // Permission is permanently denied, show a settings dialog.

                        showAllowDenyDialog()
                    } else {
                        // Permission is denied, show an "Allow" or "Deny" dialog.
                        showCameraOrGalleryDialog()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: com.karumi.dexter.listener.PermissionRequest?,
                    token: PermissionToken?
                ) {
                    // Show a rationale if necessary and continue with the permission request.
                    if (token != null) {
                        token.continuePermissionRequest()
                    }
                }
            }).check()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_IMAGE) {
                val selectedImageUri: Uri? = data?.data
                if (selectedImageUri != null) {
                    setProfilePicture(selectedImageUri)
                }
            } else if (requestCode == CAMERA_REQUEST_CODE) {
                val getImageBitmap: Bitmap? = data?.extras?.get("data") as Bitmap?
                if(getImageBitmap!= null) {
                    setProfilePicture(getImageBitmap)
                }

            }
        }
    }
    private fun setProfilePicture(image: Any) {
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Activity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        if (image is Uri) {
            // Selected from gallery
            profile.setImageURI(image)
        } else if (image is Bitmap) {
            // Captured from the camera
            profile.setImageBitmap(image)
        }
        editor.apply()
    }
    private fun showSettingsDialog() {
        // we are displaying an alert dialog for permissions
        val builder = AlertDialog.Builder(requireContext())

        // below line is the title for our alert dialog.
        builder.setTitle("Need Permissions")

        // below line is our message for our dialog
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.")
        builder.setPositiveButton("GO TO SETTINGS") { dialog, which ->
            // this method is called on click on positive button and on clicking shit button
            // we are redirecting our user from our app to the settings page of our app.
            dialog.cancel()
            // below is the intent from which we are redirecting our user.
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", requireActivity().packageName, null)
            intent.data = uri
            startActivityForResult(intent, 101)
        }
        builder.setNegativeButton("Cancel") { dialog, which ->
            // this method is called when user click on negative button.
            dialog.cancel()
        }
        // below line is used to display our dialog
        builder.show()
    }

    private fun showAllowDenyDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Permission Request")
        builder.setMessage("Allow or Deny gallery access?")

        builder.setPositiveButton("Allow") { dialog, which ->
            // The user allows access, open the gallery.

            showCameraOrGalleryDialog()
        }

        builder.setNegativeButton("Deny") { dialog, which ->
            // The user denies access, show the "Go to Settings" dialog.
            showSettingsDialog()
        }

        builder.show()
    }
    private fun showCameraOrGalleryDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Open with Camera or Gallery")
        builder.setMessage("Choose whether to open with the Camera or Gallery.")

        builder.setPositiveButton("Camera") { dialog, which ->
            // User selected the Camera.
            openCamera()
        }

        builder.setNegativeButton("Gallery") { dialog, which ->
            // User selected the Gallery.
            openGallery()
        }

        builder.show()
    }
    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (cameraIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
        } else {
            Toast.makeText(requireContext(), "No camera app available", Toast.LENGTH_SHORT).show()
        }
    }
    companion object {
        const val CAMERA_REQUEST_CODE = 123 // You can use any request code you prefer.
    }
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE)
    }
    override fun onPause() {
        super.onPause()
        saveProfileImageUri()
    }
    override fun onDestroy() {
        super.onDestroy()
        saveProfileImageUri()
    }

    private fun getProfileImageUri(): Uri? {
        return when (val drawable = profile.drawable) {
            is BitmapDrawable -> {
                // If the drawable is a BitmapDrawable, convert it to a URI
                drawable.bitmap.toUri(requireContext())
            }
            is VectorDrawable -> {
                // Handle VectorDrawable or other drawable types accordingly
                // You may choose to return null or handle them in a different way
                null
            }
            else -> {
                // Handle other drawable types accordingly
                // You may choose to return null or handle them in a different way
                null
            }
        }
    }
    private fun saveProfileImageUri() {
        // Save the current profile image URI when the fragment is paused or destroyed
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Activity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val profileImageUri = getProfileImageUri()
        editor.putString("profile_image_uri", profileImageUri?.toString())
        editor.apply()
    }
}