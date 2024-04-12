package ru.practicum.android.diploma.data.dto

import ru.practicum.android.diploma.domain.models.Phone

data class ContactsDto(
    val email: String,
    val name: String,
    val phones: List<PhoneDto>
)
