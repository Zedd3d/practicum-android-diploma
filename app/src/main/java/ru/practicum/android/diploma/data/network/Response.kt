package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.data.dto.IndustryDto

open class Response {
    var resultCode = 0
    var industriesList: List<IndustryDto> = emptyList()
}
