package com.goldouble.android.github.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.goldouble.android.github.R
import com.goldouble.android.github.databinding.ActivityMainBinding
import com.goldouble.android.github.retrofit.RetrofitService

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    // region lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
    // endregion
}