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

        initializeButtonLists(binding=binding)

        configureNumberInput()
        configureModifierButtons()

    }

    /**
     * Initializes the lists of buttons for numbers, operators, and modifiers.
     * This allows for easy access to all buttons in the calculator layout.
     *
     * @param binding [ActivityMainBinding] The binding object for the main activity layout.
     */
    private fun initializeButtonLists(binding: ActivityMainBinding)
    {
        // Initialize number buttons
        numberButtons = listOf(
            binding.zeroButton, binding.oneButton, binding.twoButton,
            binding.threeButton, binding.fourButton, binding.fiveButton,
            binding.sixButton, binding.sevenButton, binding.eightButton,
            binding.nineButton, binding.decimalButton
        )

        // Initialize operator buttons
        operatorButtons = listOf(
            binding.plusButton, binding.minusButton,
            binding.multiplyButton, binding.divideButton, binding.percentButton
        )

        modifierButtons = listOf(
            binding.plusMinusButton,
            binding.clearButton, binding.deleteButton
        )
    }

    /**
     * Configures the number input buttons to handle clicks and update the result EditText.
     * It prevents multiple decimal points in the current number and handles leading zeros.
     */
    private fun configureNumberInput()
    {
        numberButtons.forEach { button ->
            button.setOnClickListener {
                val input = button.text.toString()
                val currentResultText = binding.resultEditText.text.toString()

                // Prevent multiple decimal points in the current number
                if (input == "." && currentResultText.contains("."))
                {
                    return@setOnClickListener // Do nothing if a decimal already exists
                }

                // If the current result is "0" and input is not ".", replace it
                if (currentResultText == "0" && input != ".")
                {
                    binding.resultEditText.setText(input)
                }
                else
                {
                    binding.resultEditText.append(input)
                }
            }
        }
    }

    private fun configureModifierButtons()
    {
        modifierButtons.forEach { button ->
            button.setOnClickListener {
                when (button)
                {
                    binding.clearButton -> binding.resultEditText.setText("0")
                    binding.deleteButton ->
                    {
                        val currentText = binding.resultEditText.text.toString()
                        if (currentText.isNotEmpty())
                        {
                            val newText = currentText.dropLast(1)

                            // If only "-" remains after deleting, reset to "0"
                            if (newText == "-" || newText.isEmpty())
                            {
                                binding.resultEditText.setText("0")
                            }
                            else
                            {
                                binding.resultEditText.setText(newText)
                            }
                        }
                    }
                    binding.plusMinusButton ->
                    {
                        val currentText = binding.resultEditText.text.toString()
                        if (currentText.isNotEmpty())
                        {
                            // don't allow changing sign if the current text is "0" or empty
                            if (currentText == "0" || currentText.isEmpty())
                            {
                                return@setOnClickListener // Do nothing
                            }
                            // if the current text is already negative, remove the negative sign
                            if (currentText.startsWith("-"))
                            {
                                binding.resultEditText.setText(currentText.removePrefix("-"))
                            }
                            else
                            {
                                val prefixedCurrentText = "-$currentText"
                                binding.resultEditText.setText(prefixedCurrentText)
                            }
                        }
                    }
                }
            }
        }
    }








}