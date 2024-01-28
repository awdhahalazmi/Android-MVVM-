package com.joincoded.islamicloan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.joincoded.islamicloan.ui.theme.IslamicLoanTheme
import com.joincoded.islamicloan.viewmodel.LoanViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IslamicLoanTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoanCalculator()
                }
            }
        }
    }
}

@Composable
fun LoanCalculator(viewModel: LoanViewModel = viewModel()) {
    val viewModel : LoanViewModel = viewModel()
    var loanAmount by remember { mutableStateOf(0f) }
    var periodInMonths by remember { mutableStateOf(0) }
    var salary by remember { mutableStateOf(0f) }
    val monthlyInstallment by viewModel.monthlyInstallment.collectAsState()
    val maxInstallment by viewModel.maxInstallment.collectAsState()
    val calculatedLoanPeriod by viewModel.calculatedLoanPeriod.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = loanAmount.toString(),
            onValueChange = { loanAmount = it.toFloatOrNull() ?: 0f },
            label = { Text("The Loan Amount") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = periodInMonths.toString(),
            onValueChange = { periodInMonths = it.toIntOrNull() ?: 0 },
            label = { Text("Period in Months") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = salary.toString(),
            onValueChange = { salary = it.toFloatOrNull() ?: 0f },
            label = { Text("Salary") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            viewModel.calculateMonthlyInstallment(loanAmount, periodInMonths)
            val maxInstallment = 0.4 * salary
            val calculatedLoanPeriod = if (maxInstallment > 0) loanAmount / maxInstallment else 0


            viewModel.setMaxInstallment(maxInstallment.toFloat())
            viewModel.setCalculatedLoanPeriod(calculatedLoanPeriod.toInt())
        }) {
            Text("Calculate Installment")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Spacer(modifier = Modifier.height(16.dp))

        Text("Monthly Installment: $monthlyInstallment")
        Text("Max Installment (40% of Salary): $maxInstallment")
        Text("Calculated Loan Period: $calculatedLoanPeriod months")


    }
}

@Composable
@Preview(showBackground = true)
fun DefaultPreview() {
    IslamicLoanTheme {
        LoanCalculator()
    }}
