package com.example.headsup

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context): SQLiteOpenHelper(context,"Details.db", null, 1) {
    private val sqLiteDatabase: SQLiteDatabase = writableDatabase

    override fun onCreate(db: SQLiteDatabase?) {
        if(db != null){
            db.execSQL("create table celebrity (pk integer primary key autoincrement, Name text, Taboo1 text, Taboo2 text, Taboo3 text)")
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}

    fun addCelebrity(celebrity: Celebrity) : Int {
        val contentValues = ContentValues()
        contentValues.put("Name", celebrity.name)
        contentValues.put("Taboo1", celebrity.taboo1)
        contentValues.put("Taboo2", celebrity.taboo2)
        contentValues.put("Taboo3", celebrity.taboo3)
        return sqLiteDatabase.insert("celebrity", null, contentValues).toInt()
    }

    fun readCelebrity() : ArrayList<Celebrity> {
        var celebrity = arrayListOf<Celebrity>()
        val cursor: Cursor = sqLiteDatabase.rawQuery("SELECT * FROM celebrity", null)
        if (cursor.count == 0) {
            println("Not Found")
        } else {
            while (cursor.moveToNext()) {
                val pk = cursor.getInt(0)
                val name = cursor.getString(1)
                val taboo1 = cursor.getString(2)
                val taboo2 = cursor.getString(3)
                val taboo3 = cursor.getString(4)
                val newCelebrity = Celebrity(name,pk, taboo1, taboo2, taboo3)
                celebrity.add(newCelebrity)
            }
        }
        return celebrity
    }

    fun updateCelebrity(celebrity: Celebrity) : Int {
        val contentValues = ContentValues()
        contentValues.put("Name", celebrity.name)
        contentValues.put("Taboo1", celebrity.taboo1)
        contentValues.put("Taboo2", celebrity.taboo2)
        contentValues.put("Taboo3", celebrity.taboo3)
        return sqLiteDatabase.update("celebrity", contentValues, "pk = ${celebrity.pk}", null)
    }
    fun deleteCelebrity(pk: Int) : Int {
        return sqLiteDatabase.delete("celebrity", "pk = $pk", null)
    }
}