package com.fajar.githubsearchapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.fajar.githubsearchapp.databinding.ActivityDetailUserBinding

class DetailUserActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var user = intent.getStringExtra(EXTRA_USERNAME)

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, user)


        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailUserViewModel::class.java)

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
        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabLayout.setupWithViewPager(viewPager)
        }
    }

}