package com.goldouble.android.github.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.goldouble.android.github.databinding.FragmentSearchBinding
import com.goldouble.android.github.view.adapter.ProfileAdapter
import com.goldouble.android.github.viewmodel.SearchViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*

@ExperimentalCoroutinesApi
class SearchFragment : Fragment() {
    private val disposable = CompositeDisposable()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<SearchViewModel>()

    // region lifecycle
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        setView()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    // endregion

    // region setView
    private fun setView() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setButton()
        setRefreshLayout()
    }

    private fun setButton() {
        binding.btnSearch.setOnClickListener {
            setAdapter()
        }
    }

    private fun setRefreshLayout() {
        binding.srlProfileSearch.setOnRefreshListener {
            setAdapter()
            binding.srlProfileSearch.isRefreshing = false
        }
    }

    private fun setAdapter() {
        val adapter = ProfileAdapter {
            findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToProfileFragment())
        }
        binding.rvProfileSearch.adapter = adapter

        disposable.add(
            viewModel.flow.subscribe { pagingData ->
                adapter.submitData(lifecycle, pagingData)
            }
        )
    }
    // endregion
}