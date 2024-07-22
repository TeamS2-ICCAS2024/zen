package com.iccas.zen.presentation.heartreport

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.iccas.zen.R
import com.iccas.zen.presentation.components.BasicBackgroundWithLogo
import com.iccas.zen.presentation.report.viewModel.ReportViewModel
import com.iccas.zen.ui.theme.Brown40
import com.iccas.zen.ui.theme.Gray80
import com.iccas.zen.ui.theme.Orange30
import com.iccas.zen.utils.formatLocalDateTime
import java.time.LocalDate

@Composable
fun HeartReportScreen(navController: NavController, userId: Long = 1) {
    val currentDate = LocalDate.now()
    val currentMonth = remember { mutableStateOf(currentDate.monthValue) }
    val currentYear = remember { mutableStateOf(currentDate.year) }

    val viewModel: ReportViewModel = viewModel()

    LaunchedEffect(currentYear.value, currentMonth.value) {
        viewModel.getTetrisResultList(currentYear.value, currentMonth.value, userId)
    }

    val tetrisResultList = viewModel.tetrisResultList.collectAsState().value

    BasicBackgroundWithLogo {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 35.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.navigate("report") }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_left),
                        contentDescription = "Back",
                        modifier = Modifier.size(25.dp)
                    )
                }

                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Orange30)) {
                            append("Anger Control")
                        }
                    },
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    "Report",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 24.sp
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val isPreviousMonthDisabled = currentMonth.value == 1 && currentYear.value == currentDate.year
                IconButton(
                    onClick = {
                        if (!isPreviousMonthDisabled) {
                            if (currentMonth.value > 1) {
                                currentMonth.value--
                            } else {
                                currentMonth.value = 12
                                currentYear.value--
                            }
                        }
                    },
                    enabled = !isPreviousMonthDisabled
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.go_left),
                        contentDescription = "Previous Month",
                        tint = if (isPreviousMonthDisabled) Color.Gray else Color.Unspecified
                    )
                }

                // 날짜 텍스트
                Text(
                    text = "${currentYear.value}.${currentMonth.value.toString().padStart(2, '0')}",
                    color = Brown40,
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp
                )

                val isNextMonthDisabled = (currentMonth.value == 12 && currentYear.value >= currentDate.year) ||
                        (currentMonth.value == currentDate.monthValue && currentYear.value == currentDate.year)
                IconButton(
                    onClick = {
                        if (!isNextMonthDisabled) {
                            if (currentMonth.value < 12) {
                                currentMonth.value++
                            } else {
                                currentMonth.value = 1
                                currentYear.value++
                            }
                        }
                    },
                    enabled = !isNextMonthDisabled
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.go_right),
                        contentDescription = "Next Month",
                        tint = if (isNextMonthDisabled) Color.Gray else Color.Unspecified
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                tetrisResultList?.let { resultList ->
                    if (resultList.data.isNotEmpty()) {
                        items(resultList.data.size) { index ->
                            val result = resultList.data[index]
                            GameScoreRow(
                                game = formatLocalDateTime(result.startTime),
                                lives = result.lives,
                                navController = navController
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                    } else {
                        item {
                            Text(
                                text = "No game records !",
                                color = Brown40,
                                fontWeight = FontWeight.Bold,
                                fontSize = 25.sp,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GameScoreRow(game: String, lives: Int, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .height(60.dp)
            .background(Gray80, RoundedCornerShape(50))
            .border(2.dp, Brown40, RoundedCornerShape(50)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = { navController.navigate("heartreport2") },
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .fillMaxWidth()
                .padding(4.dp)
                .height(60.dp)
                .background(Color.Transparent, RoundedCornerShape(50))
                .border(0.dp, Color.Transparent, RoundedCornerShape(50)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Brown40
            )
        ) {
            Text(
                text = game,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                fontFamily = FontFamily.Serif
            )
            Spacer(modifier = Modifier.width(15.dp))

            Row() {
                repeat(5) { index ->
                    val heartIcon = if (index < lives) {
                        R.drawable.tetris_game_heart_filled
                    } else {
                        R.drawable.tetris_game_heart_unfilled
                    }
                    Image(
                        painter = painterResource(id = heartIcon),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
        }
    }
}
