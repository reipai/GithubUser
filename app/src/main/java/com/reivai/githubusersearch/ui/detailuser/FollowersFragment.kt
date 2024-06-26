package com.reivai.githubusersearch.ui.detailuser

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.reivai.githubusersearch.Helper
import com.reivai.githubusersearch.R
import com.reivai.githubusersearch.databinding.FragmentFollowBinding
import com.reivai.githubusersearch.ui.adapter.UserAdapter
import com.reivai.githubusersearch.viewmodel.FollowersViewModel

class FollowersFragment: Fragment(R.layout.fragment_follow) {

    private var _binding : FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowersViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        username = args?.getString(Helper.USERNAME).toString()
        _binding = FragmentFollowBinding.bind(view)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        binding.rvUser.setHasFixedSize(true)
        binding.rvUser.layoutManager = LinearLayoutManager(activity)
        binding.rvUser.adapter = adapter

        binding.refreshLayout.isRefreshing = true
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel::class.java)
        viewModel.setListFollowers(username)
        viewModel.getListFollowers().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setList(it)
                binding.refreshLayout.isRefreshing = false
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}