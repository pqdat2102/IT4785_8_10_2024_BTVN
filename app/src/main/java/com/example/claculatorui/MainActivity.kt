package com.example.claculatorui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

class MainActivity : AppCompatActivity() {

    private var operand1 = ""
    private var operand2 = ""
    private var operator = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val resultView: MaterialTextView = findViewById(R.id.result)

        // Thiết lập sự kiện click cho các nút số
        findViewById<MaterialButton>(R.id.button_0).setOnClickListener { appendToResult("0") }
        findViewById<MaterialButton>(R.id.button_1).setOnClickListener { appendToResult("1") }
        findViewById<MaterialButton>(R.id.button_2).setOnClickListener { appendToResult("2") }
        findViewById<MaterialButton>(R.id.button_3).setOnClickListener { appendToResult("3") }
        findViewById<MaterialButton>(R.id.button_4).setOnClickListener { appendToResult("4") }
        findViewById<MaterialButton>(R.id.button_5).setOnClickListener { appendToResult("5") }
        findViewById<MaterialButton>(R.id.button_6).setOnClickListener { appendToResult("6") }
        findViewById<MaterialButton>(R.id.button_7).setOnClickListener { appendToResult("7") }
        findViewById<MaterialButton>(R.id.button_8).setOnClickListener { appendToResult("8") }
        findViewById<MaterialButton>(R.id.button_9).setOnClickListener { appendToResult("9") }

        // Thiết lập sự kiện click cho các nút phép toán
        findViewById<MaterialButton>(R.id.button_Plus).setOnClickListener { setOperator("+") }
        findViewById<MaterialButton>(R.id.button_Minus).setOnClickListener { setOperator("-") }
        findViewById<MaterialButton>(R.id.button_Multiply).setOnClickListener { setOperator("*") }
        findViewById<MaterialButton>(R.id.button_Divide).setOnClickListener { setOperator("/") }

        // Thiết lập sự kiện click cho các nút chức năng
        findViewById<MaterialButton>(R.id.button_Equal).setOnClickListener { calculateResult(resultView) }
        findViewById<MaterialButton>(R.id.button_C).setOnClickListener { clear() }
        findViewById<MaterialButton>(R.id.button_CE).setOnClickListener { clearEntry() }
        findViewById<MaterialButton>(R.id.button_BS).setOnClickListener { backspace() }
    }

    private fun appendToResult(value: String) {
        if (operator.isEmpty()) {
            operand1 += value
            updateResult(operand1)
        } else {
            operand2 += value
            updateResult(operand2)
        }
    }

    private fun setOperator(op: String) {
        if (operand1.isNotEmpty() && operand2.isEmpty()) {
            operator = op
        }
    }

    private fun calculateResult(resultView: MaterialTextView) {
        if (operand1.isNotEmpty() && operand2.isNotEmpty()) {
            val result = when (operator) {
                "+" -> operand1.toDouble() + operand2.toDouble()
                "-" -> operand1.toDouble() - operand2.toDouble()
                "*" -> operand1.toDouble() * operand2.toDouble()
                "/" -> {
                    if (operand2 == "0") {
                        resultView.text = "Error"
                        return
                    } else {
                        operand1.toDouble() / operand2.toDouble()
                    }
                }
                else -> 0.0
            }

            // Kiểm tra xem kết quả có phải là số nguyên không
            val displayResult = if (result == result.toInt().toDouble()) {
                result.toInt().toString()  // Hiển thị dưới dạng số nguyên
            } else {
                result.toString()           // Hiển thị dưới dạng số thập phân
            }

            resultView.text = displayResult
            resetOperands(displayResult)
        }
    }

    private fun updateResult(value: String) {
        findViewById<MaterialTextView>(R.id.result).text = value
    }

    private fun resetOperands(result: String) {
        operand1 = result
        operand2 = ""
        operator = ""
    }

    private fun clear() {
        operand1 = ""
        operand2 = ""
        operator = ""
        updateResult("0")
    }

    private fun clearEntry() {
        if (operand2.isNotEmpty()) {
            operand2 = ""
        } else if (operator.isNotEmpty()) {
            operator = ""
        } else if (operand1.isNotEmpty()) {
            operand1 = ""
        }
        updateResult("0")
    }

    private fun backspace() {
        if (operand2.isNotEmpty()) {
            operand2 = operand2.dropLast(1)
            updateResult(operand2)
        } else if (operator.isNotEmpty()) {
            operator = ""
        } else if (operand1.isNotEmpty()) {
            operand1 = operand1.dropLast(1)
            updateResult(operand1)
        }
    }
}

