package br.com.fiap.ecotrack.service

import br.com.fiap.ecotrack.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ClimatiqApiService {
    @POST("estimate")
    suspend fun estimateEmissions(
        @Header("Authorization") authHeader: String,
        @Body request: EmissionRequest
    ): Response<EmissionResponse>
}
