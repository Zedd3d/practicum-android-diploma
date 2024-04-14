package ru.practicum.android.diploma.data.network.models

import ru.practicum.android.diploma.data.dto.VacancyAreaDto
import ru.practicum.android.diploma.data.network.Response

data class AreasResponse(
    val items: List<VacancyAreaDto>,
) : Response()
