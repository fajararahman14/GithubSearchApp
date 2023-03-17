package com.fajar.githubsearchapp.ui.detail

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fajar.githubsearchapp.data.local.FavoriteUser
import com.fajar.githubsearchapp.data.local.FavoriteUserDao
import com.fajar.githubsearchapp.data.local.UserDatabase
import com.fajar.githubsearchapp.data.model.DetailUserResponse
import com.fajar.githubsearchapp.di.ApiModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {

    val user = MutableLiveData<DetailUserResponse>()

    private var userDao: FavoriteUserDao? = null
    private var userDatabase: UserDatabase? = null

    val errorMessage = MutableLiveData<String>()

    init {
        userDatabase = UserDatabase.getDatabase(application)
        userDao = userDatabase?.favoriteUserDao()
    }

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
                    errorMessage.postValue(t.message.toString())
                }

            })

    }

    fun getUserDetail(): LiveData<DetailUserResponse> {
        return user
    }

    fun insertFavoriteUser(username: String, id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteUser(
                username,
                id
            )
            userDao?.insertFavoriteUser(user)
        }
    }

    suspend fun checkUser(id: Int) = userDao?.isFavorite(id)

    fun removeFavoriteUser(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.deleteFavoriteUser(id)
        }
    }
}
