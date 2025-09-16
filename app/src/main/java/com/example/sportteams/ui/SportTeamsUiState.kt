package com.example.sportteams.ui

import com.example.sportteams.data.DataSource
import com.example.sportteams.model.SportCategories
import com.example.sportteams.model.Team
import com.example.sportteams.ui.utils.Destination

data class SportTeamsUiState(
    val currentScreen: Destination = Destination.CATEGORY_LIST,
    val currentCategory: SportCategories = SportCategories.SOCCER,
    val currentTeams: List<Team> = DataSource.teams[currentCategory]!!,
    val currentTeam: Team = currentTeams[0],
)
