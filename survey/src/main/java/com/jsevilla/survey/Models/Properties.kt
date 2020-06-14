package com.jsevilla.survey.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Properties(
    @SerializedName(value = "title")
    @Expose
    var title: String,

    @SerializedName(value = "intro_message")
    @Expose
    val introMessage: String,

    @SerializedName(value = "end_message")
    @Expose
    val endMessage: String,

    @SerializedName(value = "skip_intro")
    @Expose
    val skipIntro: Boolean

) : Serializable