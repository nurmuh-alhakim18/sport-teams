package com.example.sportteams.ui

import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.sportteams.R
import com.example.sportteams.data.DataSource
import com.example.sportteams.model.Category
import com.example.sportteams.model.Team
import com.example.sportteams.ui.utils.ContentType
import com.example.sportteams.ui.utils.Destination

@Composable
fun SportTeamsApp(windowSize: WindowWidthSizeClass) {
    val viewModel: SportTeamsViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    val navController = rememberNavController()
    val contentType =
        when (windowSize) {
            WindowWidthSizeClass.Compact, WindowWidthSizeClass.Medium -> {
                ContentType.LIST_ONLY
            }
            WindowWidthSizeClass.Expanded -> {
                ContentType.LIST_AND_DETAIL
            }
            else -> {
                ContentType.LIST_ONLY
            }
        }
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen =
        Destination.valueOf(
            backStackEntry?.destination?.route ?: Destination.CATEGORY_LIST.name,
        )

    val isFullScreen = contentType == ContentType.LIST_ONLY
    if (!isFullScreen) {
        AppListAndDetail(
            viewModel = viewModel,
            uiState = uiState,
            navController = navController,
            modifier =
                Modifier
                    .padding(8.dp)
                    .safeDrawingPadding(),
        )
    } else {
        Scaffold(
            modifier =
                Modifier
                    .fillMaxSize()
                    .safeDrawingPadding(),
            topBar = {
                AppBar(
                    onBackClick = { navController.navigateUp() },
                    title = currentScreen.title,
                    isBackVisible = navController.previousBackStackEntry != null,
                    modifier = Modifier.padding(),
                )
            },
        ) { innerPadding ->
            AppListOnly(
                viewModel = viewModel,
                uiState = uiState,
                innerPadding = innerPadding,
                navController = navController,
                modifier = Modifier.padding(8.dp),
            )
        }
    }
}

@Composable
fun AppBar(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    isFullScreen: Boolean = true,
    isBackVisible: Boolean = false,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        if (isFullScreen && isBackVisible) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.padding(start = 16.dp),
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = null,
                )
            }
        }

        Text(
            text = stringResource(title),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier =
                when (isBackVisible) {
                    true -> Modifier.padding(start = 8.dp)
                    else -> Modifier.padding(start = 16.dp)
                },
        )
    }
}

@Composable
fun AppListAndDetail(
    viewModel: SportTeamsViewModel,
    uiState: SportTeamsUiState,
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        AppBar(
            title = R.string.app_name,
            isBackVisible = navController.previousBackStackEntry != null,
            modifier = Modifier.padding(vertical = 16.dp),
        )
        Row {
            CategoryList(
                isFullScreen = false,
                navController = navController,
                categories = DataSource.categories(),
                viewModel = viewModel,
                uiState = uiState,
                modifier = Modifier.weight(1f),
            )
            TeamList(
                teams = uiState.currentTeams,
                viewModel = viewModel,
                uiState = uiState,
                modifier = Modifier.weight(2f),
            )
        }
    }
}

@Composable
fun AppListOnly(
    viewModel: SportTeamsViewModel,
    uiState: SportTeamsUiState,
    innerPadding: PaddingValues,
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Destination.CATEGORY_LIST.name,
        modifier = modifier,
    ) {
        composable(Destination.CATEGORY_LIST.name) {
            CategoryList(
                isFullScreen = true,
                navController = navController,
                categories = DataSource.categories(),
                viewModel = viewModel,
                uiState = uiState,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(innerPadding),
            )
        }

        composable(Destination.TEAM_LIST.name) {
            TeamList(
                teams = uiState.currentTeams,
                viewModel = viewModel,
                uiState = uiState,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(innerPadding),
            )
        }
    }
}

@Composable
fun CategoryList(
    modifier: Modifier = Modifier,
    isFullScreen: Boolean = false,
    navController: NavHostController,
    categories: List<Category>,
    viewModel: SportTeamsViewModel,
    uiState: SportTeamsUiState,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        items(
            items = categories,
            key = { category -> category.name },
        ) { category ->
            CategoryItemCard(
                item = category,
                isSelected = uiState.currentCategory == category.id,
                isFullScreen = isFullScreen,
                onCardClick = {
                    viewModel.updateCurrentCategory(category.id)
                    if (isFullScreen) {
                        navController.navigate(Destination.TEAM_LIST.name)
                    }
                },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
            )
        }
    }
}

@Composable
fun TeamList(
    teams: List<Team>,
    viewModel: SportTeamsViewModel,
    uiState: SportTeamsUiState,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        items(
            items = teams,
            key = { team -> team.name },
        ) { team ->
            TeamItemCard(
                team = team,
                isSelected = uiState.currentTeam == team,
                onCardClick = {
                    viewModel.updateCurrentTeam(team)
                },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
            )
        }
    }
}

