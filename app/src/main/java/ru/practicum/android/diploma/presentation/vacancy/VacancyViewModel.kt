package ru.practicum.android.diploma.presentation.vacancy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.favorites.api.FavoritesInteractor
import ru.practicum.android.diploma.domain.impl.VacanciesRepository
import ru.practicum.android.diploma.domain.models.VacancyDetail
import javax.inject.Inject

class VacancyViewModel @Inject constructor(
    private val vacancyId: String,
    private val repository: VacanciesRepository,
    private val emailRepository: EmailRepository,
    private val favoritesInteractor: FavoritesInteractor
) : ViewModel() {
    private val state = MutableLiveData<ViewState>()
    private var likeIndicator = MutableLiveData<Boolean>()
    private var vacancy: VacancyDetail? = null

    fun observeUi(): LiveData<ViewState> = state

    var isFav: Boolean = false

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            vacancy = repository.searchById(vacancyId)
            state.postValue(
                ViewState(
                    vacancy = vacancy,
                    isLoading = false,
                    isFavorite(vacancyId)
                )
            )
        }
    }

    fun clickFavorite() {
        viewModelScope.launch {
            if (isFavorite(vacancyId)) {
                deleteFavVac()
            } else {
                setFavorite()
            }
            loadData()
        }
    }

    fun shareVacancy() {
        emailRepository.shareLink("https://ekaterinburg.hh.ru/vacancy/${vacancy!!.id}")
    }

    fun setFavorite() {
        viewModelScope.launch {
            state.value?.vacancy?.let {
                favoritesInteractor.insertDbVacanciToFavorite(
                    it
                )
            }
        }
    }

    fun deleteFavVac() {
        viewModelScope.launch {
            state.value?.vacancy?.let {
                favoritesInteractor.deleteDbVacanciFromFavorite(it.id)
            }
        }
    }

    suspend fun isFavorite(id: String): Boolean {
        isFav = favoritesInteractor.isFavorite(id)
        likeIndicator.postValue(isFav)
        return isFav
    }
}

data class ViewState(
    val vacancy: VacancyDetail? = null,
    val isLoading: Boolean = true,
    val isFavorite: Boolean = false
)
