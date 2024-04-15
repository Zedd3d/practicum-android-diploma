package ru.practicum.android.diploma.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.telephony.DisconnectCause
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.data.Constants
import ru.practicum.android.diploma.data.IndustriesRequest
import java.io.IOException
import javax.inject.Inject

class RetrofitNetworkClient @Inject constructor(
    private val headHunterService: HeadHunterService,
    private val context: Context
) : NetworkClient {

    companion object {
        const val HTTP_OK = 200
        const val HTTP_ERROR = 500
    }

    override suspend fun doRequest(query: Map<String, String>): Response {
        if (!isOnline(context)) return Response().apply { resultCode = Constants.NO_CONNECTIVITY_MESSAGE }

        @Suppress("SwallowedException")
        return try {
            val resp = headHunterService.vacancies(query)
            resp.apply {
                resultCode = HTTP_OK
            }
        } catch (e: IOException) {
            Response().apply {
                resultCode = HTTP_ERROR
            }
        } catch (e: HttpException) {
            Response().apply { resultCode = e.code() }
        }
    }

    override suspend fun doRequestById(id: String): Response {
        if (!isOnline(context)) return Response().apply { resultCode = Constants.NO_CONNECTIVITY_MESSAGE }
        @Suppress("SwallowedException")
        return try {
            val resp = headHunterService.getVacancyById(id)
            resp.apply {
                resultCode = HTTP_OK
            }
        } catch (e: IOException) {
            Response().apply {
                resultCode = HTTP_ERROR
            }
        } catch (e: HttpException) {
            Response().apply { resultCode = e.code() }
        }
    }

    override suspend fun doIndustryRequest(dto: Any): Response {
        if (!isOnline(context)) return Response().apply { resultCode = Constants.NO_CONNECTIVITY_MESSAGE }
        val response = Response()
        return try {
            when (dto) {
                is IndustriesRequest -> withContext(Dispatchers.IO) {
                    val result = headHunterService.filterIndustry()
                    response.apply {
                        industriesList = result
                        resultCode = HTTP_OK
                    }
                }

                else -> response.apply { resultCode = Constants.SERVER_ERROR }
            }
        } catch (exception: HttpException) {
            Response().apply { resultCode = exception.code() }
        }
    }
    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities != null
    }
}
