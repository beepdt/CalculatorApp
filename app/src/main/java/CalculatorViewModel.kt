package com.example.calculatorapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CalculatorViewModel: ViewModel() {
    private val _finalResult = mutableStateOf(CalculatorData())
    val finalResult:State<Double> get() = mutableStateOf(_finalResult.value.result)

    private val _inputText = mutableStateOf("")
    val inputText: State<String> = _inputText





    fun onButtonClick(button: String) {

        _inputText.value.let {
            if (button == "AC") {
                _inputText.value = ""
                _finalResult.value.result = 0.0
                return
            }

            if (button == "="){
                _finalResult.value.result = calculateResult()

            }

            if (button == "C") {

                if (_inputText.value.isNotEmpty()) {
                    _inputText.value = _inputText.value.substring(0, _inputText.value.length - 1)
                    return
                } else {
                    _inputText.value = ""
                    return
                }
            }

            else {
                _inputText.value = _inputText.value + button
                return
            }
        }
    }


    private fun calculateResult():Double{

        try {
            val inputText = _inputText.value

            when {
                inputText.contains("+") -> {

                    val numbers = inputText.split("+").map { it.trim().toDouble() }
                    val sum = numbers.sum()
                    _finalResult.value.result = sum
                }

                inputText.contains("-")->{
                    val numbers = inputText.split("-").map { it.trim().toDouble() }
                    val difference = numbers.reduce{acc, d -> acc - d }
                    _finalResult.value.result = difference

                }
                else -> {
                    _finalResult.value.result = 0.0
                }
            }
        } catch (e: Exception){
            _finalResult.value.result = 0.0
        }

        return _finalResult.value.result
    }








    fun addition(vararg values: Double){
        val sum = values.sum()
        //_finalResult.value gives the current state. Default state is 0.0.
        //_finalResult.value.copy(result = .....) changes the value of just result parameter keeping other values same
        _finalResult.value = _finalResult.value.copy(result = _finalResult.value.result + sum)
    }
}