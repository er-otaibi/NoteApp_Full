package com.example.notesapp

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.MyList.notesList

object MyList{

     var notesList = arrayListOf<String>()
}

class MainActivity : AppCompatActivity() {
    lateinit var rvMain: RecyclerView
    lateinit var etnote: EditText
    lateinit var addBtn: Button
    lateinit var helper: DBHelper
    var note =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvMain = findViewById(R.id.rvMain)
        etnote = findViewById(R.id.etnote)
        addBtn = findViewById(R.id.addBtn)

        helper = DBHelper(applicationContext)
        notesList.clear()
        helper.readData()

        rvMain.adapter = NoteAdapter(this,notesList)
        rvMain.layoutManager = LinearLayoutManager(this)


        addBtn.setOnClickListener {
            note = etnote.text.toString()

            var status = helper.saveNote(note)
            Toast.makeText(this , "Your note is added successfully $status" , Toast.LENGTH_LONG).show()

            var r = helper.getNote(note)
            etnote.setText("")
            notesList.add(r)
            rvMain.adapter!!.notifyDataSetChanged()
        }
    }


    fun updateNote(note1 : String, new: String){
        helper.update(note1, new)
        updateRV()
        Toast.makeText(this, "Note Updated", Toast.LENGTH_LONG).show()


    }

    fun deleteNote(note: String){
        helper.delete(note)
        updateRV()
        Toast.makeText(this, "Note Deleted", Toast.LENGTH_LONG).show()
    }

    private fun updateRV(){
        notesList.clear()
        helper.readData()
        rvMain.adapter!!.notifyDataSetChanged()
    }



    fun editAlert(oldNote: String){
        val dialogBuilder = androidx.appcompat.app.AlertDialog.Builder(this)
        val input = EditText(this)
        var newNote = ""
        dialogBuilder.setMessage("")
            .setPositiveButton("Save", DialogInterface.OnClickListener {
                    dialog, id ->  newNote = input.text.toString()
                                   updateNote(oldNote,newNote)
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })
        val alert = dialogBuilder.create()

        alert.setTitle("Edit Alert")
        alert.setView(input)
        alert.show()


    }

    fun deleteAlert( dNote: String){
        val dialogBuilder = androidx.appcompat.app.AlertDialog.Builder(this)


        dialogBuilder.setMessage("Confirm delete ?")
            .setPositiveButton("Delete", DialogInterface.OnClickListener {
                    dialog, id -> deleteNote(dNote)
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })
        val alert = dialogBuilder.create()

        alert.setTitle("Delete Alert")
        alert.show()


    }


}