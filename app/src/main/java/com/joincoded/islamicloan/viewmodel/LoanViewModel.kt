package com.joincoded.islamicloan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class LoanViewModel : ViewModel() {
    private val _monthlyInstallment = MutableStateFlow(0f)
    val monthlyInstallment: StateFlow<Float> = _monthlyInstallment

    private val _maxInstallment = MutableStateFlow(0f)
    val maxInstallment: StateFlow<Float> = _maxInstallment

    private val _calculatedLoanPeriod = MutableStateFlow(0)
    val calculatedLoanPeriod: StateFlow<Int> = _calculatedLoanPeriod

    fun calculateMonthlyInstallment(loanAmount: Float, periodInMonths: Int) {
        viewModelScope.launch {
            // this is the  calculation
            val installment = if (periodInMonths > 0) loanAmount / periodInMonths else 0f

            // this is an Update of the state
            _monthlyInstallment.value = installment
        }
    }

    fun setMaxInstallment(maxInstallment: Float) {
        _maxInstallment.value = maxInstallment
    }

    fun setCalculatedLoanPeriod(calculatedLoanPeriod: Int) {
        _calculatedLoanPeriod.value = calculatedLoanPeriod
    }
}