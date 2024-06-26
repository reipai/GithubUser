package com.reivai.githubusersearch.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import androidx.recyclerview.widget.LinearLayoutManager
import com.reivai.githubusersearch.Helper
import com.reivai.githubusersearch.R
import com.reivai.githubusersearch.databinding.ActivityMainBinding
import com.reivai.githubusersearch.network.model.User
import com.reivai.githubusersearch.ui.adapter.UserAdapter
import com.reivai.githubusersearch.ui.detailuser.DetailUserActivity
import com.reivai.githubusersearch.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClick(data: User) {
                Intent(this@MainActivity, DetailUserActivity::class.java).also {
                    it.putExtra(Helper.USERNAME, data.login)
                    it.putExtra(Helper.ID, data.id)
                    it.putExtra(Helper.AVATAR_URL, data.avatar_url)
                    startActivity(it)
                }
            }

        })

        binding.rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.rvUser.setHasFixedSize(true)
        binding.rvUser.adapter = adapter

        binding.btnSearch.setOnClickListener {
            searchUser()
        }

        binding.etSearch.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                searchUser()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        viewModel.getSearch().observe(this, {
            if (it != null) {
                adapter.setList(it)
                binding.refreshLayout.isRefreshing = false
            }
        })
    }

    private fun searchUser() {
        val query = binding.etSearch.text.toString()
        if (query.isEmpty()) return
        binding.refreshLayout.isRefreshing = true
        viewModel.setSearch(query)
    }
}