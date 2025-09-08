package br.com.fiap.ecotrack.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object EmissionState {
    var currentEmissionComparison by mutableStateOf<EmissionComparison?>(null)
        private set
    
    fun setEmissionComparison(comparison: EmissionComparison) {
        currentEmissionComparison = comparison
    }
    
    fun clearEmissionComparison() {
        currentEmissionComparison = null
    }
}
