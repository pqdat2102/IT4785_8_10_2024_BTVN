package com.example.claculatorui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity(), View.OnClickListener
{
    private lateinit var resultText: TextView
    var expression = StringBuilder()
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        resultText = findViewById(R.id.result)
        findViewById<Button>(R.id.button_0).setOnClickListener(this);
        findViewById<Button>(R.id.button_1).setOnClickListener(this);
        findViewById<Button>(R.id.button_2).setOnClickListener(this);
        findViewById<Button>(R.id.button_3).setOnClickListener(this);
        findViewById<Button>(R.id.button_4).setOnClickListener(this);
        findViewById<Button>(R.id.button_5).setOnClickListener(this);
        findViewById<Button>(R.id.button_6).setOnClickListener(this);
        findViewById<Button>(R.id.button_7).setOnClickListener(this);
        findViewById<Button>(R.id.button_8).setOnClickListener(this);
        findViewById<Button>(R.id.button_9).setOnClickListener(this);
        findViewById<Button>(R.id.button_CE).setOnClickListener(this);
        findViewById<Button>(R.id.button_C).setOnClickListener(this);
        findViewById<Button>(R.id.button_BS).setOnClickListener(this);
        findViewById<Button>(R.id.button_Divide).setOnClickListener(this);
        findViewById<Button>(R.id.button_Multiply).setOnClickListener(this);
        findViewById<Button>(R.id.button_Minus).setOnClickListener(this);
        findViewById<Button>(R.id.button_Plus).setOnClickListener(this);
        findViewById<Button>(R.id.button_Equal).setOnClickListener(this);
        findViewById<Button>(R.id.button_Dot).setOnClickListener(this);
        findViewById<Button>(R.id.button_PDM).setOnClickListener(this);
    }
    override fun onClick(p0: View?){
        val id = p0?.id
        when (id) {
            R.id.button_0 -> addDigit(0)
            R.id.button_1 -> addDigit(1)
            R.id.button_2 -> addDigit(2)
            R.id.button_3 -> addDigit(3)
            R.id.button_4 -> addDigit(4)
            R.id.button_5 -> addDigit(5)
            R.id.button_6 -> addDigit(6)
            R.id.button_7 -> addDigit(7)
            R.id.button_8 -> addDigit(8)
            R.id.button_9 -> addDigit(9)
            R.id.button_Plus -> addOperator("+")
            R.id.button_Minus -> addOperator("-")
            R.id.button_Multiply -> addOperator("*")
            R.id.button_Divide -> addOperator("/")
            R.id.button_CE -> clearEntry()
            R.id.button_Equal -> calculateResult()
            R.id.button_PDM -> toggleSign()
            R.id.button_BS -> backspace()
            R.id.button_C -> clearExpression()
        }
    }
    private fun backspace() {
        if (expression.isNotEmpty()) {
            expression.deleteCharAt(expression.length - 1)
            resultText.text = expression.toString()
        }
    }
    private fun toggleSign() {
        if (expression.isNotEmpty()) {
            if (expression.last().isDigit()) {
                val currentNumber = expression.split(" ").last()
                val newNumber = if (currentNumber.startsWith("-")) {
                    currentNumber.substring(1)
                } else {
                    "-$currentNumber"
                }

                expression.delete(expression.length - currentNumber.length, expression.length)
                expression.append(newNumber)
                resultText.text = expression.toString()
            }
        }
    }
    private fun addDigit(digit: Int) {
        expression.append(digit)
        resultText.text = expression.toString()
    }
    private fun addOperator(operator: String) {
        if (expression.isNotEmpty() && expression.last().isDigit()) {
            expression.append(" $operator ")
            resultText.text = expression.toString()
        }
    }
    private fun clearEntry() {
        if (expression.isNotEmpty()) {
            val lastEntry = expression.split(" ").last()
            expression.delete(expression.length - lastEntry.length, expression.length)
            resultText.text = expression.toString()
        }
    }
    private fun  clearExpression() {
        expression.clear()
        resultText.text = "0"
    }
    private fun calculateResult() {
        if (expression.isEmpty()) {
            resultText.text = "0"
            return
        }

        try {
            val result = evaluateExpression(expression.toString())
            expression.clear()
            resultText.text = result.toString()
        } catch (e: Exception) {
            resultText.text = "0"
        }
    }

    private fun evaluateExpression(expr: String): Int {
        val tokens = expr.split(" ").toMutableList()
        var i = 0
        while (i < tokens.size) {
            if (tokens[i] == "*" || tokens[i] == "/") {
                val left = tokens[i - 1].toInt()
                val right = tokens[i + 1].toInt()
                val result = if (tokens[i] == "*") {
                    left * right
                } else {
                    if (right == 0) throw ArithmeticException("Cannot divide by zero")
                    left / right
                }
                tokens[i - 1] = result.toString()
                tokens.removeAt(i)
                tokens.removeAt(i)
                i--
            }
            i++
        }
        i = 0
        while (i < tokens.size) {
            if (tokens[i] == "+" || tokens[i] == "-") {
                val left = tokens[i - 1].toInt()
                val right = tokens[i + 1].toInt()
                val result = if (tokens[i] == "+") {
                    left + right
                } else {
                    left - right
                }
                tokens[i - 1] = result.toString()
                tokens.removeAt(i)
                tokens.removeAt(i)
                i--
            }
            i++
        }
        return tokens[0].toInt()
    }
}