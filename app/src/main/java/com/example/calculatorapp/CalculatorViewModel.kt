package com.example.calculatorapp

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




//implementing the Shunt Yard Algorithm
    private fun calculateResult():Double {

    return try {
        val inputText = _calculatorData.value.equation

        //tokenizing the  input
        val tokens = tokenize(inputText)

        //reverse polish notation
        val rpn = convertToRPN(tokens)

        evaluateRPN(rpn)

    }catch(e: Exception){
        0.0
    }
}

    private fun tokenize(input: String): List<String> {
        val tokens = mutableListOf<String>()
        val regex = Regex("""\d+(\.\d+)?|[+\-x÷()]""") // Match numbers, operators, and parentheses
        val matches = regex.findAll(input).toList()

        var index = 0
        while (index < matches.size) {
            val token = matches[index].value

            // Check if "-" is a negative sign
            if (token == "-" && (index == 0 || matches[index - 1].value in listOf("(", "+", "-", "x", "÷"))) {
                if (index + 1 < matches.size && matches[index + 1].value.matches(Regex("""\d+(\.\d+)?"""))) {
                    // Combine "-" with the next number
                    tokens.add(token + matches[index + 1].value)
                    index += 2 // Skip the next token since it's already combined
                    continue
                }
            }

            // Otherwise, add the token as is
            tokens.add(token)
            index++
        }

        return tokens
    }




    private fun convertToRPN(tokens: List<String>): List<String> {
        val output = mutableListOf<String>()
        val operators = ArrayDeque<String>()
        val precedence = mapOf("+" to 1, "-" to 1, "x" to 2, "÷" to 2)
        val rightAssociative = setOf<String>() // No right-associative operators here

        for (token in tokens) {
            when {
                token.matches(Regex("""-?\d+(\.\d+)?""")) -> { // If it's a number (including negative)
                    output.add(token)
                }
                token in precedence.keys -> { // If it's an operator
                    while (operators.isNotEmpty() &&
                        operators.last() in precedence.keys &&
                        (precedence[operators.last()]!! > precedence[token]!! ||
                                (precedence[operators.last()]!! == precedence[token]!! && token !in rightAssociative)) &&
                        operators.last() != "("
                    ) {
                        output.add(operators.removeLast())
                    }
                    operators.add(token) // Push the operator onto the stack
                }
                token == "(" -> { // If it's an open parenthesis
                    operators.add(token)
                }
                token == ")" -> { // If it's a closing parenthesis
                    while (operators.isNotEmpty() && operators.last() != "(") {
                        output.add(operators.removeLast())
                    }
                    if (operators.isEmpty() || operators.last() != "(") {
                        throw IllegalArgumentException("Mismatched parentheses")
                    }
                    operators.removeLast() // Remove the '('
                }
            }
        }

        while (operators.isNotEmpty()) {
            val op = operators.removeLast()
            if (op == "(") {
                throw IllegalArgumentException("Mismatched parentheses")
            }
            output.add(op)
        }

        return output
    }



    private fun evaluateRPN(rpn: List<String>): Double {
        val stack = ArrayDeque<Double>()

        for (token in rpn) {
            when {
                token.matches(Regex("""-?\d+(\.\d+)?""")) -> {
                    // Push numbers (including negatives) onto the stack
                    stack.add(token.toDouble())
                }
                token in listOf("+", "-", "x", "÷") -> {
                    // Operators: Pop two values and apply the operator
                    if (stack.size < 2) throw IllegalArgumentException("Invalid RPN expression")

                    val b = stack.removeLast()
                    val a = stack.removeLast()

                    val result = when (token) {
                        "+" -> a + b
                        "-" -> a - b
                        "x" -> a * b
                        "÷" -> {
                            if (b == 0.0) throw ArithmeticException("Division by zero")
                            a / b
                        }
                        else -> throw IllegalArgumentException("Unsupported operator: $token")
                    }
                    stack.add(result)
                }
            }
        }

        if (stack.size != 1) throw IllegalArgumentException("Invalid RPN expression")

        return stack.last() // Return the final result
    }

}