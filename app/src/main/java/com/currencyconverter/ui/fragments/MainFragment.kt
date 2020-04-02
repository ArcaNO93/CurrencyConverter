package com.currencyconverter.ui.fragments

import android.content.Context
import android.os.Bundle
import dagger.android.support.DaggerFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.currencyconverter.R
import com.currencyconverter.adapters.CurrenciesListAdapter
import com.currencyconverter.databinding.MainFragmentBinding
import com.currencyconverter.ui.viewModels.MainFragmentViewModel
import com.currencyconverter.utils.ViewModelFactory
import javax.inject.Inject

class MainFragment : DaggerFragment() {

    @Inject
    lateinit var mViewModelFactory: ViewModelFactory
    private lateinit var mViewModel: MainFragmentViewModel
    private lateinit var mBinding: MainFragmentBinding
    private lateinit var mCurrenciesListAdapter: CurrenciesListAdapter
    private lateinit var mLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(this, mViewModelFactory).get(MainFragmentViewModel::class.java)
        mLayoutManager = LinearLayoutManager(requireContext())
        mCurrenciesListAdapter = CurrenciesListAdapter(mViewModel, mLayoutManager)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, null, false)
        mBinding.currenciesList.adapter = mCurrenciesListAdapter
        mBinding.currenciesList.layoutManager = mLayoutManager
        mBinding.currenciesList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                hideKeyboardIfOpened()
            }
        })
        mViewModel.mCurrenciesListUpdate.observe(viewLifecycleOwner, Observer {
            mCurrenciesListAdapter.updateList()
        })

        return mBinding.root
    }

    override fun onResume() {
        super.onResume()
        mViewModel.getCurrenciesRates()
    }

    override fun onPause() {
        super.onPause()
        mViewModel.unsubscribe()
    }

    fun hideKeyboardIfOpened() {
        val imm by lazy {
            context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        }
        val windowHeightMethod = InputMethodManager::class.java.getMethod("getInputMethodWindowVisibleHeight")
        val height = windowHeightMethod.invoke(imm) as Int
        if(height > 0)
            imm.hideSoftInputFromWindow(view!!.windowToken, 0)
    }
}
