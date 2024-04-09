package ru.practicum.android.diploma.presentation.filters

import dagger.Provides
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.network.Resource
import ru.practicum.android.diploma.domain.filters.industry.models.Industry

interface FIlterInteractor {
    suspend fun getIndustries(): Flow<Resource<List<Industry>>>
}
