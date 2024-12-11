package com.example.calculatorapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.FontScaling
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen(){
    Scaffold(

        topBar = {
            TopAppBar(title = { Text(text = "calculator.", fontWeight = FontWeight.Bold) })
        },

        content = {
            //paddingValue ensures that the content is padded with the top App Bar
            paddingValues ->
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp).padding(horizontal = 16.dp)

                ){
                    Text(text = "input")
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp).padding(horizontal = 16.dp)

                ){
                    Text(text = "Result will go here")
                }

                Column(modifier = Modifier.padding(horizontal = 16.dp)) {

                    Row(modifier = Modifier.fillMaxWidth()) {

                        Box(modifier = Modifier.size(100.dp).clickable {}){ Text(text = "7",modifier = Modifier.align(
                            Alignment.Center), fontSize = 32.sp)}
                        Box(modifier = Modifier.size(100.dp).clickable {}){ Text(text = "8",modifier = Modifier.align(
                            Alignment.Center), fontSize = 32.sp)}
                        Box(modifier = Modifier.size(100.dp).clickable {}){ Text(text = "9",modifier = Modifier.align(
                            Alignment.Center), fontSize = 32.sp)}
                        Box(modifier = Modifier.size(100.dp).clickable {}){ Text(text = "+",modifier = Modifier.align(
                            Alignment.Center), fontSize = 32.sp)}

                    }

                    Row(modifier = Modifier.fillMaxWidth()) {

                        Box(modifier = Modifier.size(100.dp).clickable {}){ Text(text = "4",modifier = Modifier.align(
                            Alignment.Center), fontSize = 32.sp)}
                        Box(modifier = Modifier.size(100.dp).clickable {}){ Text(text = "5",modifier = Modifier.align(
                            Alignment.Center), fontSize = 32.sp)}
                        Box(modifier = Modifier.size(100.dp).clickable {}){ Text(text = "6",modifier = Modifier.align(
                            Alignment.Center), fontSize = 32.sp)}
                        Box(modifier = Modifier.size(100.dp).clickable {}){ Text(text = "-",modifier = Modifier.align(
                            Alignment.Center), fontSize = 32.sp)}

                    }

                    Row(modifier = Modifier.fillMaxWidth()) {

                        Box(modifier = Modifier.size(100.dp).clickable {}){ Text(text = "1",modifier = Modifier.align(
                            Alignment.Center), fontSize = 32.sp)}
                        Box(modifier = Modifier.size(100.dp).clickable {}){ Text(text = "2",modifier = Modifier.align(
                            Alignment.Center), fontSize = 32.sp)}
                        Box(modifier = Modifier.size(100.dp).clickable {}){ Text(text = "3",modifier = Modifier.align(
                            Alignment.Center), fontSize = 32.sp)}
                        Box(modifier = Modifier.size(100.dp).clickable {}){ Text(text = "x",modifier = Modifier.align(
                            Alignment.Center), fontSize = 32.sp)}

                    }

                    Row(modifier = Modifier.fillMaxWidth()) {

                        Box(modifier = Modifier.size(100.dp).clickable {}){ Text(text = "0",modifier = Modifier.align(
                            Alignment.Center), fontSize = 32.sp)}
                        Box(modifier = Modifier.size(100.dp).clickable {}){ Text(text = ".",modifier = Modifier.align(
                            Alignment.Center), fontSize = 32.sp)}
                        Box(modifier = Modifier.size(100.dp).clickable {}){ Text(text = "/",modifier = Modifier.align(
                            Alignment.Center), fontSize = 32.sp)}
                        Box(modifier = Modifier.size(100.dp).clickable {}){ Text(text = "=",modifier = Modifier.align(
                            Alignment.Center), fontSize = 32.sp)}

                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun CalculatorScreenPreview(){
    CalculatorScreen()
}