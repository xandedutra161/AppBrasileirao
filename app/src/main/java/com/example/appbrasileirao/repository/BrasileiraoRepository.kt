package com.example.appbrasileirao.repository

import com.example.appbrasileirao.data.remote.ServiceApi
import javax.inject.Inject

class BrasileiraoRepository @Inject constructor(
    private val api: ServiceApi
) {
    suspend fun listTeams() = api.listTeams()
    suspend fun getPlayers(teamId: Int) = api.getPlayers(teamId)
}