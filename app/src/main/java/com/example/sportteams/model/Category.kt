package com.example.sportteams.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Category(
    val id: SportCategories,
    @DrawableRes val image: Int,
    @StringRes val name: Int,
) : CardItemI
