package com.example.calculatorapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CalculatorViewModel: ViewModel() {
    private val _calculatorData = mutableStateOf(CalculatorData())

    val calculatorData:State<CalculatorData> get() = _calculatorData






    fun onButtonClick(button: String) {

        val equation = _calculatorData.value.equation

        when (button) {
            "AC" -> {
                _calculatorData.value = _calculatorData.value.copy(equation = "", result = 0.0)
            }
            "=" -> {
                val result = calculateResult()
                _calculatorData.value = _calculatorData.value.copy(result = result)
            }
            "C" -> {
                val updatedEquation = if (equation.isNotEmpty()) {
                    equation.substring(0, equation.length - 1)
                } else {
                    ""
                }
                _calculatorData.value = _calculatorData.value.copy(equation = updatedEquation)
            }
            else -> {
                _calculatorData.value = _calculatorData.value.copy(equation = equation + button)
            }
        }
    }


    private fun calculateResult():Double{

        try {
            val inputText = _calculatorData.value.equation

            when {
                inputText.contains("+") -> {

                    val numbers = inputText.split("+").map { it.trim().toDouble() }
                    val sum = numbers.sum()
                    _calculatorData.value.result = sum
                }

                inputText.contains("-")->{
                    val numbers = inputText.split("-").map { it.trim().toDouble() }
                    val difference = numbers.reduce{acc, d -> acc - d }
                    _calculatorData.value.result = difference

                }
                else -> {
                    _calculatorData.value.result = 0.0
                }
            }
        } catch (e: Exception){
            _calculatorData.value.result = 0.0
        }

        return _calculatorData.value.result
    }








    fun addition(vararg values: Double){
        val sum = values.sum()
        //_finalResult.value gives the current state. Default state is 0.0.
        //_finalResult.value.copy(result = .....) changes the value of just result parameter keeping other values same
        _calculatorData.value = _calculatorData.value.copy(result = _calculatorData.value.result + sum)
    }
}