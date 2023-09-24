import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import istt.com.scientificcalculator.R

class MainActivity : AppCompatActivity() {

    private lateinit var instantHistory: TextView
    private lateinit var resultView: TextView

    private var currentInput: String = ""
    private var currentOperator: String = ""
    private var firstOperand: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        instantHistory = findViewById(R.id.instantHistory)
        resultView = findViewById(R.id.ResultView)

        // Set click listeners for numeric and operator buttons
        val numberButtons = arrayOf(
            findViewById<Button>(R.id.zero),
            findViewById<Button>(R.id.one),
            findViewById<Button>(R.id.two),
            findViewById<Button>(R.id.three),
            findViewById<Button>(R.id.four),
            findViewById<Button>(R.id.five),
            findViewById<Button>(R.id.six),
            findViewById<Button>(R.id.seven),
            findViewById<Button>(R.id.eight),
            findViewById<Button>(R.id.nine),
            findViewById<Button>(R.id.dot)
        )

        val operatorButtons = arrayOf(
            findViewById<Button>(R.id.Plus),
            findViewById<Button>(R.id.Minus),
            findViewById<Button>(R.id.Multipication),
            findViewById<Button>(R.id.division),
            findViewById<Button>(R.id.squre)
            // Add more operators here
        )

        // Set click listeners for numeric buttons
        numberButtons.forEach { button ->
            button.setOnClickListener {
                onNumberClick(button.text.toString())
            }
        }

        // Set click listeners for operator buttons
        operatorButtons.forEach { button ->
            button.setOnClickListener {
                onOperatorClick(button.text.toString())
            }
        }
    }

    private fun onNumberClick(value: String) {
        currentInput += value
        instantHistory.text = currentInput
    }

    private fun onOperatorClick(operator: String) {
        if (currentInput.isNotEmpty()) {
            if (firstOperand == null) {
                firstOperand = currentInput.toDouble()
                currentInput = ""
                currentOperator = operator
                instantHistory.text = "$firstOperand $currentOperator"
            } else {
                val secondOperand = currentInput.toDouble()
                val result = performCalculation(firstOperand!!, secondOperand, currentOperator)
                resultView.text = result.toString()
                firstOperand = result
                currentInput = ""
                currentOperator = operator
                instantHistory.text = "$firstOperand $currentOperator"
            }
        }
    }

    private fun performCalculation(operand1: Double, operand2: Double, operator: String): Double {
        return when (operator) {
            "+" -> operand1 + operand2
            "-" -> operand1 - operand2
            "X" -> operand1 * operand2
            "/" -> operand1 / operand2
            "squre" -> operand1 * operand1
            // Add more operators here
            else -> throw IllegalArgumentException("Unknown operator: $operator")
        }
    }

    fun onEqualClick(view: View) {
        if (currentInput.isNotEmpty() && firstOperand != null) {
            val secondOperand = currentInput.toDouble()
            val result = performCalculation(firstOperand!!, secondOperand, currentOperator)
            resultView.text = result.toString()
            firstOperand = result
            currentInput = ""
            currentOperator = ""
            instantHistory.text = ""
        }
    }

    fun onClearClick(view: View) {
        currentInput = ""
        currentOperator = ""
        firstOperand = null
        instantHistory.text = ""
        resultView.text = ""
    }
}
