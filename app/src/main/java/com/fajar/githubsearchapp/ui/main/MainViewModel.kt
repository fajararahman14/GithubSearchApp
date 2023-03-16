package com.fajar.githubsearchapp.ui.main


import android.widget.Toast
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
                        Toast.makeText(null, "Failed to load data", Toast.LENGTH_SHORT).show()
                    }
                }
            )
    }

    fun getSearchUsers(): LiveData<ArrayList<User>> {
        return listUsers
    }

}