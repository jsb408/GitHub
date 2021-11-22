package com.goldouble.android.github.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.goldouble.android.github.databinding.FragmentProfileBinding
import com.goldouble.android.github.view.adapter.ProfileAdapter
import com.goldouble.android.github.viewmodel.ProfileViewModel
import com.goldouble.android.github.viewmodel.factory.ProfileViewModelFactory
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class ProfileFragment : Fragment() {
    private val disposable = CompositeDisposable()

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val arguments by navArgs<ProfileFragmentArgs>()
    private val viewModel by viewModels<ProfileViewModel> { ProfileViewModelFactory(arguments.username) }

    private lateinit var profileAdapter: ProfileAdapter

    // region lifecycle
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        bindViewModel()
        setView()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    // endregion

    // region setView
    private fun bindViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (!it) binding.srlProfile.isRefreshing = false
        }

        viewModel.userInfo.observe(viewLifecycleOwner) {
            profileAdapter.setUserData(it)
        }

        viewModel.repositoryList.observe(viewLifecycleOwner) {
            profileAdapter.setRepositoryList(it)
        }
    }

    private fun setView() {
        setActionBar()
        setRecyclerView()
        setSwipeRefreshLayout()
    }

    private fun setActionBar() {
        (activity as? AppCompatActivity)?.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = arguments.username
        }
    }

    private fun setRecyclerView() {
        profileAdapter = ProfileAdapter()
        binding.rvProfile.adapter = profileAdapter
        loadData()

        disposable.add(
            viewModel.flowable.subscribe { pagingData ->
                viewModel.stopLoading()
                profileAdapter.submitEventData(lifecycle, pagingData)
            }
        )
    }

    private fun setSwipeRefreshLayout() {
        binding.srlProfile.setOnRefreshListener {
            viewModel.startLoading()
            loadData()
            profileAdapter.refresh()
        }
    }

    private fun loadData() {
        viewModel.loadUserInfo()
        viewModel.loadRepositories()
    }
    // endregion
}