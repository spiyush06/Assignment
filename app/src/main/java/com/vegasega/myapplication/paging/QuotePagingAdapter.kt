package com.cheezycode.quickpagingdemo.paging

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.vegasega.myapplication.R
import com.vegasega.myapplication.data.model.Data
import com.vegasega.myapplication.utils.Utility.Companion.dateformat

class QuotePagingAdapter :
    PagingDataAdapter<Data, QuotePagingAdapter.QuoteViewHolder>(COMPARATOR) {

    class QuoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.txv_name)
        val description = itemView.findViewById<TextView>(R.id.txv_description)
        val end = itemView.findViewById<TextView>(R.id.txv_end_at)
        val userStatus = itemView.findViewById<TextView>(R.id.txv_user_scheme_status)
        val status = itemView.findViewById<TextView>(R.id.txv_status)
        val image = itemView.findViewById<ImageView>(R.id.img_scheme_image)
        val linearLayout = itemView.findViewById<LinearLayout>(R.id.linearlayout_status)
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.name.text = item.name
            holder.description.text = item.description
            holder.end.text = "Valid Date " + dateformat(item.end_at)
            holder.userStatus.text = "Status "+item.user_scheme_status
            holder.status.text = item.status

            if(holder.status.equals("Active")) {
                holder.linearLayout.setBackgroundColor(Color.parseColor("#ff0000"))
            } else {
                holder.linearLayout.setBackgroundColor(Color.parseColor("#008000"))
            }

            Glide.with(holder.image)
                .setDefaultRequestOptions(
                    RequestOptions()
                        .circleCrop().error(R.drawable.scheme)
                )
                .load(item.scheme_image.url)
                .into(holder.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_list_live_scheme, parent, false)
        return QuoteViewHolder(view)
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





















