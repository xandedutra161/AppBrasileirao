package com.example.appbrasileirao.data.model.team

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TeamResponse(
    @SerializedName("response")
    val response: List<Response>
): Serializable


