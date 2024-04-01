package com.vegasega.myapplication.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.Build
import android.text.TextUtils
import android.text.format.Formatter
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Utils {

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

        fun dateformat(strDate : String): String? {
            val firstApiFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val date = LocalDate.parse(strDate , firstApiFormat)
            val formatedDate = date.dayOfMonth.toString() + " " + date.month.toString().substring(0, 3) + " , " + date.year.toString()
            return formatedDate
        }
    }
}