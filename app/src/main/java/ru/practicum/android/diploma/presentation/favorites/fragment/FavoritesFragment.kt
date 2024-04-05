package ru.practicum.android.diploma.presentation.favorites.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.App
import ru.practicum.android.diploma.databinding.FragmentFavoritesBinding
import ru.practicum.android.diploma.presentation.Factory
import ru.practicum.android.diploma.presentation.favorites.state.FavoritesState
import ru.practicum.android.diploma.presentation.favorites.viewmodel.FavoritesViewModel

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private val viewModel by viewModels<FavoritesViewModel> {
        Factory {
            App.appComponent.favoriteslComponent().viewModel()
        }
    }

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    //private val favoriteVacancies = ArrayList<Vacancy>()
    //private val favoriteVacancyAdapter = VacanciesAdapter(favoriteVacancies){}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFavoritesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun updateFavoriteStatus(state: FavoritesState) {
        binding.apply {
            when (state) {
                is FavoritesState.Content -> {
                    binding.ivFavorite.isVisible = false
                    binding.tvFavorite.isVisible = false
                    binding.rvFavorite.isVisible = true



                }
                else -> {
                    binding.ivFavorite.isVisible = true
                    binding.tvFavorite.isVisible = true
                    binding.rvFavorite.isVisible = false
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)?.isVisible = true
    }
}
