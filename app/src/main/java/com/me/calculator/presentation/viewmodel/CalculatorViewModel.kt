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
                newCalculation = " ${it.result} $calculation";
            }

            it.copy(calculation = newCalculation, isCalculating = true)
        }
    }

    fun addNum(digit: Char) {
        if (digit.isDigit()) {
            _uiState.update {
                if (it.isFistTime) {
                    it.copy(result = "$digit",  isFistTime = false)
                } else {
                    it.copy(result = "${it.result}$digit")
                }
            }
        }
    }

}

data class CalculatorUiState(
    val result: String = "0",
    val calculation: String = "",
    val isCalculating: Boolean = false,
    val isFistTime:Boolean = true,

    )