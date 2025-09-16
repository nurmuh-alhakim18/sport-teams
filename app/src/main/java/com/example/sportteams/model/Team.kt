package com.example.sportteams.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Team(
    @StringRes val name: Int,
    @StringRes val founded: Int,
    @StringRes val nickname: Int,
    @StringRes val stadium: Int,
    @StringRes val coach: Int,
    @StringRes val about: Int,
    @DrawableRes val logo: Int,
    @DrawableRes val header: Int,
) : CardItemI
