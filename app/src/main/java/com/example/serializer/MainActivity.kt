package com.example.serializer

import PolymorphicItem
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        lifecycleScope.launch {
            val response = ApiClient.service.getOfferList()
            if (response.isSuccessful) {
                Log.e("qwerty TAG", response.body()?.data.toString())
                val list = response.body()?.data
                val quote = "\""
                val parsedItem = list?.map {
                    var json = quote.plus(Gson().toJson(it))
                    json = json.plus(quote)
                    Log.e("qwerty TAG",
                        Json.decodeFromString<PolymorphicItem>(json).toString())
                }
            }
        }
        return super.onCreateView(parent, name, context, attrs)
    }
}