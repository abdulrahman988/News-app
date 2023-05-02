package com.abdulrahman.newsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdulrahman.R
import com.abdulrahman.databinding.FragmentSavedNewsBinding
import com.abdulrahman.newsapp.adapters.NewsRecyclerViewAdapter
import com.abdulrahman.newsapp.ui.NewsActivity
import com.abdulrahman.newsapp.viewmodels.NewsViewModel
import com.google.android.material.snackbar.Snackbar


class SavedNewsFragment : Fragment() {

    private lateinit var binding: FragmentSavedNewsBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsRecyclerViewAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedNewsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as NewsActivity).viewModel

        newsAdapter = NewsRecyclerViewAdapter(NewsRecyclerViewAdapter.OnClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(R.id.action_savedNewsFragment_to_articleFragment,
                bundle
            )
        })

        setupRecyclerView()
        swipeToDelete()
    }


    private fun setupRecyclerView() {
        binding.rvSavedNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        viewModel.getSavedNews().observe(viewLifecycleOwner, Observer { articles ->
            newsAdapter.submitList(articles)
        })


    }


    private fun swipeToDelete() {
        val itemTouchHelper =
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.ACTION_STATE_IDLE,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val deletedArticle = newsAdapter.items[position]

                    viewModel.deleteArticle(deletedArticle)

                    Snackbar.make(binding.rvSavedNews, "Deleted Successfully", Snackbar.LENGTH_LONG)
                        .apply {
                            setAction(
                                "Undo"
                            ) { viewModel.saveArticle(deletedArticle) }
                        }.show()
                }
            })
        itemTouchHelper.attachToRecyclerView(binding.rvSavedNews)
    }
}

