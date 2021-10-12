package com.jsx.sprout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jsx.applib.base.BaseVmActivity

class MainActivity : BaseVmActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun init(savedInstanceState: Bundle?) {
    }

    override fun getLayoutId() = R.layout.activity_main
}