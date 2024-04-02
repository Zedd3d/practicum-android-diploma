package ru.practicum.android.diploma.presentation.favorites.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.App
import ru.practicum.android.diploma.databinding.FragmentFavoritesBinding
import ru.practicum.android.diploma.presentation.Factory
import ru.practicum.android.diploma.presentation.favorites.view_model.FavoritesViewModel
import ru.practicum.android.diploma.presentation.general.VacanciesAdapter

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private val viewModel by viewModels<FavoritesViewModel> {
        Factory{
            App.appComponent.favoriteslComponent().viewModel()
        }
    }

    private val binding by viewBinding(FragmentFavoritesBinding::bind)
    private lateinit var adapter: VacanciesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
