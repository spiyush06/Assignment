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
import com.vegasega.myapplication.utils.Utility.Companion.boldText
import com.vegasega.myapplication.utils.Utility.Companion.boldTextDate
import com.vegasega.myapplication.utils.Utility.Companion.dateformat
import com.vegasega.myapplication.utils.Utility.Companion.translateToHindi

class SchemePagingAdapter(isHindi: Boolean) : PagingDataAdapter<Data, SchemePagingAdapter.QuoteViewHolder>(COMPARATOR) {

    private var isHindi: Boolean = isHindi

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item, isHindi)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowListLiveSchemeBinding.inflate(inflater, parent, false)
        return QuoteViewHolder(binding)
    }

    class QuoteViewHolder(private val binding: RowListLiveSchemeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Data, isHindi: Boolean) {
            binding.executePendingBindings()
            binding.txvName.text = item.name
            binding.txvDescription.text = item.description
            val strUserSchemeStatus = boldText("Status ${item.user_scheme_status.capitalize()}")
            val strEndAt = boldTextDate("Valid Date ${dateformat(item.end_at)}")
            binding.txvUserSchemeStatus.text=strUserSchemeStatus

            binding.txvStatus.text = item.status
            binding.txvEndAt.text = strEndAt

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

            if (isHindi) {

                item.name.translateToHindi(onSuccess = {
                    binding.txvName.text=it
                }, onFailure = {})

                item.description.translateToHindi(onSuccess = {
                    binding.txvDescription.text=it
                }, onFailure = {})

                item.status.translateToHindi(onSuccess = {
                    binding.txvStatus.text=it
                }, onFailure = {})

                item.user_scheme_status.translateToHindi(onSuccess = {
                val spannable = boldText("स्थिति $it")
                    binding.txvUserSchemeStatus.text=spannable
                }, onFailure = {})

                item.end_at.translateToHindi(onSuccess = {
                    val spannable = boldTextDate("मान्य दिनांक ${dateformat(item.end_at)}")
                    binding.txvEndAt.text=spannable
                }, onFailure = {})

            }
        }
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

    fun fetchData(isHindi: Boolean) {
        this.isHindi = isHindi
        notifyDataSetChanged()
    }

}