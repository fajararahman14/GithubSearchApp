package com.fajar.githubsearchapp.ui.detail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.fajar.githubsearchapp.databinding.ActivityDetailUserBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {


    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var user = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, user)


        viewModel = ViewModelProvider(this).get(DetailUserViewModel::class.java)

        viewModel.setUserDetail(user.toString())
        viewModel.getUserDetail()
            .observe(this) {
                if (it !== null) {
                    binding.apply {
                        tvName.text = it.name
                        tvUsername.text = it.username
                        tvFollowing.text = "${it.following} Following"
                        tvFollowers.text = "${it.followers} Followers"
                        Glide.with(this@DetailUserActivity)
                            .load(it.avatar_url)
                            .transition(
                                DrawableTransitionOptions.withCrossFade()
                            )
                            .centerCrop()
                            .into(ivProfile)
                    }
                }
            }
        viewModel.errorMessage.observe(this) { message ->
            if (message != null) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0) {
                        binding.toggleFavorite.isChecked = true
                        _isChecked = true
                    } else {
                        binding.toggleFavorite.isChecked = false
                        _isChecked = false
                    }
                } else {
                    Toast.makeText(this@DetailUserActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.toggleFavorite.setOnClickListener {
            _isChecked = !_isChecked
            if (_isChecked) {
                viewModel.insertFavoriteUser(user.toString(), id)
                Toast.makeText(this, "Added to Favorite", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.removeFavoriteUser(id)
                Toast.makeText(this, "Removed from Favorite", Toast.LENGTH_SHORT).show()
            }
        }

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabLayout.setupWithViewPager(viewPager)
        }
    }


    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
    }


}