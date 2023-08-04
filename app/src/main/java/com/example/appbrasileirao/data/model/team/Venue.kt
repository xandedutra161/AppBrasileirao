package com.example.appbrasileirao.data.model.team

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Venue(
    @SerializedName("address")
    val address: String? = null,
    @SerializedName("capacity")
    val capacity: Int,
    @SerializedName("city")
    val city: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("surface")
    val surface: String
): Serializable