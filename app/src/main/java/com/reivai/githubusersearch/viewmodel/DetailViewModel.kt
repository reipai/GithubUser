package com.reivai.githubusersearch.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.reivai.githubusersearch.network.NetworkClient
import com.reivai.githubusersearch.network.response.DetailUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    val detailUser = MutableLiveData<DetailUserResponse>()

    fun setDetailUser(username: String) {
        NetworkClient.apiInstance
            .getDetailUser(username)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful) {
                        detailUser.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }

            })
    }

    fun getDetailUser(): LiveData<DetailUserResponse> {
        return detailUser
    }
}