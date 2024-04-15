package ru.practicum.android.diploma.presentation.vacancy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.favorites.api.FavoritesInteractor
import ru.practicum.android.diploma.domain.general.models.ResponseState
import ru.practicum.android.diploma.domain.impl.SearchVacanciesByIdUseCase
import ru.practicum.android.diploma.presentation.vacancy.models.VacancyViewState
import java.io.IOException
import javax.inject.Inject

class VacancyViewModel @Inject constructor(
    private val vacancyId: String,
    private val searchVacanciesByIdUseCase: SearchVacanciesByIdUseCase,
    private val emailRepository: EmailRepository,
    private val favoritesInteractor: FavoritesInteractor
) : ViewModel() {
    private val state = MutableLiveData<VacancyViewState>()

    fun observeUi(): LiveData<VacancyViewState> = state

    var isFav: Boolean = false

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            state.postValue(VacancyViewState.Loading)
            val isFavorite = isFavorite(vacancyId)
            @Suppress("SwallowedException")
            try {
                val response = searchVacanciesByIdUseCase(vacancyId)

                when (response) {
                    is ResponseState.ContentVacancyDetail -> {
                        if (isFavorite) favoritesInteractor.insertDbVacanciToFavorite(response.vacancyDetail)
                        state.postValue(VacancyViewState.Content(response.vacancyDetail, isFavorite))
                    }

                    else -> if (isFavorite) loadFromFavorites()
                }
            } catch (e: IOException) {
                state.postValue(VacancyViewState.ServerError)
            }
        }
    }

    fun clickFavorite() {
        viewModelScope.launch {
            var isFavorite = false
            if (isFavorite(vacancyId)) {
                deleteFavVac()
            } else {
                setFavorite()
                isFavorite = true
            }

            val currentValue = state.value

            state.postValue(
                when (currentValue) {
                    is VacancyViewState.Content -> VacancyViewState.Content(currentValue.vacancyDetail, isFavorite)
                    else -> VacancyViewState.Loading
                }
            )
        }
    }

    fun shareVacancy() {
        val currentVal = state.value
        when (currentVal) {
            is VacancyViewState.Content -> currentVal.vacancyDetail.alternateUrl?.let { emailRepository.shareLink(it) }
            else -> {
                null
            }
        }
    }

    fun setFavorite() {
        viewModelScope.launch {
            val valueState = state.value
            when (valueState) {
                is VacancyViewState.Content -> favoritesInteractor.insertDbVacanciToFavorite(valueState.vacancyDetail)
                else -> null
            }
        }
    }

    suspend fun loadFromFavorites() {
        val res = favoritesInteractor.loadFavoriteVacancy(vacancyId)
        if (res == null) {
            state.postValue(VacancyViewState.ServerError)
        } else {
            state.postValue(VacancyViewState.Content(res, true))
        }
    }

    fun deleteFavVac() {
        viewModelScope.launch {
            favoritesInteractor.deleteDbVacanciFromFavorite(vacancyId)
        }
    }

    suspend fun isFavorite(id: String): Boolean {
        isFav = favoritesInteractor.isFavorite(id)
        return isFav
    }
}
