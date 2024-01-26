package com.raion.keynotes.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.raion.keynotes.model.NoteClass
import com.raion.keynotes.screen.DownloadScreen
import com.raion.keynotes.screen.HomeScreen
import com.raion.keynotes.screen.NoteDAOViewModel
import com.raion.keynotes.screen.NoteScreen
import com.raion.keynotes.screen.ProfileScreen
import com.raion.keynotes.screen.RaionAPIViewModel

@Composable
fun NavHost(
    RaionAPIViewModel: RaionAPIViewModel,
    NoteDAOViewModel: NoteDAOViewModel
){
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    var noteList = NoteDAOViewModel.notelist.collectAsState().value

    androidx.navigation.compose.NavHost(
        navController = navController,
        startDestination = NavEnum.HomeScreen.name
    ) {
        composable(NavEnum.HomeScreen.name){
            HomeScreen(
                viewModel = RaionAPIViewModel,
                navController = navController,
                addNote = { RaionAPIViewModel.addNote(it.first, it.second) },
                deleteNote = { RaionAPIViewModel.deleteNote(it)}
            )
        }

        composable(
            NavEnum.NoteScreen.name+"/{noteId}",
            arguments = listOf(navArgument(name = "noteId"){type = NavType.StringType})
        ){
            backStackEntry ->
            NoteScreen(
                navController = navController, backStackEntry.arguments?.getString("noteId"),
                viewModel = RaionAPIViewModel,
                deleteNote = { RaionAPIViewModel.deleteNote(it)},
                downloadNote = { NoteDAOViewModel.addNote(NoteClass(
                    noteId = it[0],
                    title = it[1],
                    description = it[2],
                    createdAt = it[3],
                    updatedAt = it[4]))
                }
            )
        }

        composable(NavEnum.ProfileScreen.name){
            ProfileScreen(
                navController = navController,
                viewModel = RaionAPIViewModel
            )
        }

        composable(NavEnum.DownloadScreen.name){
            DownloadScreen(
                noteDAOViewModel = NoteDAOViewModel,
                navController = navController,
                deleteNote = { NoteDAOViewModel.removeNote(it)},
                noteList = noteList,
                RaionAPIViewModel = RaionAPIViewModel
            )
        }
    }
}