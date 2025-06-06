package ca.georgiancollege.ice5

import android.widget.Button
import ca.georgiancollege.ice5.databinding.ActivityMainBinding


class Calculator(private val binding: ActivityMainBinding) {

    private lateinit var numberButtons: List<Button>
    private lateinit var operatorButtons: List<Button>
    private lateinit var modifierButtons: List<Button>

    private var currentOperand: String = ""
    private var currentOperator: String = ""
    private var calculationDone: Boolean = false

    init {
        initializeButtonLists(binding = binding)
        configureNumberInput()
        configureModifierButtons()
        configureOperatorButtons()
    }

    private fun initializeButtonLists(binding: ActivityMainBinding) {
        numberButtons = listOf(
            binding.zeroButton, binding.oneButton, binding.twoButton,
            binding.threeButton, binding.fourButton, binding.fiveButton,
            binding.sixButton, binding.sevenButton, binding.eightButton,
            binding.nineButton, binding.decimalButton
        )

        operatorButtons = listOf(
            binding.plusButton, binding.minusButton,
            binding.multiplyButton, binding.divideButton, binding.percentButton
        )

        modifierButtons = listOf(
            binding.plusMinusButton,
            binding.clearButton, binding.deleteButton
        )
    }

    private fun configureNumberInput() {
        numberButtons.forEach { button ->
            button.setOnClickListener {
                val input = button.text.toString()
                val currentResultText = binding.resultEditText.text.toString()

                if (input == "." && currentResultText.contains(".")) return@setOnClickListener

                if (currentResultText == "0" && input != ".") {
                    binding.resultEditText.setText(input)
                } else {
                    if (calculationDone) {
                        binding.resultEditText.setText(input)
                        calculationDone = false
                    } else {
                        binding.resultEditText.append(input)
                    }
                }
            }
        }
    }

    private fun configureModifierButtons() {
        modifierButtons.forEach { button ->
            button.setOnClickListener {
                when (button) {
                    binding.clearButton -> {
                        binding.resultEditText.setText("0")
                        currentOperand = ""
                        currentOperator = ""
                        calculationDone = false
                    }
                    binding.deleteButton -> {
                        val currentText = binding.resultEditText.text.toString()
                        if (currentText.isNotEmpty()) {
                            val newText = currentText.dropLast(1)
                            binding.resultEditText.setText(if (newText.isEmpty() || newText == "-") "0" else newText)
                        }
                    }
                    binding.plusMinusButton -> {
                        val currentText = binding.resultEditText.text.toString()
                        if (currentText != "0") {
                            binding.resultEditText.setText(
                                if (currentText.startsWith("-"))
                                    currentText.removePrefix("-")
                                else
                                    "-$currentText"
                            )
                        }
                    }
                }
            }
        }
    }

    private fun configureOperatorButtons() {
        operatorButtons.forEach { button ->
            button.setOnClickListener {
                val value = binding.resultEditText.text.toString()
                when (button) {
                    binding.plusButton, binding.minusButton,
                    binding.multiplyButton, binding.divideButton -> {
                        if (currentOperator.isEmpty()) {
                            currentOperand = value
                            currentOperator = button.text.toString()
                            binding.resultEditText.setText("0")
                        }
                    }
                    binding.percentButton -> {}
                }
            }
        }

        binding.equalsButton.setOnClickListener {
            if (currentOperand.isNotEmpty() && currentOperator.isNotEmpty()) {
                val secondOperand = binding.resultEditText.text.toString()
                val result = performCalculation(currentOperand, secondOperand, currentOperator)
                binding.resultEditText.setText(result)
                currentOperand = ""
                currentOperator = ""
                calculationDone = true
            }
        }
    }

    private fun performCalculation(first: String, second: String, operator: String): String {
        try {
            val num1 = first.toFloat()
            val num2 = second.toFloat()
            var result = 0f

            if (operator == "+") {
                result = num1 + num2
            } else if (operator == "-") {
                result = num1 - num2
            } else if (operator == "×" || operator == "*") {
                result = num1 * num2
            } else if (operator == "÷" || operator == "/") {
                if (num2 != 0f) {
                    result = num1 / num2
                } else {
                    return "Error"
                }
            } else {
                return "Error"
            }

            return result.toString()
        } catch (e: Exception) {
            return "Error"
        }
    }


}