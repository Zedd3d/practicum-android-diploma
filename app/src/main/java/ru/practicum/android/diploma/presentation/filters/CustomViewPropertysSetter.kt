package ru.practicum.android.diploma.presentation.filters

import android.annotation.SuppressLint
import androidx.core.view.isVisible
import ru.practicum.android.diploma.databinding.FilterCategotyElementBinding

object CustomViewPropertysSetter {
    @SuppressLint("ResourceAsColor")
    fun setViewPropertys(v: FilterCategotyElementBinding, textValue: String) {
        if (textValue.isEmpty()) {
            v.smallTextBlock.isVisible = false
            v.standardTextBlock.text = v.smallTextBlock.text
            v.standardTextBlock.isEnabled = false
            v.ivBtnGo.isVisible = true
            v.ivBtnClear.isVisible = false
        } else {
            v.smallTextBlock.isVisible = true
            v.standardTextBlock.text = textValue
            v.standardTextBlock.isEnabled = true
            v.ivBtnGo.isVisible = false
            v.ivBtnClear.isVisible = true
        }
    }
}
