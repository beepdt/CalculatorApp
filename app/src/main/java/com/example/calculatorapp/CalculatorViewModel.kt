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
        val regex = Regex("""\d+(\.\d+)?|[+\-x÷()]""")
        val matches = regex.findAll(input)

        for ((index, match) in matches.withIndex()) {
            val token = match.value

            // Handle negative numbers
            if (token == "-" && (index == 0 || tokens.lastOrNull() in listOf("(", "+", "-", "x", "÷"))) {
                // If "-" is at the start or after an operator/parenthesis, it's a negative number
                val nextMatch = matches.elementAtOrNull(index + 1)
                if (nextMatch != null && nextMatch.value.matches(Regex("""\d+(\.\d+)?"""))) {
                    tokens.add(token + nextMatch.value) // Combine "-" with the next number
                    continue
                }
            }

            tokens.add(token)
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
                token.matches(Regex("""\d+(\.\d+)?""")) -> {
                    // If it's a number, push onto the stack
                    stack.add(token.toDouble())
                }
                token in listOf("+", "-", "x", "÷") -> {
                    // If it's an operator, pop two values, apply the operator, and push the result
                    if (stack.size < 2) {
                        throw IllegalArgumentException("Invalid RPN expression")
                    }
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
                    stack.add(result) // Use `add()` to push the result onto the stack
                }
            }
        }

        if (stack.size != 1) {
            throw IllegalArgumentException("Invalid RPN expression")
        }

        return stack.last() // The final result is the last item on the stack
    }










}