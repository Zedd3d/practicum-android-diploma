package ru.practicum.android.diploma.domain.filters.industry.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.Constants
import ru.practicum.android.diploma.data.IndustriesRequest
import ru.practicum.android.diploma.data.converters.FiltersConvertor
import ru.practicum.android.diploma.data.network.Resource
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.domain.filters.industry.api.IndustryRepository
import ru.practicum.android.diploma.domain.filters.industry.models.Industry
import javax.inject.Inject

class IndustryRepositoryImpl @Inject constructor(
    private val networkClient: RetrofitNetworkClient
) : IndustryRepository {
    override suspend fun getIndustries(): Flow<Resource<List<Industry>>> = flow {
        val response = networkClient.doIndustryRequest(IndustriesRequest)
        when (response.resultCode) {
            Constants.NO_CONNECTIVITY_MESSAGE -> {
                emit(Resource(data = null, code = Constants.NO_CONNECTIVITY_MESSAGE))
            }

            Constants.SUCCESS_RESULT_CODE -> {
                emit(
                    Resource(
                        data = response.industriesList.map { industryDto ->
                            FiltersConvertor.convertorToIndustry(
                                industryDto
                            )
                        },
                        code = Constants.SUCCESS_RESULT_CODE
                    )
                )
            }

            else -> {
                emit(Resource(data = null, code = Constants.SERVER_ERROR))
            }
        }
    }
}
