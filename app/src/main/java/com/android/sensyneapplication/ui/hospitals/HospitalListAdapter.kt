package com.android.sensyneapplication.ui.hospitals

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.sensyneapplication.R
import com.android.sensyneapplication.framework.domain.model.HospitalItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.line_item.view.*

class HospitalListAdapter(
    val adapterOnClick: (Any) -> Unit
) :
    RecyclerView.Adapter<HospitalListAdapter.HospitalItemViewHolder>() {

    private var data = mutableListOf<HospitalItem?>()

    val requestOptions: RequestOptions by lazy {
        RequestOptions()
            .error(R.drawable.no_internet)
            .placeholder(R.drawable.ic_movie_placeholder)
    }

    fun updateData(newData: List<HospitalItem?>) {
        // data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    inner class HospitalItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(movie: HospitalItem?) {
            // itemView.titleTextView.text = movie?.title
            // itemView.overviewTextView.text = movie?.overview

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(""/*"$POSTER_IMAGE_PATH_PREFIX${movie?.posterPath}"*/)
                .into(itemView.posterImageView)

            itemView.setOnClickListener {
                movie?.let {
                    adapterOnClick.invoke(it)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.line_item,
            parent,
            false
        )

        return HospitalItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HospitalItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

    companion object {
        const val POSTER_IMAGE_PATH_PREFIX = "https://image.tmdb.org/t/p/w300"
    }
}
