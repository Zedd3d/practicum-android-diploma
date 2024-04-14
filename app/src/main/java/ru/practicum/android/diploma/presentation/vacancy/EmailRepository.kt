package ru.practicum.android.diploma.presentation.vacancy

import ru.practicum.android.diploma.domain.models.Email

interface EmailRepository {
    fun shareLink(link: String)
    fun sendEmail(email: Email)
}
