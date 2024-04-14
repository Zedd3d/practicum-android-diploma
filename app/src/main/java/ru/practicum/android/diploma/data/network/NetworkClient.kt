package ru.practicum.android.diploma.data.network

interface NetworkClient {
    suspend fun doRequest(query: Map<String, String>): Response

    suspend fun doRequestById(id: String): Response

    suspend fun getAreas(): Response

    suspend fun getAreasById(id: String): Response
}
