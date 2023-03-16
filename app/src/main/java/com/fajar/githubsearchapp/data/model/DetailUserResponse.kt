package com.fajar.githubsearchapp.data.model

import com.google.gson.annotations.SerializedName

data class DetailUserResponse(
    @SerializedName("login")
    val username : String,
    val id : Int,
    val avatar_url : String,
    val followers_url : String,
    val following_url : String,
    val name : String,
    val following : Int,
    val followers : Int
)