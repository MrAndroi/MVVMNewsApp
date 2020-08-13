package com.androiddevs.mvvmnewsapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.ui.adapters.NewsAdapter
import com.androiddevs.mvvmnewsapp.ui.viewModels.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_saved_news.*

@AndroidEntryPoint
class SavedNewsFragment:Fragment(R.layout.fragment_saved_news) {

    val viewModel:NewsViewModel by viewModels()
    lateinit var adapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = NewsAdapter()

        adapter.onItemClick {
            val bundle = Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(R.id.action_savedNewsFragment_to_articleFragment,bundle)
        }


        val callback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = adapter.differ.currentList[position]
                viewModel.deleteNews(article)
                Snackbar.make(view,"Article deleted",Snackbar.LENGTH_SHORT).apply {
                    setAction("Undo"){
                        viewModel.insertNews(article)
                    }
                }.show()
            }

        }

        ItemTouchHelper(callback).apply {
            attachToRecyclerView(rvSavedNews)
        }

        viewModel.getAllNews().observe(viewLifecycleOwner, Observer {
            adapter.differ.submitList(it)
        })
        rvSavedNews.adapter = adapter
    }
}