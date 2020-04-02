package com.currencyconverter.ui.activities

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.currencyconverter.R
import com.currencyconverter.ui.viewModels.MainActivityViewModel
import com.currencyconverter.utils.ViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var mViewModelFactory: ViewModelFactory
    private lateinit var mViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        mViewModel = ViewModelProvider(this, mViewModelFactory).get(MainActivityViewModel::class.java)
    }

}
