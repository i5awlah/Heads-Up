package com.example.headsup

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context): SQLiteOpenHelper(context,"celebrityDetails.db", null, 1) {
    private val sqLiteDatabase: SQLiteDatabase = writableDatabase

    override fun onCreate(db: SQLiteDatabase?) {
        if(db != null){
            db.execSQL("create table celebrityInfo (Name text, Taboo1 text, Taboo2 text, Taboo3 text)")
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}

    fun saveData(name: String, taboo1: String, taboo2: String, taboo3: String) : Long {
        val contentValues = ContentValues()
        contentValues.put("Name", name)
        contentValues.put("Taboo1", taboo1)
        contentValues.put("Taboo2", taboo2)
        contentValues.put("Taboo3", taboo3)
        return sqLiteDatabase.insert("celebrityInfo", null, contentValues)
    }
    fun readData() : ArrayList<Celebrity> {
        var celebrity = arrayListOf<Celebrity>()
        val cursor: Cursor = sqLiteDatabase.rawQuery("SELECT * FROM celebrityInfo", null)
        if (cursor.count == 0) {
            println("Not Found")
        } else {
            while (cursor.moveToNext()) {
                val name = cursor.getString(0)
                val taboo1 = cursor.getString(1)
                val taboo2 = cursor.getString(2)
                val taboo3 = cursor.getString(3)
                val newCelebrity = Celebrity(name,0, taboo1, taboo2, taboo3)
                celebrity.add(newCelebrity)
            }
        }
        return celebrity
    }
}