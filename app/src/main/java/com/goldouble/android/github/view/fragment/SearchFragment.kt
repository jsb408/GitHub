package com.goldouble.android.github.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.goldouble.android.github.R
import com.goldouble.android.github.databinding.FragmentSearchBinding
import com.goldouble.android.github.view.adapter.SearchResultAdapter
import com.goldouble.android.github.viewmodel.SearchViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class SearchFragment : Fragment() {
    private val disposable = CompositeDisposable()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<SearchViewModel>()

    private lateinit var searchResultAdapter: SearchResultAdapter

    // region lifecycle
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        bindViewModel()
        setView()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        disposable.clear()
    }
    // endregion

    private fun bindViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (!it) binding.srlProfileSearch.isRefreshing = false
        }

        viewModel.searchKeyword.observe(viewLifecycleOwner) {
            setAdapter()
        }
    }

    // region setView
    private fun setView() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setActionBar()
        setRefreshLayout()
    }

    private fun setActionBar() {
        (activity as? AppCompatActivity)?.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
            setTitle(R.string.app_name)
        }
    }

    private fun setRefreshLayout() {
        binding.srlProfileSearch.setOnRefreshListener {
            searchResultAdapter.refresh()
        }
    }

    private fun setAdapter() {
        searchResultAdapter = SearchResultAdapter {
            findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToProfileFragment(it))
        }
        binding.rvProfileSearch.adapter = searchResultAdapter
        searchResultAdapter.addLoadStateListener {
            if (it.refresh is LoadState.NotLoading) {
                viewModel.stopLoading()
                viewModel.setResult(searchResultAdapter.itemCount == 0)
            }
        }

        disposable.add(
            viewModel.flowable.subscribe { pagingData ->
                searchResultAdapter.submitData(lifecycle, pagingData)
            }
        )
    }
    // endregion
}