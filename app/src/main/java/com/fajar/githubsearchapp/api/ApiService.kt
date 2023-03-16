package com.fajar.githubsearchapp.api

import com.fajar.githubsearchapp.data.model.DetailUserResponse
import com.fajar.githubsearchapp.data.model.User
import com.fajar.githubsearchapp.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @Headers("Authorization: token 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")
    @GET("search/users")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}