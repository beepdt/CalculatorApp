package com.example.calculatorapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CalculatorViewModel: ViewModel() {
    private val _finalResult = mutableStateOf(CalculatorData())
    val finalResult:State<Double> get() = mutableStateOf(_finalResult.value.result)

    private var _inputText = mutableStateOf("")


    fun onButtonClick(button: String){
            _inputText.value = _inputText.value+button
    }

    fun addition(vararg values: Double){
        val sum = values.sum()
        //_finalResult.value gives the current state. Default state is 0.0.
        //_finalResult.value.copy(result = .....) changes the value of just result parameter keeping other values same
        _finalResult.value = _finalResult.value.copy(result = _finalResult.value.result + sum)
    }
}