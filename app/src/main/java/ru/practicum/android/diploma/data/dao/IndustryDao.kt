package ru.practicum.android.diploma.data.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.network.Resource
import ru.practicum.android.diploma.domain.filters.industry.models.Industry

//@Dao
//interface IndustryDao {
//    @Query("GET * FROM ")
//    suspend fun getIndustries(): Flow<Resource<List<Industry>>>
//}
