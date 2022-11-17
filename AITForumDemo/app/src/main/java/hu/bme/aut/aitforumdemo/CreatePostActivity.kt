package hu.bme.aut.aitforumdemo

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import hu.bme.aut.aitforumdemo.data.Post
import hu.bme.aut.aitforumdemo.databinding.ActivityCreatePostBinding

class CreatePostActivity : AppCompatActivity() {

    companion object {
        const val COLLECTION_POSTS = "posts"
        const val REQUEST_CAMERA_PERMISSION = 1001
    }

    private lateinit var binding: ActivityCreatePostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSend.setOnClickListener {
            uploadPost()
        }

        binding.btnAttach.setOnClickListener {
            openCamera()
        }

        requestNeededPermission()
    }

    var uploadBitmap: Bitmap? = null

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result ->

        if (result.resultCode == Activity.RESULT_OK){
            val data: Intent? = result.data
            uploadBitmap = data!!.extras!!.get("data") as Bitmap
            binding.imgAttach.setImageBitmap(uploadBitmap)
            binding.imgAttach.visibility = View.VISIBLE
        }
    }

    fun openCamera() {
        val intentPhoto = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        resultLauncher.launch(intentPhoto)
    }




    private fun requestNeededPermission() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.CAMERA)) {
                Toast.makeText(this,
                    "I need it for camera", Toast.LENGTH_SHORT).show()
            }

            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.CAMERA),
                REQUEST_CAMERA_PERMISSION)

        } else {
            // we already have permission
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_CAMERA_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "CAMERA perm granted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "CAMERA perm NOT granted", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }





    private fun uploadPost() {
        val myPost = Post(
            FirebaseAuth.getInstance().currentUser!!.uid,
            FirebaseAuth.getInstance().currentUser!!.email!!,
            binding.etTitle.text.toString(),
            binding.etBody.text.toString(),
            ""
        )

        val postsCollection = FirebaseFirestore.getInstance()
            .collection(COLLECTION_POSTS)

        postsCollection.add(myPost)
            .addOnSuccessListener {
                Toast.makeText(this,
                    "Post saved", Toast.LENGTH_SHORT).show()

                finish()
            }
            .addOnFailureListener{
                Toast.makeText(this,
                        "Error: ${it.message}",
                    Toast.LENGTH_SHORT).show()
            }
    }



}