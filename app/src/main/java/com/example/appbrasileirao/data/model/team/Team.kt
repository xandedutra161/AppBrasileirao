package com.example.appbrasileirao.data.model.team

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Team(
    @SerializedName("code")
    val code: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("founded")
    val founded: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("logo")
    val logo: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("national")
    val national: Boolean
): Serializable
