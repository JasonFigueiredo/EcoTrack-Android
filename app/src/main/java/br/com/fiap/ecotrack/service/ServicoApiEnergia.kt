package br.com.fiap.ecotrack.service

import br.com.fiap.ecotrack.model.RequisicaoEmissaoEnergia
import br.com.fiap.ecotrack.model.RespostaEmissaoEnergia
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ServicoApiEnergia {
    @POST("estimate")
    suspend fun estimarEmissoesEnergia(
        @Header("Authorization") authHeader: String,
        @Body request: RequisicaoEmissaoEnergia
    ): Response<RespostaEmissaoEnergia>
}
