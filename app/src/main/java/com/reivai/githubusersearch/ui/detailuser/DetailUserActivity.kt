package com.reivai.githubusersearch.ui.detailuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.reivai.githubusersearch.Helper
import com.reivai.githubusersearch.viewmodel.DetailViewModel
import com.reivai.githubusersearch.R
import com.reivai.githubusersearch.databinding.ActivityDetailUserBinding
import com.reivai.githubusersearch.ui.adapter.SectionPagerAdapter

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setTitle("Detail User Github")
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        val username = intent.getStringExtra(Helper.USERNAME)
        val id = intent.getIntExtra(Helper.ID, 0)
        val avatarUrl = intent.getStringExtra(Helper.AVATAR_URL)
        val bundle = Bundle()
        bundle.putString(Helper.USERNAME, username)

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)).get(DetailViewModel::class.java)

        Log.d(Helper.TAG, "username: $username")
        if (username != null) {
            viewModel.setDetailUser(username)
        }

        viewModel.getDetailUser().observe(this, {
            if (it != null) {
                binding.tvName.text = it.name
                binding.tvUsername.text = it.login
                binding.tvFollowers.text = "${it.followers} Followers"
                binding.tvFollowing.text = "${it.following} Following"
                Glide.with(this@DetailUserActivity)
                    .load(it.avatar_url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(binding.ivDetailPict)
                binding.tvCompany.text = it.company
                binding.tvLocation.text = it.location
            }
        })

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.viewPager.adapter = sectionPagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}