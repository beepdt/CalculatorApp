package com.example.calculatorapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Box as Box

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen(viewModel: CalculatorViewModel){

    val buttons = listOf(
        listOf("C","(",")","/"),
        listOf("7","8","9","+"),
        listOf("4","5","6","_"),
        listOf("1","2","3","x"),
        listOf("AC",".","0","="),
    )

    Scaffold(

        topBar = {
            TopAppBar(title = { Text(text = "calculator.", fontWeight = FontWeight.Bold) })
        },

        content = {
            //paddingValue ensures that the content is padded with the top App Bar
            paddingValues ->
            Column(
                //verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(horizontal = 16.dp)

                ){
                    Text(overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        fontSize = 24.sp,
                        text = "input",
                        modifier = Modifier.align(Alignment.BottomEnd))
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(horizontal = 16.dp)

                ){
                    Text(
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 32.sp,
                        text = "Result will go here",
                        modifier = Modifier.align(Alignment.BottomEnd))
                }

                Spacer(modifier = Modifier.weight(1f))

                //number grid

                Column(modifier = Modifier.padding(16.dp)){
                    buttons.forEach{
                            row ->
                        Row(modifier = Modifier.fillMaxWidth()){

                            row.forEach{
                                    label ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color.Cyan)
                                        .height(100.dp)
                                        .weight(1f)
                                        .clickable{viewModel.onButtonClick(label)},
                                    contentAlignment = Alignment.Center
                                ){
                                    Text(text = label, fontSize = 24.sp)
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





