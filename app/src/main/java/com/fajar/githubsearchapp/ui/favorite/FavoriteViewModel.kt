package com.fajar.githubsearchapp.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fajar.githubsearchapp.data.local.FavoriteUser
import com.fajar.githubsearchapp.data.local.FavoriteUserDao
import com.fajar.githubsearchapp.data.local.UserDatabase
import com.fajar.githubsearchapp.data.model.DetailUserResponse

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    val user = MutableLiveData<DetailUserResponse>()

    private var userDao: FavoriteUserDao? = null
    private var userDatabase: UserDatabase? = null

    val errorMessage = MutableLiveData<String>()

    init {
        userDatabase = UserDatabase.getDatabase(application)
        userDao = userDatabase?.favoriteUserDao()
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>>? {
        return userDao?.getFavoriteUser()
    }

}