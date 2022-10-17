package com.most4dev.acquaintanceapp.managers

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.BitmapFactory

import android.provider.MediaStore

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream
import android.content.Context

class PhotoManager {

    companion object {

        @SuppressLint("LongLogTag")
        fun savePhoto(data: Intent, activity: Activity): Bitmap {
            val selectedImage: Uri = data.data!!
            val filePath = arrayOf(MediaStore.Images.Media.DATA)
            val c: Cursor =
                activity.contentResolver.query(
                    selectedImage,
                    filePath,
                    null,
                    null,
                    null
                )!!
            c.moveToFirst()
            val columnIndex: Int = c.getColumnIndex(filePath[0])
            val picturePath: String = c.getString(columnIndex)
            c.close()
            var thumbnail = BitmapFactory.decodeFile(picturePath)
            thumbnail = getResizedBitmap(thumbnail, 400)
            return thumbnail
        }

        fun bitMapToString(userImage1: Bitmap): String? {
            val byteArrayOutputStream = ByteArrayOutputStream()
            userImage1.compress(Bitmap.CompressFormat.PNG, 60, byteArrayOutputStream)
            val b: ByteArray = byteArrayOutputStream.toByteArray()
            return Base64.encodeToString(b, Base64.DEFAULT)
        }

        fun stringToBitmap(dataBitmap: String): Bitmap {
            val bitmapByte = Base64.decode(dataBitmap, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(bitmapByte, 0, bitmapByte.size)
        }

        private fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap? {
            var width = image.width
            var height = image.height
            val bitmapRatio = width.toFloat() / height.toFloat()
            if (bitmapRatio > 1) {
                width = maxSize
                height = (width / bitmapRatio).toInt()
            } else {
                height = maxSize
                width = (height * bitmapRatio).toInt()
            }
            return Bitmap.createScaledBitmap(image, width, height, true)
        }

        fun checkEqualsBitmap(context: Context, bitmap1: Bitmap, drawableID: Int): Boolean{
            val bitmap2 = BitmapFactory.decodeResource(
                context.resources,
                drawableID
            )
            return bitmap1 == bitmap2
        }


    }

}