package com.example.notesapp

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "myNotes.db", null, 1) {

    private var sqLiteDatabase: SQLiteDatabase = writableDatabase
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table notes (Note text )")

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}

    fun saveNote(s1: String): Long {
        val cv = ContentValues()
        cv.put("Note", s1)
        return sqLiteDatabase.insert("notes", null, cv)
    }

    @SuppressLint("Range")
    fun getNote(note: String): String {

        var c : Cursor = sqLiteDatabase.query("notes" , null , "Note=?" , arrayOf(note) , null , null , null)
        c.moveToFirst()

       return c.getString(c.getColumnIndex("Note"))
    }

    @SuppressLint("Range")
    fun readData() {
        var selectQuery = "SELECT  * FROM notes"
        var cursor: Cursor? = null
        try {

            cursor = sqLiteDatabase.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            sqLiteDatabase.execSQL(selectQuery)
        }
        var note: String
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                   note = cursor.getString(cursor.getColumnIndex("Note"))
                    MyList.notesList.add(note)
                } while (cursor.moveToNext())
            }
        }
    }

    fun update(s1: String, s2: String) {
        val cv = ContentValues()
        cv.put("Note", s2)
        sqLiteDatabase.update("notes" , cv , "Note=?" , arrayOf(s1))

    }

    fun delete(s1: String){
        sqLiteDatabase.delete("notes", "Note=?", arrayOf(s1))
    }
}