package com.example.mycity.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mycity.R
import com.example.mycity.data.CityUiState

enum class MyCityScreen(val titleRes: Int) {
    Start(R.string.my_city),
    City(R.string.categories),
    Category(R.string.places),
    Place(R.string.place),
    Custom(R.string.my_city)
}

enum class MyCityContentType {
    Standard,
    Medium,
    Expanded
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCityApp(
    windowSize: WindowWidthSizeClass,
    viewModel: CityViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    // Navigation
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = MyCityScreen.valueOf(
        backStackEntry?.destination?.route ?: MyCityScreen.Start.name
    )
    // UiState
    val uiState by viewModel.uiState.collectAsState()

    val contentType = when (windowSize) {
        WindowWidthSizeClass.Medium -> MyCityContentType.Medium
        WindowWidthSizeClass.Expanded -> MyCityContentType.Expanded
        else -> MyCityContentType.Standard
    }

    if (contentType == MyCityContentType.Standard || contentType == MyCityContentType.Medium) {
        Scaffold(
            topBar = {
                MyCityTopBar(
                    canNavigateBack = navController.previousBackStackEntry != null,
                    currentScreen = currentScreen,
                    uiState = uiState,
                    navigateUp = { navController.navigateUp() },
                    changeSearch = { handleSearch(currentScreen, viewModel, navController) },
                    changeSearchField = { viewModel.changeSearchField(it) }
                )
            }
        ) { paddingValues ->

            NavHost(
                navController = navController,
                startDestination = MyCityScreen.Start.name,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(MyCityScreen.Start.name) {
                    CitiesScreen(
                        cities = uiState.cities,
                        onCityClicked = { city ->
                            viewModel.changeCity(city)
                            navController.navigate(MyCityScreen.City.name)
                        }
                    )
                }
                composable(MyCityScreen.City.name) {
                    CategoriesScreen(
                        categories = uiState.cityPicked!!.categories,
                        onCategoryClicked = { category ->
                            viewModel.changeCategory(category)
                            navController.navigate(MyCityScreen.Category.name)
                        },
                        contentType = contentType
                    )
                }
                composable(MyCityScreen.Category.name) {
                    PlacesScreen(
                        places = uiState.categoryPicked!!.places,
                        onPlaceClicked = { place ->
                            viewModel.changePlace(place)
                            navController.navigate(MyCityScreen.Place.name)
                        }
                    )
                }
                composable(MyCityScreen.Place.name) {
                    DetailScreen(
                        place = uiState.placePicked!!,
                        contentType = contentType
                    )
                }
                composable(MyCityScreen.Custom.name) {
                    CustomScreen(
                        customPlaces = uiState.customPlaces,
                        onPlaceClicked = { place ->
                            viewModel.changePlace(place)
                            navController.navigate(MyCityScreen.Place.name)
                        }
                    )
                }
            }
        }
    } else {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(text = stringResource(id = R.string.my_city)) },
                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                Row(modifier = Modifier.weight(1f)) {
                    CitiesScreen(
                        cities = uiState.cities,
                        onCityClicked = { city ->
                            viewModel.changeCity(city)
                        },
                        modifier = Modifier.weight(1f)
                    )
                    CategoriesScreen(
                        categories = uiState.cityPicked?.categories ?: listOf(),
                        onCategoryClicked = { category ->
                            viewModel.changeCategory(category)
                        },
                        contentType = contentType,
                        modifier = Modifier.weight(1f)
                    )
                    PlacesScreen(
                        places = uiState.categoryPicked?.places ?: listOf(),
                        onPlaceClicked = { place ->
                            viewModel.changePlace(place)
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
                Box(modifier = Modifier.weight(1f)) {
                    if (uiState.placePicked != null) {
                        DetailScreen(
                            place = uiState.placePicked!!,
                            contentType = contentType
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCityTopBar(
    currentScreen: MyCityScreen,
    uiState: CityUiState,
    canNavigateBack: Boolean,
    changeSearch: () -> Unit,
    changeSearchField: (String) -> Unit,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    val name = topBarTitle(currentScreen, uiState)

    if (currentScreen != MyCityScreen.Custom) {
        CenterAlignedTopAppBar(
            title = { Text(text = name) },
            navigationIcon = {
                if (canNavigateBack) {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            actions = {
                if (currentScreen == MyCityScreen.Start) {
                    IconButton(onClick = changeSearch) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = stringResource(R.string.search)
                        )
                    }
                }
            },
            modifier = modifier
        )
    } else {
        TopAppBar(
            title = {
                TextField(
                    value = uiState.searchText,
                    onValueChange = { changeSearchField(it) },
                    placeholder = { Text(text = stringResource(R.string.search_place)) },
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = stringResource(R.string.search)
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            },
            actions = {
                IconButton(onClick = changeSearch) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(R.string.close)
                    )
                }
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = modifier
        )
    }
}

@Composable
private fun topBarTitle(
    currentScreen: MyCityScreen,
    uiState: CityUiState
): String {
    return when (currentScreen) {
        MyCityScreen.City -> stringResource(
            currentScreen.titleRes,
            uiState.cityPicked!!.name
        )

        MyCityScreen.Category -> stringResource(
            currentScreen.titleRes,
            uiState.cityPicked!!.name,
            uiState.categoryPicked!!.name
        )

        MyCityScreen.Place -> uiState.placePicked!!.name
        else -> stringResource(currentScreen.titleRes)
    }
}

private fun handleSearch(currentScreen: MyCityScreen, viewModel: CityViewModel, navController: NavHostController) {
    if (currentScreen == MyCityScreen.Custom) {
        navController.navigateUp()
    } else {
        viewModel.changeSearchField("")
        navController.navigate(MyCityScreen.Custom.name)
    }
}