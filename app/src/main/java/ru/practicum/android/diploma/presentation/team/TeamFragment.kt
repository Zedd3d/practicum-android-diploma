package ru.practicum.android.diploma.presentation.team

import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.practicum.android.diploma.R

class TeamFragment : Fragment(R.layout.fragment_team) {

    override fun onResume() {
        super.onResume()
        activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)?.isVisible = true
    }
}
