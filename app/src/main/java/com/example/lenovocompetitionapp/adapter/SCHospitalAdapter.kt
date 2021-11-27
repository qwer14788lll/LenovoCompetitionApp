package com.example.lenovocompetitionapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lenovocompetitionapp.R
import com.example.lenovocompetitionapp.activities.SCDoctorActivity
import com.example.lenovocompetitionapp.model.SCHAOGroup

class SCHospitalAdapter(private val dataList: ArrayList<SCHAOGroup>): RecyclerView.Adapter<SCHospitalAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val id: TextView = view.findViewById(R.id.order_id)
        val scHospitalName: TextView = view.findViewById(R.id.schospital_name)
        val scAdministrativeName: TextView = view.findViewById(R.id.scadministrative_name)
        val scOutpatientName: TextView = view.findViewById(R.id.scoutpatient_name)
        val scOutpatientId: TextView = view.findViewById(R.id.toSCDoctor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.schospital_item, parent, false)
        val viewHolder = ViewHolder(view)
        viewHolder.scOutpatientId.setOnClickListener {
            //获取点击的项
            val data = dataList[viewHolder.adapterPosition]
            Log.i("Test","data.scHospitalId:${data.scHospitalId}")
            Log.i("Test","data.scAdministrativeId:${data.scAdministrativeId}")
            Log.i("Test","data.scOutpatientId:${data.scOutpatientId}")
            SCDoctorActivity.actionStart(parent.context, data.scHospitalId, data.scAdministrativeId, data.scOutpatientId)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.id.text = position.toString()
        holder.scHospitalName.text = data.scHospitalName
        holder.scAdministrativeName.text = data.scAdministrativeName
        holder.scOutpatientName.text = data.scOutpatientName
    }

    override fun getItemCount() = dataList.size
}