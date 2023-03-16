package com.fajar.githubsearchapp.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fajar.githubsearchapp.data.model.DetailUserResponse
import com.fajar.githubsearchapp.di.ApiModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel : ViewModel() {

    val user = MutableLiveData<DetailUserResponse>()

    fun setUserDetail(username: String) {
        ApiModule.apiService.getUserDetail(username)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    user.postValue(response.body())
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })

    }

    fun getUserDetail(): LiveData<DetailUserResponse> {
        return user
    }
}
