package com.example.sportteams.ui

import androidx.lifecycle.ViewModel
import com.example.sportteams.data.DataSource
import com.example.sportteams.model.SportCategories
import com.example.sportteams.model.Team
import com.example.sportteams.ui.utils.Destination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SportTeamsViewModel : ViewModel() {
    private val _uiState =
        MutableStateFlow(
            SportTeamsUiState(
                Destination.CATEGORY_LIST,
                SportCategories.SOCCER,
                DataSource.teams[SportCategories.SOCCER]!!,
                DataSource.teams[SportCategories.SOCCER]!![0],
            ),
        )
    val uiState: StateFlow<SportTeamsUiState> = _uiState.asStateFlow()

    fun updateCurrentCategory(category: SportCategories) {
        _uiState.update { currentState ->
            currentState.copy(
                currentCategory = category,
                currentTeams = DataSource.teams[category]!!,
                currentTeam = DataSource.teams[category]!![0],
            )
        }
    }

    fun updateCurrentTeam(team: Team) {
        _uiState.update { currentState ->
            currentState.copy(
                currentTeam = team,
            )
        }
    }
}
