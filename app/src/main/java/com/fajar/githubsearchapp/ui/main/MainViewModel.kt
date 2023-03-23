package com.fajar.githubsearchapp.ui.main


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

class MainViewModel : ViewModel() {

    val listUsers = MutableLiveData<ArrayList<User>>()

    val errorMessage = MutableLiveData<String>()

    fun setSearchUsers(query: String) {
        ApiModule.apiService
            .getSearchUsers(query)
            .enqueue(
                object : Callback<UserResponse> {
                    override fun onResponse(
                        call: Call<UserResponse>,
                        response: Response<UserResponse>
                    ) {
                        if (response.isSuccessful) {
                            listUsers.postValue(response.body()?.items)
                        }
                    }

                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        Log.d("Failure", t.message.toString())
                        errorMessage.postValue(t.message.toString())
                    }
                }
            )
    }

    fun getSearchUsers(): LiveData<ArrayList<User>> {
        return listUsers
    }

}