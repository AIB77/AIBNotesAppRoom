package com.example.aibnotesapproom

import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row.view.*

class RecyclerViewAdapter(private val activity: MainActivity, private val TheNote: List<MyNote>): RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder> (){



    class ItemViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return  ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val pNote=TheNote[position]

        holder.itemView.apply {
            tvResult.text= pNote.noteText
            deletenote.setOnClickListener {
                activity.deleteNote(pNote.id)
            }
            updatenote.setOnClickListener {
                activity.raiseDialog(pNote.id)
            }

        }
    }

    override fun getItemCount() = TheNote.size

}

