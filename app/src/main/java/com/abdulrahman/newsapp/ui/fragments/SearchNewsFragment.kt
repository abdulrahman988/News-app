package com.abdulrahman.newsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdulrahman.R
import com.abdulrahman.databinding.FragmentSearchNewsBinding
import com.abdulrahman.newsapp.ui.NewsActivity
import com.abdulrahman.newsapp.utils.Constants
import com.abdulrahman.newsapp.viewmodels.NewsViewModel
import com.abdulrahman.newsapp.adapters.PagingAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment() {

    private lateinit var binding: FragmentSearchNewsBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var adapter: PagingAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchNewsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel


        var job: Job? = null
        binding.etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(Constants.SEARCH_NEWS_TIME_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        collectUiState(it.toString())
                    }
                }

            }
        }

        initView()
    }

    private fun collectUiState(q: String) {
        lifecycleScope.launch {
            viewModel.searchAllNews(q).observe(viewLifecycleOwner) {
                it?.let {
                    adapter.submitData(lifecycle, it)
                }
            }
        }

        adapter.addLoadStateListener { loadStates ->
            if (loadStates.refresh is LoadState.Loading || loadStates.append is LoadState.Loading) {
                showProgressBar()
            } else {
                hideProgressBar()
            }
            val errorState = when {
                loadStates.append is LoadState.Error -> loadStates.append as LoadState.Error
                loadStates.prepend is LoadState.Error -> loadStates.prepend as LoadState.Error
                loadStates.refresh is LoadState.Error -> loadStates.refresh as LoadState.Error
                else -> null
            }
            errorState?.let {
                Toast.makeText(this.activity, it.error.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun initView() {
        adapter = PagingAdapter(PagingAdapter.OnClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(R.id.action_searchNewsFragment_to_articleFragment, bundle)
        })
        binding.rvSearchNews.adapter = adapter
        binding.rvSearchNews.layoutManager = LinearLayoutManager(activity)
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }
}