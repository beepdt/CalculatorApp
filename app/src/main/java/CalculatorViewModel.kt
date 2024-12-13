package com.example.calculatorapp

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CalculatorViewModel: ViewModel() {

    //calling the calculator dataclass as a variable
    private val _calculatorData = mutableStateOf(CalculatorData())
    //exposing the private variable to the public functions
    val calculatorData:State<CalculatorData> get() = _calculatorData




    fun onButtonClick(button: String) {

        //copy is used to copy the state and update it ot the mutable class

        _calculatorData.value.equation.let {
            when (button) {
                "AC" -> {
                    _calculatorData.value = _calculatorData.value.copy(equation = "", result = 0.0)
                    return
                }
                "=" -> {
                    val result = calculateResult()
                    _calculatorData.value= _calculatorData.value.copy(result = result)
                    return
                }
                "C" -> {
                    if (_calculatorData.value.equation.isNotEmpty()) {
                        _calculatorData.value = _calculatorData.value.copy(
                            equation = _calculatorData.value.equation.substring(
                                0,
                                _calculatorData.value.equation.length - 1
                            )
                        )
                        return
                    }

                }
                else -> {
                    _calculatorData.value =
                        _calculatorData.value.copy(equation = _calculatorData.value.equation + button)
                    return
                }
            }
        }
    }


    private fun calculateResult():Double{

        return try {
            val inputText = _calculatorData.value.equation

            when {
                inputText.contains("+") -> {

                    val numbers = inputText.split("+").map { it.trim().toDouble() }
                    numbers.sum()

                }

                inputText.contains("-")->{
                    val numbers = inputText.split("-").map { it.trim().toDouble() }
                     numbers.reduce{acc, d -> acc - d }


                }
                else -> {
                     0.0
                }
            }
        } catch (e: Exception){
            0.0
        }


    }








    fun addition(vararg values: Double){
        val sum = values.sum()
        //_finalResult.value gives the current state. Default state is 0.0.
        //_finalResult.value.copy(result = .....) changes the value of just result parameter keeping other values same
        _calculatorData.value = _calculatorData.value.copy(result = _calculatorData.value.result + sum)
    }
}