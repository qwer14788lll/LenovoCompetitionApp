package com.example.lenovocompetitionapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lenovocompetitionapp.R
import com.example.lenovocompetitionapp.model.SCDVGroup
import com.example.lenovocompetitionapp.model.SCVisitData
import org.litepal.LitePal

class SCDoctorAdapter(private val dataList: ArrayList<SCDVGroup>) :
    RecyclerView.Adapter<SCDoctorAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.doctor_name)
        val adept: TextView = view.findViewById(R.id.doctor_adept)
        val scVisitRecyclerView: RecyclerView = view.findViewById(R.id.scvisitRecyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.scdoctor_item, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.name.text = data.name
        holder.adept.text = data.adept
        val list = LitePal.select("treatmentDate", "timeType")
            .where("SCHospitalId = ? " +
                        "and SCAdministrativeId = ? " +
                        "and SCOutpatientId = ? " +
                        "and SCDoctorId = ?",
                data.scHospitalId.toString(),
                data.scAdministrativeId.toString(),
                data.scOutpatientId.toString(),
                data.scDoctorDataId.toString()
            )
            .find(SCVisitData::class.java)
        holder.scVisitRecyclerView.layoutManager = LinearLayoutManager(holder.itemView.context)
        holder.scVisitRecyclerView.adapter = SCVisitAdapter(list)
    }

    override fun getItemCount() = dataList.size
}