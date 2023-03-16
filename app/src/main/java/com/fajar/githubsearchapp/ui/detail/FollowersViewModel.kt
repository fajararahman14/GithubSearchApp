package com.fajar.githubsearchapp.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fajar.githubsearchapp.data.model.User
import com.fajar.githubsearchapp.data.model.UserResponse
import com.fajar.githubsearchapp.di.ApiModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {

    val listFollowers = MutableLiveData<ArrayList<User>>()

    fun setListFollowers(user: String) {
        ApiModule.apiService
            .getUserFollowers(user)
            .enqueue(object : Callback<ArrayList<User>> {
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful) {
                        listFollowers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getListFollowers(): LiveData<ArrayList<User>> {
        return listFollowers
    }
}