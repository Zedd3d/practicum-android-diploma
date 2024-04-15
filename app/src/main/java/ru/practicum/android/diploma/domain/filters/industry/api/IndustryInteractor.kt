package ru.practicum.android.diploma.domain.filters.industry.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.network.Resource
import ru.practicum.android.diploma.domain.filters.industry.models.Industry

interface IndustryInteractor {
    suspend fun getIndustries(): Flow<Resource<List<Industry>>>
}
