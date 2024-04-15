package ru.practicum.android.diploma.data.network

import android.content.Context
import android.net.ConnectivityManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.data.network.models.AreasResponse
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
        if (!isOnline(context)) return Response().apply { resultCode = -1 }

        return withContext(Dispatchers.IO) {
            try {
                val resp = headHunterService.vacancies(query)
                resp.apply {
                    resultCode = HTTP_OK
                }
            } catch (e: IOException) {
                println(e)
                Response().apply {
                    resultCode = HTTP_ERROR
                }
            } catch (e: HttpException) {
                println(e)
                Response().apply { resultCode = e.code() }
            }
        }
    }

    override suspend fun doRequestById(id: String): Response {
        if (!isOnline(context)) return Response().apply { resultCode = -1 }
        return withContext(Dispatchers.IO) {
            try {
                val resp = headHunterService.getVacancyById(id)
                resp.apply {
                    resultCode = HTTP_OK
                }
            } catch (e: IOException) {
                println(e)
                Response().apply {
                    resultCode = HTTP_ERROR
                }
            } catch (e: HttpException) {
                println(e)
                Response().apply { resultCode = e.code() }
            }
        }
    }

    override suspend fun getAreas(): Response {
        if (!isOnline(context)) return Response().apply { resultCode = -1 }

        return try {
            val response = headHunterService.getAreas()

            val resp = AreasResponse(response)

            resp.apply {
                resultCode = HTTP_OK
            }
        } catch (e: IOException) {
            println(e)
            Response().apply {
                resultCode = HTTP_ERROR
            }
        } catch (e: HttpException) {
            println(e)
            Response().apply { resultCode = e.code() }
        }
    }


    override suspend fun getAreasById(id: String): Response {
        if (!isOnline(context)) return Response().apply { resultCode = -1 }

        return try {
            val response = when (id) {
                "" -> {
                    headHunterService.getAreas()
                }

                else -> {
                    listOf(headHunterService.getAreaById(id))
                }
            }

            val resp = AreasResponse(response)

            resp.apply {
                resultCode = HTTP_OK
            }
        } catch (e: IOException) {
            println(e)
            Response().apply {
                resultCode = HTTP_ERROR
            }
        } catch (e: HttpException) {
            println(e)
            Response().apply { resultCode = e.code() }
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
