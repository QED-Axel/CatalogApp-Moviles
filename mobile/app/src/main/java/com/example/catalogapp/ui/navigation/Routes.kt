package com.example.catalogapp.ui.navigation

sealed class Route(val route: String) {
    object Onboarding : Route("onboarding")
    object Home : Route("home")
    object Detail : Route("detail/{id}") {
        fun createRoute(id: Int) = "detail/$id"
    }
    object Favorites : Route("favorites")
}
