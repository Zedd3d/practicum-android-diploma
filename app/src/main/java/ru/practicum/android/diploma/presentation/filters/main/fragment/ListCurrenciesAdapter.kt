package ru.practicum.android.diploma.presentation.filters.main.fragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.Currency
import ru.practicum.android.diploma.util.SalaryUtil

class ListCurrenciesAdapter(
    context: Context): ArrayAdapter<Currency>(context, R.layout.currency_item, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        //posField = position;
        return getCustomView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, getCustomView(position, convertView, parent), parent)
    }

    fun getCustomView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        val currency = getItem(position)
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                .inflate(R.layout.currency_item, null)
        }

        convertView?.let {
            val textFieldId = it.findViewById<View>(R.id.tvTitleCurrency) as TextView
            textFieldId.setText("${currency?.name?:""} ${currency?.sign?:""}")
        }

        return convertView!!
    }

    companion object{
        val list = listOf<Currency>(
            Currency(0,"<Empty>",""),
            Currency(1,"RUR","(${SalaryUtil.getSymbolFromCurrency("RUR")})"),
            Currency(2,"EUR","(${SalaryUtil.getSymbolFromCurrency("EUR")})"),
            Currency(3,"KZT","(${SalaryUtil.getSymbolFromCurrency("KZT")})"),
            Currency(4,"AZN","(${SalaryUtil.getSymbolFromCurrency("AZN")})"),
            Currency(5,"USD","(${SalaryUtil.getSymbolFromCurrency("USD")})"),
            Currency(6,"BYR","(${SalaryUtil.getSymbolFromCurrency("BYR")})"),
            Currency(7,"GEL","(${SalaryUtil.getSymbolFromCurrency("GEL")})"),
            Currency(8,"UAH","(${SalaryUtil.getSymbolFromCurrency("UAH")})"),
            Currency(9,"UZS","(${SalaryUtil.getSymbolFromCurrency("UZS")})")
        )
    }
}

