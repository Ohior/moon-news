package play.appexample.moonnews.utils

import android.content.Context
import android.util.Log
import android.widget.Toast

object Constant {
    private const val SOURCE_ID = "cnn"
    private val SOURCE = listOf("bbc-news","cnn","fox-news","google-news ").random()
    val API_BASE_URL = "https://saurav.tech/NewsAPI/everything/$SOURCE.json"
    fun debugMessage(message:String, tag:String = "DEBUG_MESSAGE"){
        Log.e(tag, message )
    }

    fun showToast(context: Context, message: String){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}