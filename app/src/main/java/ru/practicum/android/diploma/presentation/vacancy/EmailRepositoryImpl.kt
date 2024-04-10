package ru.practicum.android.diploma.presentation.vacancy

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import ru.practicum.android.diploma.domain.models.Email
import javax.inject.Inject

class EmailRepositoryImpl @Inject constructor(
    private val appContext: Context
) : EmailRepository {
    override fun sendEmail(email: Email) {
        val sendEmailIntent = Intent().apply {
            data = Uri.parse("mail_to:")
            action = Intent.ACTION_SENDTO
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email.emailAddress))
            putExtra(Intent.EXTRA_TEXT, email.text)
            putExtra(Intent.EXTRA_SUBJECT, email.subject)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            Intent.createChooser(this, null)
        }
        ContextCompat.startActivity(appContext, sendEmailIntent, null)
    }

    override fun shareLink(link: String) {
        val shareLinkIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plane"
            putExtra(Intent.EXTRA_TEXT, link)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            Intent.createChooser(this, null)
        }
        ContextCompat.startActivity(appContext, shareLinkIntent, null)
    }
}
