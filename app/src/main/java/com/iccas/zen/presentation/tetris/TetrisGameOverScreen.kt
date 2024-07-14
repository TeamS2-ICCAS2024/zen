package com.iccas.zen.presentation.tetris

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.iccas.zen.R
import com.iccas.zen.presentation.tetris.tetrisComponents.ReplayAndExitControlButtons
import com.iccas.zen.ui.theme.Green50
import com.iccas.zen.ui.theme.Red50

@Composable
fun TetrisGameOverScreen(
    navController: NavHostController,
    level: Int?,
    score: Int?,
    lives: Int?,
    dateTime: String?
) {
    val stats = listOf(
        "level" to (level ?: 0),
        "score" to (score ?: 0)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.tetris_game_over),
            contentDescription = null,
            modifier = Modifier
                .width(240.dp)
                .padding(end = 20.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(30.dp))

        stats.forEach { (label, value) ->
            Text(
                text = "$label: $value",
                color = Color.White,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold,
                fontSize = 30.sp
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(5) { index ->
                val heartIcon = if (index < (lives ?: 0)) {
                    R.drawable.tetris_game_heart_filled
                } else {
                    R.drawable.tetris_game_heart_unfilled
                }
                Image(
                    painter = painterResource(id = heartIcon),
                    contentDescription = null,
                    modifier = Modifier.size(27.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
            }
        }
        Spacer(modifier = Modifier.height(50.dp))

        ReplayAndExitControlButtons(
            onReplay = { navController.navigate("tetris_game") },
            onExit = { navController.navigate("game_select") },
            replayButtonBackground = Green50,
            exitButtonBackground = Red50,
            replayIconColor = Color.Black,
            exitIconColor = Color.Black
        )
    }
}