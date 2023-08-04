package com.example.appbrasileirao.data.model.team

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Response(
    @SerializedName("team")
    val team: Team,
    @SerializedName("venue")
    val venue: Venue
): Serializable

