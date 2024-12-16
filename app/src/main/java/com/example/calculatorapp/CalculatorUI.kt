package com.example.calculatorapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculatorapp.ui.theme.customFont
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen(viewModel: CalculatorViewModel){

    //makes status bar transparent
    val systemUiController = rememberSystemUiController()
    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(
            color = Color(0xFF222222),
            darkIcons = false
        )
    }


    val calculatorData = viewModel.calculatorData.value

    val calculateResult = when {
        calculatorData.result.toString().endsWith(".0") -> {
            calculatorData.result.toString().replace(".0","")
        }
        else -> {calculatorData.result.toString()}
    }


    val buttons = listOf(
        listOf("C","(",")","÷"),
        listOf("7","8","9","+"),
        listOf("4","5","6","-"),
        listOf("1","2","3","x"),
        listOf("AC","0",".","="),
    )

    Scaffold(

        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.White
                ),
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "calculator.",
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            fontFamily = customFont
                        )
                        Spacer(modifier = Modifier.weight(1f))

                        Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "options", modifier = Modifier.padding(10.dp))
                    } }
            )
        },

        content = {
            //paddingValue ensures that the content is padded with the top App Bar
            paddingValues ->
            Column(
                //verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xff434343), Color(0xff000000)),
                            start = Offset(0f, 0f), // Top-left corner
                            end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                        )
                    )
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {

                //input Equation
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(horizontal = 16.dp)

                ){
                    Text(overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        color = Color.LightGray,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        text = calculatorData.equation,
                        modifier = Modifier.align(Alignment.BottomEnd))
                }

                //output result
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(horizontal = 16.dp)

                ){
                    Text(
                        maxLines = 1,
                        color = Color.White,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold,
                        fontSize = 60.sp,
                        text = calculateResult,
                        modifier = Modifier.align(Alignment.BottomEnd))
                }

                Spacer(modifier = Modifier.weight(1f))

                //number grid

                Column(modifier = Modifier
                    .padding(0.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xff434343), Color(0xff000000)),
                            start = Offset(0f, 0f), // Top-left corner
                            end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                        )
                    )
                ){
                    buttons.forEach{
                            row ->
                        Row(modifier = Modifier.fillMaxWidth()){

                            row.forEach{
                                    label ->
                                Box(
                                    modifier = Modifier
                                        .height(100.dp)
                                        .weight(1f)
                                        .clickable { viewModel.onButtonClick(label) },
                                    contentAlignment = Alignment.Center
                                ){
                                    when (label) {
                                        "x" -> {
                                            Text(
                                                text = label,
                                                fontSize = 30.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White
                                            )
                                        }
                                        "÷" -> {
                                            Text(
                                                text = label,
                                                fontSize = 30.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White
                                            )}
                                        else -> {Text(
                                            text = label,
                                            fontSize = 28.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color.White
                                        )}
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun CalculatorScreenPreview(){
    CalculatorScreen(CalculatorViewModel())
}





