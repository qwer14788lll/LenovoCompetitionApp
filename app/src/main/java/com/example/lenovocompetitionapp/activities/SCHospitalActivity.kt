package com.example.lenovocompetitionapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lenovocompetitionapp.R
import com.example.lenovocompetitionapp.adapter.SCHospitalAdapter
import com.example.lenovocompetitionapp.databinding.ActivitySchospitalBinding
import com.example.lenovocompetitionapp.db.DBHelper
import com.example.lenovocompetitionapp.httpservice.SCHospitalService
import com.example.lenovocompetitionapp.model.SCAdministrativeData
import com.example.lenovocompetitionapp.model.SCHAOGroup
import com.example.lenovocompetitionapp.model.SCHospitalData
import com.example.lenovocompetitionapp.model.SCOutpatientData
import com.example.lenovocompetitionapp.util.ServiceCreator
import com.example.lenovocompetitionapp.util.ServiceCreator.test
import kotlinx.coroutines.*
import org.litepal.LitePal
import org.litepal.extension.findAll
import retrofit2.await
import retrofit2.create


class SCHospitalActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivitySchospitalBinding
    private lateinit var mSCHospitalDataList: List<SCHospitalData>
    private lateinit var mSCAdministrativeDataList: List<SCAdministrativeData>
    private lateinit var mSCOutpatientDataList: List<SCOutpatientData>
    private val scHospitalService = ServiceCreator.retrofit.create<SCHospitalService>()
    private val job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySchospitalBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mBinding.titleBar.mTitle.text = "预约挂号"
        initData()
        initSpinner()
        mBinding.buttonQuery.setOnClickListener {
            val scHospitalId = mBinding.spSchospital.selectedItemPosition
            val scAdministrativeName = mBinding.spScadministrative.selectedItem.toString()
            val scOutpatientName = mBinding.spScoutpatient.selectedItem.toString()
            val dataList =  query(scHospitalId,scAdministrativeName,scOutpatientName)
            mBinding.SCHospitalRecyclerView.layoutManager = LinearLayoutManager(this)
            mBinding.SCHospitalRecyclerView.adapter = SCHospitalAdapter(dataList)
        }
    }

    private fun isSCHospitalDataListInitialized() = this::mSCHospitalDataList.isInitialized
    private fun initSpinner() {
        Log.i("Test", "initSpinner")
        mBinding.spSchospital.onItemSelectedListener = object : OnItemSelectedListener {
            //当选中某一个数据项时触发该方法
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //System.out.println(spinner==parent);//true
                //System.out.println(view);
                //String data = adapter.getItem(position);//从适配器中获取被选择的数据项
                //String data = list.get(position);//从集合中获取被选择的数据项
                when (position) {
                    0 -> {
                        if (isSCHospitalDataListInitialized()) {
                            mBinding.spSchospital.adapter = getSpinnerAdapter(mSCHospitalDataList, "医院")
                        } else {
                            mBinding.spSchospital.adapter = getSpinnerAdapter(listOf<String>(), "医院")
                        }
                        val adapter = getSpinnerAdapter(listOf<String>(), "科室")
                        adapter.remove("error")
                        adapter.add("应先选择医院！")
                        mBinding.spScadministrative.adapter = adapter
                    }
                    else -> {
                        val list = LitePal.where("SCHospitalId = ?", position.toString())
                            .find(SCAdministrativeData::class.java)
                        mBinding.spScadministrative.adapter = getSpinnerAdapter(list, "科室")
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.i("Test", "onNothingSelected")
            }
        }
        mBinding.spScadministrative.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {
                        val adapter = getSpinnerAdapter(listOf<String>(), "门诊")
                        adapter.remove("error")
                        adapter.add("应先选择科室！")
                        mBinding.spScoutpatient.adapter = adapter
                    }
                    else -> {
                        val list = LitePal.where(
                            "SCHospitalId = ? and SCAdministrativeId in(select id from SCAdministrativeData where name = ?)",
                            mBinding.spSchospital.selectedItemPosition.toString(),
                            mBinding.spScadministrative.selectedItem.toString()
                        )
                        .find(SCOutpatientData::class.java)
                        mBinding.spScoutpatient.adapter = getSpinnerAdapter(list, "门诊")
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // TODO Auto-generated method stub
            }
        }
    }

    private fun query(scHospitalId: Int, scAdministrativeName: String, scOutpatientName: String): ArrayList<SCHAOGroup> {
        val list = ArrayList<SCHAOGroup>()
        var sql = "select SCHospitalData.id,SCHospitalData.name,SCAdministrativeData.id,SCAdministrativeData.name,SCOutpatientData.id,SCOutpatientData.name " +
                "from SCHospitalData,SCAdministrativeData,SCOutpatientData " +
                "where SCHospitalData.id = SCAdministrativeData.SCHospitalId and SCAdministrativeData.id = SCOutpatientData.SCAdministrativeId"
        if (scHospitalId != 0) {
            sql += " and SCHospitalData.id=$scHospitalId"
            if (scAdministrativeName != "请选择科室") {
                sql += " and SCAdministrativeData.name='$scAdministrativeName'"
                if (scOutpatientName != "请选择门诊") {
                    sql += " and SCOutpatientData.name='$scOutpatientName'"
                }
            }
        }
        val data = LitePal.findBySQL(sql)
        while (data.moveToNext()) {
            list.add(
                SCHAOGroup(
                    data.getInt(0),
                    data.getString(1),
                    data.getInt(2),
                    data.getString(3),
                    data.getInt(4),
                    data.getString(5)
                )
            )
        }
        return list
    }

    private fun initData() {
        val scope = CoroutineScope(job)
        scope.launch {
            mSCHospitalDataList = withContext(Dispatchers.IO) { getSCHospitalData() }
            Log.i("Test", "scope.launch1:" + mSCHospitalDataList.size .toString())
            DBHelper.toDBModel(mSCHospitalDataList)
            Log.i("Test", "scope.launch2:" + mSCHospitalDataList.size.toString())
        }
        scope.launch {
            mSCAdministrativeDataList = withContext(Dispatchers.IO) { getSCAdministrativeData() }
            DBHelper.toDBModel(mSCAdministrativeDataList)
        }
        scope.launch {
            mSCOutpatientDataList = withContext(Dispatchers.IO) { getSCOutpatientData() }
            DBHelper.toDBModel(mSCOutpatientDataList)
        }
        //job.cancel()
    }

    private fun <T> getSpinnerAdapter(data: List<T>, type: String): ArrayAdapter<String> {
        val list = mutableListOf<String>()
        list.add("请选择$type")
        for (item in data) {
            list.add(
                when (item) {
                    is SCHospitalData -> item.name
                    is SCAdministrativeData -> item.name
                    is SCOutpatientData -> item.name
                    else -> "error"
                }
            )
        }
        return ArrayAdapter(this, R.layout.spinner_style, R.id.spinner_text_name, list)
    }

    private suspend fun getSCHospitalData(): List<SCHospitalData> {
        val list = scHospitalService.getSCHospitalAll().await()
        if (list.status == 400) {
            withContext(Dispatchers.Main){
                Toast.makeText(applicationContext, list.message, Toast.LENGTH_SHORT).show()
            }
        }
        return list.data
    }

    private suspend fun getSCAdministrativeData(): List<SCAdministrativeData> {
        val list = scHospitalService.getSCAdministrativeAll().test()
        if (list.status == 400) {
            withContext(Dispatchers.Main){
                Toast.makeText(applicationContext, list.message, Toast.LENGTH_SHORT).show()
            }
        }
        return list.data
    }

    private suspend fun getSCOutpatientData(): List<SCOutpatientData> {
        val list = scHospitalService.getSCOutpatientAll().test()
        if (list.status == 400) {
            withContext(Dispatchers.Main){
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