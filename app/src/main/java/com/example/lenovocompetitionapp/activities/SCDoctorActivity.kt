package com.example.lenovocompetitionapp.activities

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lenovocompetitionapp.adapter.SCDoctorAdapter
import com.example.lenovocompetitionapp.databinding.ActivityScdoctorBinding
import com.example.lenovocompetitionapp.db.DBHelper
import com.example.lenovocompetitionapp.httpservice.SCHospitalService
import com.example.lenovocompetitionapp.model.SCDVGroup
import com.example.lenovocompetitionapp.model.SCDoctorData
import com.example.lenovocompetitionapp.model.SCVisitData
import com.example.lenovocompetitionapp.util.ServiceCreator
import com.example.lenovocompetitionapp.util.ServiceCreator.test
import kotlinx.coroutines.*
import org.litepal.LitePal
import retrofit2.await
import retrofit2.create

class SCDoctorActivity : AppCompatActivity() {

    companion object {
        private const val scHospitalId = "scHospitalId"
        private const val scAdministrativeId = "scAdministrativeId"
        private const val scOutpatientId = "scOutpatientId"
        fun actionStart(context: Context, hospitalId: Int, administrativeId: Int, outpatientId: Int) {
            val intent = Intent(context, SCDoctorActivity::class.java)
            intent.putExtra(scHospitalId, hospitalId)
            intent.putExtra(scAdministrativeId, administrativeId)
            intent.putExtra(scOutpatientId, outpatientId)
            context.startActivity(intent)
        }
    }

    private lateinit var mBinding: ActivityScdoctorBinding
    private val scHospitalService = ServiceCreator.retrofit.create<SCHospitalService>()
    private val job = Job()
    private lateinit var dataList: ArrayList<SCDVGroup>
    private lateinit var progressDialog:ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityScdoctorBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mBinding.titleBar.mTitle.text = "推荐医生"
        Log.i("Test","11111111")
        val intent = intent
        val hospitalId = intent.getIntExtra(scHospitalId,0)
        val administrativeId = intent.getIntExtra(scAdministrativeId,0)
        val outpatientId = intent.getIntExtra(scOutpatientId,0)

        progressDialog = ProgressDialog(this).apply {
            setTitle("请稍后")
            setMessage("正在帮您查找不错的医生")
            setProgressStyle(ProgressDialog.STYLE_SPINNER) //转圈的进度条
        }
        progressDialog.show()

        initData(hospitalId,administrativeId,outpatientId)
        mBinding.SCDoctorRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun initData(hospitalId: Int, administrativeId: Int, outpatientId: Int) {
        val scope = CoroutineScope(job)
        val doctorJob = scope.launch {
            val doctorList = withContext(Dispatchers.IO) { getSCDoctorSearch(hospitalId, administrativeId, outpatientId) }
            DBHelper.toDBModel(doctorList)
            Log.i("Test","222222")
        }
        val visitJob = scope.launch {
            val visitList = withContext(Dispatchers.IO) { getSCVisitSearch(hospitalId, administrativeId, outpatientId) }
            DBHelper.toDBModel(visitList)
            Log.i("Test","33333333")
        }
        val dateJob = scope.launch {
            while (!(doctorJob.isCompleted && visitJob.isCompleted)) delay(200)
            dataList = getSCDVGroup(hospitalId, administrativeId, outpatientId)
            Log.i("Test","4444444")
        }
        val okJob = scope.launch {
            while (!dateJob.isCompleted) delay(400)
            Log.i("Test","555555555")
            withContext(Dispatchers.Main) { mBinding.SCDoctorRecyclerView.adapter = SCDoctorAdapter(dataList) }
        }
        scope.launch {
            while (!okJob.isCompleted) delay(600)
            Log.i("Test","6666666")
            progressDialog.cancel()
        }
    }

    private fun getSCDVGroup(scHospitalId: Int, scAdministrativeId: Int, scOutpatientId: Int): ArrayList<SCDVGroup> {
        val list = ArrayList<SCDVGroup>()
        val sql = "select distinct name,adept,SCDoctorData.id " +
                "from SCDoctorData,SCVisitData " +
                "where SCDoctorData.id = SCVisitData.SCDoctorId " +
                "and SCDoctorData.SCHospitalId = SCVisitData.SCHospitalId " +
                "and SCDoctorData.SCAdministrativeId = SCVisitData.SCAdministrativeId " +
                "and SCDoctorData.SCOutpatientId = SCVisitData.SCOutpatientId " +
                "and SCDoctorData.SCHospitalId = $scHospitalId " +
                "and SCDoctorData.SCAdministrativeId = $scAdministrativeId " +
                "and SCDoctorData.SCOutpatientId = $scOutpatientId"
        val data = LitePal.findBySQL(sql)
        while (data.moveToNext()) {
            list.add(
                SCDVGroup(
                    data.getString(0),
                    data.getString(1),
                    scHospitalId,scAdministrativeId,scOutpatientId,data.getInt(2)
                )
            )
        }
        return list
    }

    private suspend fun getSCDoctorSearch(hospitalId: Int, administrativeId: Int, outpatientId: Int): List<SCDoctorData> {
        val list = scHospitalService.getSCDoctorSearch(
            hId = hospitalId,
            aId = administrativeId,
            oId = outpatientId
        ).await()
        if (list.status == 400) {
            withContext(Dispatchers.Main) {
                Toast.makeText(applicationContext, list.message, Toast.LENGTH_SHORT).show()
            }
        }
        return list.data
    }

    private suspend fun getSCVisitSearch(hospitalId: Int, administrativeId: Int, outpatientId: Int): List<SCVisitData> {
        val list = scHospitalService.getSCVisitSearch(
            hId = hospitalId,
            aId = administrativeId,
            oId = outpatientId,
        ).test()
        if (list.status == 400) {
            withContext(Dispatchers.Main) {
                Toast.makeText(applicationContext, list.message, Toast.LENGTH_SHORT).show()
            }
        }
        return list.data
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}