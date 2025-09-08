package br.com.fiap.ecotrack.model

data class TarifaEnergia(
    val estado: String,
    val sigla: String,
    val regiao: String,
    val tarifaResidencial: Double, // R$/kWh
    val tarifaComercial: Double,   // R$/kWh
    val tarifaIndustrial: Double,  // R$/kWh
    val distribuidora: String,
    val ultimaAtualizacao: String
)

// Enum para tipos de consumidor
enum class TipoConsumidor(val displayName: String) {
    RESIDENCIAL("Residencial"),
    COMERCIAL("Comercial"),
    INDUSTRIAL("Industrial")
}

// Dados das tarifas por estado (valores aproximados de 2024)
fun getTarifasPorEstado(): List<TarifaEnergia> {
    return listOf(
        // Região Sudeste
        TarifaEnergia(
            estado = "São Paulo",
            sigla = "SP",
            regiao = "Sudeste",
            tarifaResidencial = 0.65,
            tarifaComercial = 0.72,
            tarifaIndustrial = 0.58,
            distribuidora = "Enel SP",
            ultimaAtualizacao = "2024"
        ),
        TarifaEnergia(
            estado = "Rio de Janeiro",
            sigla = "RJ",
            regiao = "Sudeste",
            tarifaResidencial = 0.68,
            tarifaComercial = 0.75,
            tarifaIndustrial = 0.62,
            distribuidora = "Light",
            ultimaAtualizacao = "2024"
        ),
        TarifaEnergia(
            estado = "Minas Gerais",
            sigla = "MG",
            regiao = "Sudeste",
            tarifaResidencial = 0.62,
            tarifaComercial = 0.69,
            tarifaIndustrial = 0.55,
            distribuidora = "CEMIG",
            ultimaAtualizacao = "2024"
        ),
        TarifaEnergia(
            estado = "Espírito Santo",
            sigla = "ES",
            regiao = "Sudeste",
            tarifaResidencial = 0.70,
            tarifaComercial = 0.77,
            tarifaIndustrial = 0.64,
            distribuidora = "Energisa ES",
            ultimaAtualizacao = "2024"
        ),
        
        // Região Sul
        TarifaEnergia(
            estado = "Rio Grande do Sul",
            sigla = "RS",
            regiao = "Sul",
            tarifaResidencial = 0.58,
            tarifaComercial = 0.65,
            tarifaIndustrial = 0.52,
            distribuidora = "CEEE",
            ultimaAtualizacao = "2024"
        ),
        TarifaEnergia(
            estado = "Santa Catarina",
            sigla = "SC",
            regiao = "Sul",
            tarifaResidencial = 0.60,
            tarifaComercial = 0.67,
            tarifaIndustrial = 0.54,
            distribuidora = "Celesc",
            ultimaAtualizacao = "2024"
        ),
        TarifaEnergia(
            estado = "Paraná",
            sigla = "PR",
            regiao = "Sul",
            tarifaResidencial = 0.59,
            tarifaComercial = 0.66,
            tarifaIndustrial = 0.53,
            distribuidora = "Copel",
            ultimaAtualizacao = "2024"
        ),
        
        // Região Nordeste
        TarifaEnergia(
            estado = "Bahia",
            sigla = "BA",
            regiao = "Nordeste",
            tarifaResidencial = 0.72,
            tarifaComercial = 0.79,
            tarifaIndustrial = 0.66,
            distribuidora = "Coelba",
            ultimaAtualizacao = "2024"
        ),
        TarifaEnergia(
            estado = "Pernambuco",
            sigla = "PE",
            regiao = "Nordeste",
            tarifaResidencial = 0.75,
            tarifaComercial = 0.82,
            tarifaIndustrial = 0.69,
            distribuidora = "Celpe",
            ultimaAtualizacao = "2024"
        ),
        TarifaEnergia(
            estado = "Ceará",
            sigla = "CE",
            regiao = "Nordeste",
            tarifaResidencial = 0.78,
            tarifaComercial = 0.85,
            tarifaIndustrial = 0.72,
            distribuidora = "Enel CE",
            ultimaAtualizacao = "2024"
        ),
        TarifaEnergia(
            estado = "Maranhão",
            sigla = "MA",
            regiao = "Nordeste",
            tarifaResidencial = 0.80,
            tarifaComercial = 0.87,
            tarifaIndustrial = 0.74,
            distribuidora = "Cemar",
            ultimaAtualizacao = "2024"
        ),
        
        // Região Norte
        TarifaEnergia(
            estado = "Amazonas",
            sigla = "AM",
            regiao = "Norte",
            tarifaResidencial = 0.85,
            tarifaComercial = 0.92,
            tarifaIndustrial = 0.79,
            distribuidora = "Energisa AM",
            ultimaAtualizacao = "2024"
        ),
        TarifaEnergia(
            estado = "Pará",
            sigla = "PA",
            regiao = "Norte",
            tarifaResidencial = 0.82,
            tarifaComercial = 0.89,
            tarifaIndustrial = 0.76,
            distribuidora = "Celpa",
            ultimaAtualizacao = "2024"
        ),
        TarifaEnergia(
            estado = "Rondônia",
            sigla = "RO",
            regiao = "Norte",
            tarifaResidencial = 0.88,
            tarifaComercial = 0.95,
            tarifaIndustrial = 0.82,
            distribuidora = "Energisa RO",
            ultimaAtualizacao = "2024"
        ),
        
        // Região Centro-Oeste
        TarifaEnergia(
            estado = "Goiás",
            sigla = "GO",
            regiao = "Centro-Oeste",
            tarifaResidencial = 0.66,
            tarifaComercial = 0.73,
            tarifaIndustrial = 0.60,
            distribuidora = "Enel GO",
            ultimaAtualizacao = "2024"
        ),
        TarifaEnergia(
            estado = "Mato Grosso",
            sigla = "MT",
            regiao = "Centro-Oeste",
            tarifaResidencial = 0.71,
            tarifaComercial = 0.78,
            tarifaIndustrial = 0.65,
            distribuidora = "Energisa MT",
            ultimaAtualizacao = "2024"
        ),
        TarifaEnergia(
            estado = "Mato Grosso do Sul",
            sigla = "MS",
            regiao = "Centro-Oeste",
            tarifaResidencial = 0.69,
            tarifaComercial = 0.76,
            tarifaIndustrial = 0.63,
            distribuidora = "Energisa MS",
            ultimaAtualizacao = "2024"
        ),
        TarifaEnergia(
            estado = "Distrito Federal",
            sigla = "DF",
            regiao = "Centro-Oeste",
            tarifaResidencial = 0.67,
            tarifaComercial = 0.74,
            tarifaIndustrial = 0.61,
            distribuidora = "CEB",
            ultimaAtualizacao = "2024"
        )
    )
}

// Função para obter tarifa por estado
fun getTarifaPorEstado(siglaEstado: String, tipoConsumidor: TipoConsumidor): TarifaEnergia? {
    val tarifas = getTarifasPorEstado()
    return tarifas.find { it.sigla.equals(siglaEstado, ignoreCase = true) }
}

// Função para calcular custo
fun calcularCustoEnergia(
    consumoKwh: Double,
    siglaEstado: String,
    tipoConsumidor: TipoConsumidor = TipoConsumidor.RESIDENCIAL
): Double? {
    val tarifa = getTarifaPorEstado(siglaEstado, tipoConsumidor) ?: return null
    
    val tarifaPorKwh = when (tipoConsumidor) {
        TipoConsumidor.RESIDENCIAL -> tarifa.tarifaResidencial
        TipoConsumidor.COMERCIAL -> tarifa.tarifaComercial
        TipoConsumidor.INDUSTRIAL -> tarifa.tarifaIndustrial
    }
    
    return consumoKwh * tarifaPorKwh
}
