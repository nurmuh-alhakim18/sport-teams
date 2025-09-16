package com.example.sportteams.data

import androidx.compose.material3.Text
import com.example.sportteams.R
import com.example.sportteams.model.Category
import com.example.sportteams.model.SportCategories
import com.example.sportteams.model.Team
import kotlin.to

object DataSource {
    val categories = {
        listOf(
            Category(
                id = SportCategories.SOCCER,
                image = R.drawable.outline_sports_soccer_24,
                name = R.string.soccer
            ),
            Category(
                id = SportCategories.BASKETBALL,
                image = R.drawable.outline_sports_basketball_24,
                name = R.string.basketball
            ),
            Category(
                id = SportCategories.FOOTBALL,
                image = R.drawable.outline_sports_football_24,
                name = R.string.football
            ),
        )
    }
    val teams =
        mapOf(
            SportCategories.SOCCER to
                listOf(
                    Team(
                        name = R.string.real_madrid,
                        founded = R.string.real_madrid_founded,
                        nickname = R.string.real_madrid_nickname,
                        stadium = R.string.real_madrid_stadium,
                        coach = R.string.real_madrid_coach,
                        about = R.string.real_madrid_about,
                        logo = R.drawable.madrid,
                        header = R.drawable.madrid_header,
                    ),
                    Team(
                        name = R.string.barcelona,
                        founded = R.string.barcelona_founded,
                        nickname = R.string.barcelona_nickname,
                        stadium = R.string.barcelona_stadium,
                        coach = R.string.barcelona_coach,
                        about = R.string.barcelona_about,
                        logo = R.drawable.barca,
                        header = R.drawable.barca_header,
                    ),
                    Team(
                        name = R.string.psg,
                        founded = R.string.psg_founded,
                        nickname = R.string.psg_nickname,
                        stadium = R.string.psg_stadium,
                        coach = R.string.psg_coach,
                        about = R.string.psg_about,
                        logo = R.drawable.psg,
                        header = R.drawable.psg_header,
                    ),
                ),
            SportCategories.BASKETBALL to
                listOf(
                    Team(
                        name = R.string.gsw,
                        founded = R.string.gsw_founded,
                        nickname = R.string.gsw_nickname,
                        stadium = R.string.gsw_stadium,
                        coach = R.string.gsw_coach,
                        about = R.string.gsw_about,
                        logo = R.drawable.gsw,
                        header = R.drawable.gsw_header,
                    ),
                    Team(
                        name = R.string.celtics,
                        founded = R.string.celtics_founded,
                        nickname = R.string.celtics_nickname,
                        stadium = R.string.celtics_stadium,
                        coach = R.string.celtics_coach,
                        about = R.string.celtics_about,
                        logo = R.drawable.celtics,
                        header = R.drawable.celtics_header,
                    ),
                    Team(
                        name = R.string.bucks,
                        founded = R.string.bucks_founded,
                        nickname = R.string.bucks_nickname,
                        stadium = R.string.bucks_stadium,
                        coach = R.string.bucks_coach,
                        about = R.string.bucks_about,
                        logo = R.drawable.bucks,
                        header = R.drawable.bucks_header,
                    ),
                ),
            SportCategories.FOOTBALL to
                listOf(
                    Team(
                        name = R.string.chiefs,
                        founded = R.string.chiefs_founded,
                        nickname = R.string.chiefs_nickname,
                        stadium = R.string.chiefs_stadium,
                        coach = R.string.chiefs_coach,
                        about = R.string.chiefs_about,
                        logo = R.drawable.chiefs,
                        header = R.drawable.chiefs_header,
                    ),
                    Team(
                        name = R.string.packers,
                        founded = R.string.packers_founded,
                        nickname = R.string.packers_nickname,
                        stadium = R.string.packers_stadium,
                        coach = R.string.packers_coach,
                        about = R.string.packers_about,
                        logo = R.drawable.packers,
                        header = R.drawable.packers_header,
                    ),
                    Team(
                        name = R.string.buccaneers,
                        founded = R.string.buccaneers_founded,
                        nickname = R.string.buccaneers_nickname,
                        stadium = R.string.buccaneers_stadium,
                        coach = R.string.buccaneers_coach,
                        about = R.string.buccaneers_about,
                        logo = R.drawable.buccaneers,
                        header = R.drawable.buccaneers_header,
                    ),
                ),
        )
}
