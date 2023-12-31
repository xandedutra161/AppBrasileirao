package com.example.appbrasileirao.data.model.player

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PlayerResponse(
    @SerializedName("response")
    val response: List<Response>
) : Serializable

data class Response(
    @SerializedName("player")
    val player: Player,
): Serializable

data class Player(
    @SerializedName("age")
    val age: Int,
    @SerializedName("birth")
    val birth: Birth,
    @SerializedName("firstname")
    val firstname: String,
    @SerializedName("height")
    val height: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("injured")
    val injured: Boolean,
    @SerializedName("lastname")
    val lastname: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("nationality")
    val nationality: String,
    @SerializedName("photo")
    val photo: String,
    @SerializedName("weight")
    val weight: String
): Serializable


data class Birth(
    @SerializedName("country")
    val country: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("place")
    val place: String
): Serializable

