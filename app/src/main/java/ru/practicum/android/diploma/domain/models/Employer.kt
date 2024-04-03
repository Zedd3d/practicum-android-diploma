package ru.practicum.android.diploma.domain.models

data class Employer(
    val id: String,
    val logoUrls: String?,
    val name: String,
    val trusted: Boolean,
    val vacanciesUrl: String
)