@Composable
fun CategoryItemCard(
    item: Category,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    isFullScreen: Boolean = false,
    onCardClick: () -> Unit,
) {
    Card(
        onClick = onCardClick,
        shape = RoundedCornerShape(8.dp),
        colors =
            when {
                isSelected && !isFullScreen -> {
                    CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    )
                }
                else -> {
                    CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                }
            },
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 4.dp,
            ),
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier =
                Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
        ) {
            Icon(
                painter = painterResource(item.image),
                contentDescription = null,
                modifier = Modifier.padding(end = 16.dp),
            )
            Text(
                text = stringResource(item.name),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
fun TeamItemCard(
    team: Team,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onCardClick: () -> Unit,
) {
    Card(
        onClick = onCardClick,
        shape =
            RoundedCornerShape(
                topEnd = 24.dp,
                bottomStart = 24.dp,
            ),
        colors =
            when {
                isSelected -> {
                    CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    )
                }
                else -> {
                    CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                }
            },
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 4.dp,
            ),
        modifier = modifier,
    ) {
        Column(
            modifier =
                Modifier.animateContentSize(
                    animationSpec =
                        spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessMediumLow,
                        ),
                ),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier =
                    Modifier
                        .padding(16.dp)
                        .fillMaxSize(),
            ) {
                Text(
                    text = stringResource(team.name),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                )
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Outlined.KeyboardArrowDown,
                        contentDescription = null,
                    )
                }
            }

            if (isSelected) {
                TeamDetail(team = team)
            }
        }
    }
}

@Composable
fun TeamDetail(
    team: Team,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        TeamDetailHeader(
            team = team,
            modifier = Modifier.height(250.dp),
        )
        Spacer(modifier = Modifier.height(24.dp))
        TeamDetailBody(
            team = team,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
fun TeamDetailHeader(
    team: Team,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(team.header),
            contentDescription = stringResource(team.name),
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopCenter,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(250.dp),
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier =
                Modifier
                    .align(alignment = Alignment.BottomCenter),
        ) {
            Image(
                painter = painterResource(team.logo),
                contentDescription = stringResource(team.name),
                contentScale = ContentScale.Crop,
                modifier =
                    Modifier
                        .offset(y = 32.dp)
                        .clip(CircleShape)
                        .size(120.dp)
                        .border(
                            width = 2.dp,
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primary,
                        ),
            )
        }
    }
}

@Composable
fun TeamDetailBody(
    team: Team,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        SummaryCard(
            team = team,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
        )
        Text(
            text = stringResource(R.string.about),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier =
                Modifier.padding(
                    top = 16.dp,
                    bottom = 8.dp,
                ),
        )
        Text(
            text = stringResource(team.about),
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Composable
fun SummaryCard(
    team: Team,
    modifier: Modifier = Modifier,
) {
    Card(
        shape =
            RoundedCornerShape(
                topEnd = 24.dp,
                bottomStart = 24.dp,
            ),
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            ),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 4.dp,
            ),
        modifier = modifier,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
        ) {
            Text(
                text = stringResource(R.string.summary),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp),
            )
            Spacer(modifier = Modifier.padding(8.dp))
            SummaryInfo(
                icon = Icons.Outlined.DateRange,
                title = stringResource(R.string.founded),
                info = stringResource(team.founded),
                modifier = Modifier.padding(8.dp),
            )
            SummaryDivider()
            SummaryInfo(
                icon = Icons.Outlined.PlayArrow,
                title = stringResource(R.string.nickname),
                info = stringResource(team.nickname),
                modifier = Modifier.padding(8.dp),
            )
            SummaryDivider()
            SummaryInfo(
                icon = Icons.Outlined.Home,
                title = stringResource(R.string.stadium),
                info = stringResource(team.stadium),
                modifier = Modifier.padding(8.dp),
            )
            SummaryDivider()
            SummaryInfo(
                icon = Icons.Outlined.Person,
                title = stringResource(R.string.coach),
                info = stringResource(team.coach),
                modifier = Modifier.padding(8.dp),
            )
        }
    }
}

@Composable
fun SummaryInfo(
    icon: ImageVector,
    title: String,
    info: String,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxSize(),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = "$title: $info",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
        )
    }
}

@Composable
fun SummaryDivider(modifier: Modifier = Modifier) {
    HorizontalDivider(
        color = MaterialTheme.colorScheme.onSurface,
        thickness = 4.dp,
        modifier =
            modifier.padding(
                top = 8.dp,
                bottom = 8.dp,
                end = 16.dp,
                start = 48.dp,
            ),
    )
}
