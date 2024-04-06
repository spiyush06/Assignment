package com.vegasega.myapplication.utils

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class Utility {
    companion object {

        fun checkForInternet(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                val network = connectivityManager.activeNetwork ?: return false

                val activeNetwork =
                    connectivityManager.getNetworkCapabilities(network) ?: return false

                return when {
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                    else -> false
                }
            } else {
                @Suppress("DEPRECATION") val networkInfo =
                    connectivityManager.activeNetworkInfo ?: return false
                @Suppress("DEPRECATION")
                return networkInfo.isConnected
            }
        }

        fun showToastShort(context: Context, message: String) {
            if (!message.isBlank() && message.isNotBlank()) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun dateformat(strDate : String): String? {
            val firstApiFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale("hi", "IN"))
            val date = LocalDate.parse(strDate , firstApiFormat)
            val formatedDate = date.dayOfMonth.toString() + " " + date.month.toString().substring(0, 3) + ", " + date.year.toString()

            return formatedDate
        }

        fun String.translateToHindi(onSuccess: (String) -> Unit, onFailure: () -> Unit) {
            val options = TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.ENGLISH)
                .setTargetLanguage(TranslateLanguage.HINDI)
                .build()

            val translator = Translation.getClient(options)

            translator.translate(this)
                .addOnSuccessListener(OnSuccessListener { translatedText ->
                    onSuccess(translatedText)
                    translator.close()
                })
                .addOnFailureListener(OnFailureListener {
                    onFailure()
                    translator.close()
                })
        }

        fun boldText(strString: String) : SpannableString{
            val boldText = strString.substringAfter(" ")

            val spannable = SpannableString(strString)
            val startIndex = strString.indexOf(boldText)
            val endIndex = startIndex + boldText.length

            spannable.setSpan(StyleSpan(Typeface.BOLD), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannable.setSpan(ForegroundColorSpan(Color.RED), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            return spannable
        }

        fun boldTextDate(strString: String) : SpannableString{
            val boldText = strString.substringAfter(" ").substringAfter(" ")

            val spannable = SpannableString(strString)

            val startIndex = strString.indexOf(boldText)
            val endIndex = startIndex + boldText.length

            spannable.setSpan(StyleSpan(Typeface.BOLD), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            return spannable
        }

    }
}