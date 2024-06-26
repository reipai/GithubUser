package com.reivai.githubusersearch.network

import com.reivai.githubusersearch.network.model.User
import com.reivai.githubusersearch.network.response.DetailUserResponse
import com.reivai.githubusersearch.network.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkApi {

    @GET("search/users")
    fun getSearch(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowersUser(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    fun getFollowingUser(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}