package com.example.catalogapp.ui.navigation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.catalogapp.ui.viewmodels.ThemeViewModel
import kotlinx.coroutines.launch

import com.example.catalogapp.ui.screens.HomeScreen
import com.example.catalogapp.ui.screens.DetailScreen
import com.example.catalogapp.ui.screens.FavoritesScreen
import com.example.catalogapp.ui.viewmodels.FavoritesViewModel
import android.app.Application
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.catalogapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavigation(themeViewModel: ThemeViewModel) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val isDarkMode by themeViewModel.isDarkMode.collectAsState()
    
    val application = LocalContext.current.applicationContext as Application
    val favoritesViewModel: FavoritesViewModel = viewModel(factory = FavoritesViewModel.provideFactory(application))

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(16.dp))
                Text(stringResource(id = R.string.app_name), modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleLarge)
                HorizontalDivider()
                NavigationDrawerItem(
                    label = { Text(stringResource(id = R.string.nav_home)) },
                    selected = false,
                    icon = { Icon(Icons.Default.Home, contentDescription = stringResource(id = R.string.content_desc_home)) },
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(Route.Home.route) {
                            popUpTo(Route.Home.route) { inclusive = true }
                        }
                    }
                )
                NavigationDrawerItem(
                    label = { Text(stringResource(id = R.string.nav_favorites)) },
                    selected = false,
                    icon = { Icon(Icons.Default.Star, contentDescription = stringResource(id = R.string.content_desc_favorites)) },
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(Route.Favorites.route)
                    }
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                NavigationDrawerItem(
                    label = { Text(stringResource(id = R.string.nav_dark_mode)) },
                    selected = false,
                    badge = { Switch(checked = isDarkMode, onCheckedChange = { themeViewModel.toggleTheme() }) },
                    onClick = { themeViewModel.toggleTheme() }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(id = R.string.title_movies_books)) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = stringResource(id = R.string.content_desc_menu))
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = Route.Home.route,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(Route.Home.route) {
                    HomeScreen(onNavigateToDetail = { id -> 
                        navController.navigate(Route.Detail.createRoute(id)) 
                    })
                }
                composable(Route.Favorites.route) {
                    FavoritesScreen(
                        onNavigateToDetail = { id ->
                            navController.navigate(Route.Detail.createRoute(id))
                        },
                        viewModel = favoritesViewModel
                    )
                }
                composable(Route.Detail.route) { backStackEntry ->
                    val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                    if (id != null) {
                        DetailScreen(id = id)
                    } else {
                        Text(stringResource(id = R.string.error_invalid_id), modifier = Modifier.padding(16.dp))
                    }
                }
            }
        }
    }
}
