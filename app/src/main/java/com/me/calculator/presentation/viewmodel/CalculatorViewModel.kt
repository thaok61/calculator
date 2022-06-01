package com.me.calculator.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.*
import kotlin.math.pow

class CalculatorViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CalculatorUiState())
    val uiState: StateFlow<CalculatorUiState> = _uiState.asStateFlow()

    fun calculate(calculation: Char) {
        _uiState.update {
            var newCalculation = ""
            if (it.isCalculating) {
                newCalculation = "${processCalculation(it.calculation, it.result)} $calculation"
            }
            if (it.calculation.isEmpty()) {
                newCalculation = "${it.result} $calculation";
            }

            it.copy(calculation = newCalculation, isCalculating = true, isFistTime = true)
        }
    }

    fun addDot(digit: Char) {
        _uiState.update {
            if (!it.result.contains(digit)) {
                val result = it.result + digit
                it.copy(result = result)
            } else {
                it
            }
        }
    }

    fun addNum(digit: Char, times: Int) {
        _uiState.update {
            if (it.isFistTime) {
                it.copy(result = "$digit", isFistTime = false)
            }else if (it.result == "0") {
                it.copy(result = "$digit")
            } else {
                if (it.result.length < 10) {
                    val result = if (times > 1) {
                        "${it.result}$digit$digit"
                    } else {
                        "${it.result}$digit"
                    }
                    it.copy(result = result)
                } else {
                    it
                }
            }
        }
    }

    fun addPlusMinus() {
        _uiState.update {
            var result = it.result
            if (result == "0") {
                return
            }
            result = if (result.contains('-')) {
                result.replace("-","", true)
            } else {
                "-${it.result}"
            }

            it.copy(result = result)
        }
    }

    fun clear() {
        _uiState.update {
            it.copy(
                result = "0",
                calculation = "",
                isCalculating = false,
                isFistTime = true,
            )
        }
    }

    fun equal() {
        _uiState.update {
            val result = processCalculation(it.calculation, it.result)
            it.copy(result = "$result", calculation = "", isFistTime = true, isCalculating = false)
        }
    }

    private fun processCalculation(calculation: String, result: String): Double {
        var data = result.toDouble()
        if (calculation.isNotEmpty()) {
            val list = calculation.split(" ")
            val first = list[0].toDouble()
            val second = result.toDouble()
            data = when (list[1]) {
                "÷" -> first / second
                "×" -> first * second
                "+" -> first + second
                "−" -> first - second
                "^" -> first.pow(second)
                else -> {
                    0.0
                }
            }
        }

        return data;
    }

}

data class CalculatorUiState(
    val result: String = "0",
    val calculation: String = "",
    val isCalculating: Boolean = false,
    val isFistTime: Boolean = true,
)