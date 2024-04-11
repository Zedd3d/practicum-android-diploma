package ru.practicum.android.diploma.data.network

interface NetworkClient {
    suspend fun doRequest(query: Map<String, String>): Response

    suspend fun doRequestById(id: String): Response
}
