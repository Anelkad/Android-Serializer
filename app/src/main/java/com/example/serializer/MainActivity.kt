package com.example.serializer

import PolymorphicItem
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.serializer.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var adapter = Adapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvList.adapter = adapter
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
                val list = response.body()?.data
                adapter.submitList(list?.map {
                    Json.decodeFromJsonElement<PolymorphicItem>(it)
                })
            }
        }
        return super.onCreateView(parent, name, context, attrs)
    }
}