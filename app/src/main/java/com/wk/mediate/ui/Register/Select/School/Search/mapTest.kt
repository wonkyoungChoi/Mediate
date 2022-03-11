package com.wk.mediate.ui.Register.Select.School.Search

import android.R
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.wk.mediate.databinding.ActivityTestBinding
import net.daum.mf.map.api.MapView


class mapTest : AppCompatActivity() {
    private lateinit var binding: ActivityTestBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapView = MapView(this)

        val mapViewContainer = binding.mapView
        mapViewContainer.addView(mapView)

    }

}

