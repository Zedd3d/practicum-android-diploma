package ru.practicum.android.diploma.util

import android.content.Context
import android.icu.text.DecimalFormat
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Salary
import java.lang.StringBuilder

object SalaryUtil {

    fun formatSalary(context: Context, salary: Salary?): String {
        val text = StringBuilder()

        salary?.from?.let {
            text.append("от ${formatSalary(it)}")
        }

        salary?.to?.let {
            text.append(" до ${formatSalary(it)}")
        }

        if (text.isEmpty()) {
            text.append(context.getString(R.string.no_salary))
        } else {
            text.append(" ${getSymbolFromCurrency(salary?.currency)}")
        }

        return text.toString()
    }

    private fun getSymbolFromCurrency(currency: String?): String {
        return when (currency) {
            "RUR" -> {
                "₽"
            }

            "EUR" -> {
                "€"
            }

            "KZT" -> {
                "₸"
            }

            "AZN" -> {
                "\u20BC"
            }

            "USD" -> {
                "$"
            }

            "BYR" -> {
                "\u0042\u0072"
            }

            "GEL" -> {
                "\u20BE"
            }

            "UAH" -> {
                "\u20b4"
            }

            "UZS" -> {
                "Soʻm"
            }

            else -> {
                ""
            }
        }
    }

    private fun formatSalary(salary: Int): String {
        val df = DecimalFormat()
        df.isGroupingUsed = true
        df.groupingSize = 3
        return df.format(salary).replace(",", " ")
    }
}
