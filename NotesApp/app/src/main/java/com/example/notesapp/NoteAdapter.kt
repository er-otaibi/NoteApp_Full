package com.example.notesapp

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.note_row.view.*

class NoteAdapter(private val activity: MainActivity, private val notes: List<String>):  RecyclerView.Adapter<NoteAdapter.ItemViewHolder>(){
    class ItemViewHolder (itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.note_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        var list1 = notes[position]

        holder.itemView.apply {
            textView.text = list1
            editBtn.setOnClickListener {
                var note = textView.text.toString()
                activity.editAlert(note)
            }
            deleteBtn.setOnClickListener {
                var note = textView.text.toString()
                activity.deleteAlert(note) }
        }
    }
    override fun getItemCount() = notes.size



}