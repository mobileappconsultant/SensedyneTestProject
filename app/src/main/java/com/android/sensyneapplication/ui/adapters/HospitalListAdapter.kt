package com.android.sensyneapplication.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.sensyneapplication.R
import com.android.sensyneapplication.SensyneApplication
import com.android.sensyneapplication.framework.domain.model.HospitalItem
import kotlinx.android.synthetic.main.view_hospital_item.view.*

class HospitalListAdapter(
    val adapterOnClick: (HospitalItem) -> Unit
) :
    RecyclerView.Adapter<HospitalListAdapter.HospitalItemViewHolder>() {

    private var data = mutableListOf<HospitalItem?>()

    fun updateData(newData: List<HospitalItem?>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    inner class HospitalItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(hospitalItem: HospitalItem?) {

            with(hospitalItem) {
                itemView.primary_text.text = this?.ParentName
                itemView.sub_text.text = this?.OrganisationName
                itemView.supporting_text.text = this?.buildAddress
            }

            itemView.setOnClickListener {
                hospitalItem?.let {
                    //    adapterOnClick.invoke(it)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.view_hospital_item,
            parent,
            false
        )
        return HospitalItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HospitalItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

    val HospitalItem.buildAddress: String
        get() {
            return Address1.plus(" ").plus(Address2).plus(" ").plus(Address3).plus(",")
                .plus(County).plus(" ").plus(City)
                .plus(" ").plus(Postcode)
        }
}
