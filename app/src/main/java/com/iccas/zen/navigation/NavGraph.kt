package com.iccas.zen.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.iccas.zen.SelectEmotionScreen
import com.iccas.zen.presentation.character.CharScreen
import com.iccas.zen.presentation.character.CollectionScreen
import com.iccas.zen.presentation.chatBot.ChatScreen
import com.iccas.zen.presentation.heart.BaseResultScreen
import com.iccas.zen.presentation.heart.CountDownScreen
import com.iccas.zen.presentation.heart.GuideMeasureBaseScreen
import com.iccas.zen.presentation.heart.HighHeartRateScreen
import com.iccas.zen.presentation.heart.MeasureBaseScreen
import com.iccas.zen.presentation.heart.RecommendMeasureBaseScreen
import com.iccas.zen.presentation.heart.viewmodel.MeasureHeartViewModel
import com.iccas.zen.presentation.home.GameSelectScreen
import com.iccas.zen.presentation.home.PersonalSettingScreen
import com.iccas.zen.presentation.home.SettingScreen
import com.iccas.zen.presentation.onBoarding.OnboardingPage1
import com.iccas.zen.presentation.onBoarding.OnboardingPage2
import com.iccas.zen.presentation.onBoarding.OnboardingPage3
import com.iccas.zen.presentation.onBoarding.OnboardingPage4
import com.iccas.zen.presentation.report.AngerGameReport
import com.iccas.zen.presentation.report.AngerGameSelect
import com.iccas.zen.presentation.report.EmotionDiarySelect
import com.iccas.zen.presentation.report.ReportScreen
import com.iccas.zen.presentation.report.SelfDiagnosisSelect
import com.iccas.zen.presentation.signup.LoginScreen
import com.iccas.zen.presentation.signup.SignupScreen
import com.iccas.zen.presentation.signup.WelcomeScreen
import com.iccas.zen.presentation.tetris.TetrisGameScreen
import com.iccas.zen.presentation.tetris.logic.GameViewModel
import com.iccas.zen.presentation.yoga.StartYogaGameScreen
import com.iccas.zen.presentation.yoga.YogaGameScreen
import com.iccas.zen.presentation.diagnosis.DiagnosisScreen
import com.iccas.zen.presentation.diagnosis.SurveyScreen
import com.iccas.zen.presentation.diagnosis.ResultScreen
import com.iccas.zen.presentation.heartreport.HeartReportScreen
import com.iccas.zen.presentation.heartreport.HeartReport2Screen

@Composable
fun NavGraph(
    measureHeartViewModel: MeasureHeartViewModel,
    gameViewModel: GameViewModel
) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "diagnosis") {
//    NavHost(navController, startDestination = "onboarding1") {
        composable("onboarding1") { OnboardingPage1(navController) }
        composable("onboarding2") { OnboardingPage2(navController) }
        composable("onboarding3") { OnboardingPage3(navController) }
        composable("onboarding4") { OnboardingPage4(navController) }
        composable("welcome") { WelcomeScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("signup") { SignupScreen(navController) }
        composable("game_select") {
            GameSelectScreen(navController = navController)
        }
        composable("guide_measure_base") {
            GuideMeasureBaseScreen(navController = navController)
        }
        composable("recommend_measure_base") {
            RecommendMeasureBaseScreen(navController = navController)
        }
        composable("measure_base") {
            MeasureBaseScreen(
                measureHeartViewModel = measureHeartViewModel,
                navController = navController
            )
        }
        composable("base_result") {
            BaseResultScreen(
                measureHeartViewModel = measureHeartViewModel,
                navController = navController
            )
        }
        composable(
            "countdown/{route}",
            arguments = listOf(navArgument("route") { type = NavType.StringType })
        ) { backStackEntry ->
            val route = backStackEntry.arguments?.getString("route") ?: "tetris_game"
            CountDownScreen(navController = navController, route = route)
        }
        /* composable("game_select") {
             GameSelectScreen(navController = navController)
         }*/
        composable("tetris_game") {
            TetrisGameScreen(
                measureHeartViewModel = measureHeartViewModel,
                gameViewModel = gameViewModel,
                navController = navController
            )
        }
        composable("diagnosis") {
            DiagnosisScreen(navController)
        }
        composable("survey") {
            SurveyScreen(navController)
        }
        composable(
            "result/{score}",
            arguments = listOf(navArgument("score") { type = NavType.IntType })
        ) { backStackEntry ->
            val score = backStackEntry.arguments?.getInt("score") ?: 0
            ResultScreen(score = score, navController = navController)
        }
composable("heartreport")
{
    HeartReportScreen(navController)
}
        composable("heartreport2")
        {
           HeartReport2Screen(navController)
        }
        composable("high_heart_rate") {
            HighHeartRateScreen()
        }


        composable("select_emotion") {
            SelectEmotionScreen(navController = navController)
        }
        composable(

            route = "tetris_game_over/level={level}/score={score}/lives={lives}/dateTime={dateTime}",
            arguments = listOf(
                navArgument("level") { type = NavType.IntType },
                navArgument("score") { type = NavType.IntType },
                navArgument("lives") { type = NavType.IntType },
                navArgument("dateTime") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val level = backStackEntry.arguments?.getInt("level")
            val score = backStackEntry.arguments?.getInt("score")
            val lives = backStackEntry.arguments?.getInt("lives")
            val dateTime = backStackEntry.arguments?.getString("dateTime")
            TetrisGameScreen(
                measureHeartViewModel = measureHeartViewModel,
                gameViewModel = gameViewModel,
                navController = navController
            )
        }
        composable(
            "chat_screen/{emojiResId}?prompt={prompt}",
            arguments = listOf(
                navArgument("emojiResId") { type = NavType.IntType },
                navArgument("prompt") { type = NavType.StringType; defaultValue = "" }
            )
        ) { backStackEntry ->
            val emojiResId = backStackEntry.arguments?.getInt("emojiResId") ?: 0
            val prompt = backStackEntry.arguments?.getString("prompt") ?: ""
            ChatScreen(navController, emojiResId, prompt)
        }
        composable("start_yoga") {
            StartYogaGameScreen(navController = navController)
        }
        composable(route = "yoga_game") {
            YogaGameScreen(initialPoseIndex = 0, navController = navController)
        }
        composable("char_main ") {
            CharScreen(navController = navController)
        }
        composable("collection") {
            CollectionScreen(navController = navController)
        }
        composable("report") {
            ReportScreen(navController = navController)
        }
        composable("report/emotion_diary") {
            EmotionDiarySelect(navController = navController)
        }
        composable("report/self_diagnosis") {
            SelfDiagnosisSelect(navController = navController)
        }
        composable("report/anger_game") {
            AngerGameSelect(navController = navController)
        }

        composable(
            route = "anger_game_report/{gameName}",
            arguments = listOf(navArgument("gameName") { type = NavType.StringType })
        ) { backStackEntry ->
            val gameName = backStackEntry.arguments?.getString("gameName") ?: "unknown"
            AngerGameReport(navController = navController, gameName = gameName)
        }
        composable("setting") {
            SettingScreen(navController = navController)
        }
        composable("personal_setting") {
            PersonalSettingScreen(navController = navController)
        }
    }
}