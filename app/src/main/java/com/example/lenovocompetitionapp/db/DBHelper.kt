package com.example.lenovocompetitionapp.db

import com.example.lenovocompetitionapp.model.*
import org.litepal.LitePal
import org.litepal.LitePal.getDatabase
import org.litepal.crud.LitePalSupport


object DBHelper {

    var db = getDatabase()

    /**
     * 将列表数据对象存入数据库
     */
    inline fun <reified T> toDBModel(list: List<T>) where T : LitePalSupport {
        //LitePal.deleteAll<T>()
        var id = 0
        list.forEach {
            when (it) {
                is SCHospitalData -> id = it.id
                is SCAdministrativeData -> id = it.id
                is SCOutpatientData -> id = it.id
                is SCDoctorData -> id = it.id
                is SCVisitData -> id = it.id
            }
            if (LitePal.find(T::class.java, id.toLong()) == null) it.save()
        }
    }
}