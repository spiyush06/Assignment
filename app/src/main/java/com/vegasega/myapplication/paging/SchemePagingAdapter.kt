package com.vegasega.myapplication.paging

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.vegasega.myapplication.R
import com.vegasega.myapplication.data.model.Data
import com.vegasega.myapplication.databinding.RowListLiveSchemeBinding
import com.vegasega.myapplication.utils.Utility.Companion.dateformat

class SchemePagingAdapter :
    PagingDataAdapter<Data, SchemePagingAdapter.QuoteViewHolder>(COMPARATOR) {

    class QuoteViewHolder(private val binding: RowListLiveSchemeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Data) {
            binding.executePendingBindings()
            binding.txvName.text = item.name
            binding.txvDescription.text = item.description
            binding.txvEndAt.text = "Valid Date " + dateformat(item.end_at)
            binding.txvUserSchemeStatus.text = "Status " + item.user_scheme_status
            binding.txvStatus.text = item.status

            if (item.status.equals("Active")) {
                binding.linearlayoutStatus.setBackgroundColor(Color.parseColor("#008000"))
            } else {
                binding.linearlayoutStatus.setBackgroundColor(Color.parseColor("#ff0000"))
            }

            Glide.with(binding.imgScheme)
                .setDefaultRequestOptions(
                    RequestOptions()
                        .circleCrop().error(R.drawable.scheme)
                )
                .load(item.scheme_image.url)
                .into(binding.imgScheme)
        }

    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowListLiveSchemeBinding.inflate(inflater, parent, false)
        return QuoteViewHolder(binding)
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Data>() {
            override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem.scheme_id == newItem.scheme_id
            }

            override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem == newItem
            }
        }
    }
}