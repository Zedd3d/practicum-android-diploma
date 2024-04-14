package ru.practicum.android.diploma.data.network

import android.content.Context
import android.net.ConnectivityManager
import retrofit2.HttpException
import ru.practicum.android.diploma.data.dto.VacancyAreaDto
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
        if (!isOnline(context)) return Response().apply { resultCode = -1 }
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

    override suspend fun getAreas(): Response {
        if (!isOnline(context)) return Response().apply { resultCode = -1 }

        return try {
            val response = headHunterService.getAreas()

            val resp = AreasResponse(response)

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

    private fun addAreas(vacancyAreaDto: VacancyAreaDto, list: MutableList<VacancyAreaDto>) {
        list.add(vacancyAreaDto)
        vacancyAreaDto.areas.forEach {
            addAreas(it, list)
        }
    }

    override suspend fun getAreasById(id: String): Response {
        if (!isOnline(context)) return Response().apply { resultCode = -1 }

        return try {

            val response = when (id) {
                "" -> {
                    val result = mutableListOf<VacancyAreaDto>()
                    headHunterService.getAreas().forEach {
                        addAreas(it, result)
                    }
                    result
                }

                else -> {
                    val result = mutableListOf<VacancyAreaDto>()
                    headHunterService.getAreaById(id).areas.forEach {
                        addAreas(it, result)
                    }
                    result
                }
            }

            val resp = AreasResponse(response)

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

    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities != null
    }

}
