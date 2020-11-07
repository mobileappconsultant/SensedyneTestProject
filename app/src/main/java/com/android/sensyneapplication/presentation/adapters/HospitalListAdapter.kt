package com.android.sensyneapplication.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.sensyneapplication.R
import com.android.sensyneapplication.framework.domain.model.HospitalItem
import com.jakewharton.rxbinding4.view.clicks
import kotlinx.android.synthetic.main.view_hospital_item.view.*

class HospitalListAdapter(
    val adapterOnClick: (ClickActions) -> Unit
) :
    RecyclerView.Adapter<HospitalListAdapter.HospitalItemViewHolder>() {

    private var data = mutableListOf<HospitalItem?>()
    private lateinit var currentActions: ClickActions
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

                itemView.clicks()
                    .subscribe { adapterOnClick.invoke(ClickActions.MainAction(hospitalItem)) }
                itemView.phone_action.clicks()
                    .subscribe { adapterOnClick.invoke(ClickActions.PhoneAction(hospitalItem?.Phone)) }
                itemView.email_action.clicks()
                    .subscribe { adapterOnClick.invoke(ClickActions.EmailAction(hospitalItem?.Email)) }
                itemView.location_action.clicks().subscribe {
                    adapterOnClick.invoke(
                        ClickActions.LocationAction(
                            hospitalItem?.Latitude,
                            hospitalItem?.Longitude
                        )
                    )
                }
                itemView.web_action.clicks()
                    .subscribe { adapterOnClick.invoke(ClickActions.WebAction(hospitalItem?.Website)) }
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
