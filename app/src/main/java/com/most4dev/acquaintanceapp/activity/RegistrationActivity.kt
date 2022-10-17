package com.most4dev.acquaintanceapp.activity

import android.Manifest
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.most4dev.acquaintanceapp.R
import kotlinx.android.synthetic.main.activity_registration.*
import android.provider.MediaStore
import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.most4dev.acquaintanceapp.models.PersonModel
import android.graphics.drawable.BitmapDrawable
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.Toast
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import com.most4dev.acquaintanceapp.managers.*
import androidx.lifecycle.ViewModelProvider
import com.most4dev.acquaintanceapp.Config
import com.most4dev.acquaintanceapp.MainActivity
import com.most4dev.acquaintanceapp.utils.snackBar
import com.most4dev.acquaintanceapp.viewModels.AcquaintanceViewModel

class RegistrationActivity : AppCompatActivity() {

    private lateinit var sharedPreferenceManager: SharedPreferenceManager
    private lateinit var acquaintanceViewModel: AcquaintanceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        sharedPreferenceManager = SharedPreferenceManager(this)
        acquaintanceViewModel = ViewModelProvider(this)[AcquaintanceViewModel::class.java]
        setMethods()

    }

    private fun setMethods() {
        val text =
            getString(R.string.agree_terms_condition) + "<a href=\"" + Config.termsURL + "\">" +
                    getString(R.string.terms_condition) + "</a>"
        checkBoxTermsCondition.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_COMPACT)
        checkBoxTermsCondition.movementMethod = LinkMovementMethod.getInstance()

        profile_image.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                == PackageManager.PERMISSION_GRANTED
            ) {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                resultLaunchChooseGallery.launch(intent)
            } else {
                resultPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                )
            }
        }

        buttonRegister.setOnClickListener {
            buttonRegister.isEnabled = false
            if (checkPlaces()) {
                if (AgeManager.comingOfAge(inputTextBirthday.text.toString()) &&
                    sharedPreferenceManager.getAgeOfComing()
                ) {
                    val selectedId: Int = radioGroupGender.checkedRadioButtonId

                    val selectedRadioButton = findViewById<View>(selectedId) as RadioButton
                    val personModel = PersonModel(
                        inputEditTextName.text.toString(),
                        selectedRadioButton.text.toString(),
                        PhotoManager.bitMapToString(
                            (profile_image.drawable as BitmapDrawable)
                                .bitmap
                        )!!,
                        inputTextBirthday.text.toString(),
                        inputEditAbout.text.toString()
                    )
                    sharedPreferenceManager.savePersonModel(
                        personModel
                    )
                    sharedPreferenceManager.setCompleteRegister(true)
                    acquaintanceViewModel.sendPerson(personModel)
                    acquaintanceViewModel.getWebViewLoad().observe(this, {
                        if (!it) {
                            //TODO start activity
                        } else {
                            startActivity(
                                Intent(
                                    this,
                                    MainActivity::class.java
                                )
                            )
                            finish()
                        }
                    })

                } else {
                    sharedPreferenceManager.setAgeOfComing(false)
                    Toast.makeText(
                        this,
                        R.string.age_of_coming,
                        Toast.LENGTH_LONG
                    ).show()
                }

            } else {
                buttonRegister.isEnabled = true
                showToastValidData()
            }
        }

        inputTextBirthday.setOnClickListener {
            CustomDialogManager.createDateBirthday(
                this,
                inputTextBirthday
            )
        }

        inputEditTextName.addTextChangedListener(
            TextWatcherManager.setTextWatcher(
                4,
                inputEditTextName,
                getString(R.string.more_4)
            )
        )

        inputEditAbout.addTextChangedListener(
            TextWatcherManager.setTextWatcher(
                30,
                inputEditAbout,
                getString(R.string.more_70)
            )
        )

    }

    private fun showToastValidData() {
        var string = ""
        if (inputEditTextName.text!!.isEmpty()) {
            string += getString(R.string.name_empty) + "\n"
        }
        if (inputEditTextName.text!!.length < 4) {
            string += getString(R.string.name_length) + "\n"
        }
        if (inputTextBirthday.text!!.isEmpty()) {
            string += getString(R.string.birthday_empty) + "\n"
        }
        if (inputEditAbout.text!!.isEmpty()) {
            string += getString(R.string.about_empty) + "\n"
        }
        if (inputEditAbout.text!!.length < 30) {
            string += getString(R.string.about_length) + "\n"
        }
        if (!checkBoxTermsCondition.isChecked) {
            string += getString(R.string.checkBox_checked) + "\n"
        }
        if (profile_image.drawable == null ||
            (profile_image.drawable as BitmapDrawable).bitmap == null ||
            PhotoManager.checkEqualsBitmap(
                this,
                (profile_image.drawable as BitmapDrawable).bitmap,
                R.drawable.photo_placeholder
            )
        ) {
            string += getString(R.string.photo_not_selected) + "\n"
        }
        Toast.makeText(
            this,
            string.substring(0, string.length - 1),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun checkPlaces(): Boolean {
        return inputEditTextName.text!!.isNotEmpty() &&
                inputEditTextName.text!!.length > 4 &&
                inputTextBirthday.text!!.isNotEmpty() &&
                inputEditAbout.text!!.isNotEmpty() &&
                inputEditAbout.text!!.length > 30 &&
                checkBoxTermsCondition.isChecked &&
                profile_image.drawable != null &&
                (profile_image.drawable as BitmapDrawable).bitmap != null &&
                !PhotoManager.checkEqualsBitmap(
                    this,
                    (profile_image.drawable as BitmapDrawable).bitmap,
                    R.drawable.photo_placeholder
                )
    }

    private val resultLaunchChooseGallery: ActivityResultLauncher<Intent> =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                profile_image.setImageBitmap(PhotoManager.savePhoto(result.data!!, this))
            }
        }


    private val resultPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        )
        { permissions ->
            var permissionBoolean = false

            permissions.entries.forEach {
                if (it.value) {
                    permissionBoolean = true
                } else {
                    permissionBoolean = false
                    constraint_register.snackBar(
                        getString(R.string.permission_denied) + it.key
                    )
                    return@forEach
                }
            }

            if (permissionBoolean) {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                resultLaunchChooseGallery.launch(intent)
            }

        }
}