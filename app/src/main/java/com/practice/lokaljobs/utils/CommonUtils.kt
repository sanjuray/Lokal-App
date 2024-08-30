package com.practice.lokaljobs.utils

import android.content.res.Resources
import android.util.Log
import com.practice.lokaljobs.utils.constants.Constants
import java.text.SimpleDateFormat
import java.util.Locale

object CommonUtils {

    fun networkLogs(statusCode: Int, responseMessage: String){
        Log.d("SGNETWORK","Status Code: $statusCode : $responseMessage")
    }

    fun detectLogs(msg: String){
        Log.d("SGDETECTIVE",msg)
    }

    fun getNAIfEmpty(str: String) = str.ifEmpty {"N/A"}

    fun getDateFormatted(date: String): String{
        var outputString = ""
        try {
            val inputFormat = SimpleDateFormat(Constants.DATE_FORMAT_FROM_SERVER, Locale.ENGLISH)
            val outputFormat = SimpleDateFormat(Constants.DATE_FORMAT_TO_DISPLAY, Locale.ENGLISH)
            val inputDate = date.split("T")[0]
            outputString = outputFormat.format(inputFormat.parse(inputDate)!!)
        }catch (e: Exception){
            e.errorLogs("getNAIfEmpty@CommonUtils")
        }
        return outputString
    }

    fun convertDpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }

    fun isStringEmptyOrNull(str: String?) = str.isNullOrEmpty()

}