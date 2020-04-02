package com.currencyconverter.adapters

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.currencyconverter.R
import com.currencyconverter.data.dto.BaseCurrency
import com.currencyconverter.data.dto.CurrenciesList
import com.currencyconverter.data.dto.Currency
import com.currencyconverter.databinding.CurrencyRowBinding
import com.currencyconverter.ui.viewModels.MainFragmentViewModel
import com.squareup.picasso.Picasso

class CurrenciesListAdapter(
    private val mViewModel: MainFragmentViewModel,
    private val mLayoutManager: LinearLayoutManager
) : RecyclerView.Adapter<CurrenciesListAdapter.CurrenciesListViewHolder>() {

    private var mCurrenciesList: MutableList<Currency> = CurrenciesList.currenciesList
    private lateinit var mSmoothScroller: RecyclerView.SmoothScroller
    private lateinit var mView: RecyclerView
    private lateinit var mContext: Context

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrenciesListViewHolder {
        val binding = CurrencyRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        if(!::mContext.isInitialized)
            mContext = parent.context
        if(!::mSmoothScroller.isInitialized)
            mSmoothScroller = object : LinearSmoothScroller(parent.context) {
            override fun getVerticalSnapPreference(): Int {
                return SNAP_TO_START
            }
        }
        return CurrenciesListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrenciesListViewHolder, position: Int) {
        holder.bind(mCurrenciesList[holder.adapterPosition])
    }

    override fun getItemCount(): Int {
        return mCurrenciesList.size
    }

    fun updateList() {
        mCurrenciesList.forEachIndexed { index, currency ->
            notifyItemChanged(index, currency.value)
        }
    }

    fun makeFirst(position: Int) {
        mCurrenciesList[0].base = false
        mCurrenciesList[position].base = true
        val tmp = mCurrenciesList[position]
        mCurrenciesList.removeAt(position)
        mCurrenciesList.add(0, tmp)
        if (mLayoutManager.findFirstCompletelyVisibleItemPosition() != 0) {
            mSmoothScroller.targetPosition = 0
            mLayoutManager.startSmoothScroll(mSmoothScroller)
        } else {
            notifyItemMoved(position, 0)
        }
    }

    override fun getItemId(position: Int): Long =
        mCurrenciesList[position].country.hashCode().toLong()

    inner class CurrenciesListViewHolder(private val binding: CurrencyRowBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.valueTextField.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if(s.toString().length == 1 && (s.toString() == "." || s.toString() == "0"))
                        s!!.clear()
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (binding.currency!!.base && !mView.isComputingLayout) {
                        val result = s.toString()
                        BaseCurrency.value = when {
                            result.isEmpty() -> 0.0
                            result == "." -> 0.0
                            else -> result.toDouble()
                        }
                        mViewModel.forceListUpdate()
                        notifyDataSetChanged()
                        binding.valueTextField.setSelection(binding.valueTextField.text!!.length)
                    }
                }
            })
        }

        fun bind(currency: Currency) {
            binding.currency = currency
            binding.currencyCountryFullName = java.util.Currency.getInstance(currency.country).displayName
            Picasso
                .get()
                .load(R.drawable.emoji_money_main)
                .into(binding.countryFlag)
            binding.valueTextField.apply {
                isEnabled = binding.currency!!.base
                setOnFocusChangeListener { _, hasFocus ->
                    binding.valueTextField.post {
                        if (hasFocus) {
                            binding.valueTextField.setSelection(binding.valueTextField.text!!.length)
                        }
                    }
                }
                setOnClickListener {
                    binding.valueTextField.setSelection(binding.valueTextField.text!!.length)
                }
            }
            binding.currencyRowMainLayout.setOnClickListener {
                binding.valueTextField.requestFocus()
                binding.valueTextField.isEnabled = false
                mViewModel.forceListUpdate()
                mViewModel.unsubscribe()
                makeFirst(adapterPosition)
                BaseCurrency.country = binding.currency!!.country
                BaseCurrency.value = binding.currency!!.value.toDouble()
                mViewModel.getCurrenciesRates()
                binding.valueTextField.isEnabled = true
            }
            binding.executePendingBindings()
        }
    }
}