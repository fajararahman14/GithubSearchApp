package com.fajar.githubsearchapp.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fajar.githubsearchapp.adapter.UserAdapter
import com.fajar.githubsearchapp.data.local.FavoriteUser
import com.fajar.githubsearchapp.data.model.User
import com.fajar.githubsearchapp.databinding.ActivityFavoriteBinding
import com.fajar.githubsearchapp.ui.detail.DetailUserActivity

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@FavoriteActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.username)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailUserActivity.EXTRA_AVATAR, data.avatar_url)
                    startActivity(it)
                }
            }
        })
        binding.apply {
            rvUser.adapter = adapter
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
        }
        viewModel.getFavoriteUser()?.observe(this) { user ->
            if (user != null) {
                val list = mapList(user)
                adapter.setList(list)
            }
        }

    }

    private fun mapList(user: List<FavoriteUser>): ArrayList<User> {
        val listUser = ArrayList<User>()
        for (users in user) {
            val userMapped = User(
                users.login,
                users.id,
                users.avatar_url,
            )
            listUser.add(userMapped)
        }
        return listUser
    }
}