package com.vegasega.myapplication.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vegasega.myapplication.R
import com.vegasega.myapplication.databinding.ItemLoaderBinding


class LoaderAdapter : LoadStateAdapter<LoaderAdapter.LoaderViewHolder>() {

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLoaderBinding.inflate(inflater, parent, false)
        return LoaderViewHolder(binding)
    }

    class LoaderViewHolder(private val binding: ItemLoaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) {
            binding.executePendingBindings()
            binding.progressBar.isVisible = loadState is LoadState.Loading
        }
    }
}