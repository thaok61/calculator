package com.me.calculator.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CalculatorViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CalculatorUiState())
    val uiState: StateFlow<CalculatorUiState> = _uiState.asStateFlow()

    fun calculate(calculation: Char) {
        _uiState.update {
            var newCalculation = ""
            if (it.calculation.isEmpty()) {
                newCalculation = "${it.result} $calculation";
            }

            it.copy(calculation = newCalculation, isCalculating = true, isFistTime = true)
        }
    }

    fun addNum(digit: Char, times: Int) {
        _uiState.update {
            if (it.isFistTime) {
                it.copy(result = "$digit", isFistTime = false)
            } else {
                if (it.result.length < 10) {
                    var result = 0L
                    result = if (times > 1) {
                        "${it.result}$digit$digit".toLong()
                    } else {
                        "${it.result}$digit".toLong()
                    }
                    it.copy(result = "$result")
                } else {
                    it
                }
            }
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

    fun processCalculation(calculation: String, result: String): Long {
        var data = result.toLong()
        if (calculation.isNotEmpty()) {
            val list = calculation.split(" ")
            val first = list[0].toLong()
            val second = result.toLong()
            data = when (list[1]) {
                "÷" -> first / second
                "×" -> first * second
                "+" -> first + second
                "−" -> first - second
                else -> {
                    0
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
    val isFistTime:Boolean = true,
)