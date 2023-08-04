package com.example.appbrasileirao.data.remote

import com.example.appbrasileirao.data.model.player.PlayerResponse
import com.example.appbrasileirao.data.model.team.TeamResponse
import com.example.appbrasileirao.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ServiceApi {

    @Headers(
        "${Constants.HEADER_KEY_RAPIDAPI_KEY}: ${Constants.HEADER_VALUE_RAPIDAPI_KEY}",
        "${Constants.HEADER_KEY_RAPIDAPI_HOST}: ${Constants.HEADER_VALUE_RAPIDAPI_HOST}"
    )
    @GET("teams")
    suspend fun listTeams(
        @Query("league") league: String = "71",
        @Query("season") season: String = "2023"
    ) : Response<TeamResponse>

    @Headers(
        "${Constants.HEADER_KEY_RAPIDAPI_KEY}: ${Constants.HEADER_VALUE_RAPIDAPI_KEY}",
        "${Constants.HEADER_KEY_RAPIDAPI_HOST}: ${Constants.HEADER_VALUE_RAPIDAPI_HOST}"
    )

    @GET("players?league=71&season=2023")
    suspend fun getPlayers(
        @Query("team") teamId: Int
    ) : Response<PlayerResponse>
}