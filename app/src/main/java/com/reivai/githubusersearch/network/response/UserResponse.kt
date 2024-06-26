package com.reivai.githubusersearch.network.response

import com.google.gson.annotations.SerializedName
import com.reivai.githubusersearch.network.model.User

data class UserResponse (
    @SerializedName("items")
    val items : ArrayList<User>
)