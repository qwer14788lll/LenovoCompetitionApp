package com.example.lenovocompetitionapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.lenovocompetitionapp.R
import com.example.lenovocompetitionapp.model.SCVisitData
import com.example.lenovocompetitionapp.util.StringHelper

class SCVisitAdapter(private val dataList: MutableList<SCVisitData>) : RecyclerView.Adapter<SCVisitAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dateTime: TextView = view.findViewById(R.id.date_time)
        val reservationId: TextView = view.findViewById(R.id.reservation_id)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.scvisit_item, parent, false)
        val viewHolder = ViewHolder(view)
        viewHolder.reservationId.setOnClickListener {
            Toast.makeText(parent.context,"预约成功",Toast.LENGTH_LONG).show()
            viewHolder.reservationId.text = "已预约"
            viewHolder.reservationId.setTextColor(parent.context.resources.getColor(R.color.black,parent.context.theme))
            viewHolder.reservationId.isEnabled = false
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        val str = StringHelper.timeStamp2Date(data.treatmentDate.toString()) +" "+ if (data.timeType == 0) "上午" else "下午"
        holder.dateTime.text = str
    }

    override fun getItemCount() = dataList.size
}