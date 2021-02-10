package br.com.brunoccbertolini.easyandfastnotes_noads.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.brunoccbertolini.easyandfastnotes_noads.R
import br.com.brunoccbertolini.easyandfastnotes_noads.data.NoteEntity
import br.com.brunoccbertolini.easyandfastnotes_noads.databinding.RvItemBinding

class NoteListAdapter(private val notesList: List<NoteEntity>,
private val listener:ListItemListener):
    RecyclerView.Adapter<NoteListAdapter.NoteListViewHolder>() {

    val selectedNotes = arrayListOf<NoteEntity>()

   inner class NoteListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
       val binding = RvItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.rv_item, parent, false)
        return NoteListViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteListViewHolder, position: Int) {
        val note = notesList[position]
        holder.binding.apply {
            tvNote.text = note.text
            root.setOnClickListener {
                listener.editNote(note.id)
            }
            fabNote.setOnClickListener {
                if (selectedNotes.contains(note)){
                    selectedNotes.remove(note)
                    fabNote.setImageResource(R.drawable.ic_notes)
                }else{
                    selectedNotes.add(note)
                    fabNote.setImageResource(R.drawable.ic_check)
                }
                listener.onItemSelectionChanged()
            }
            fabNote.setImageResource(
                if (selectedNotes.contains(note)){
                    R.drawable.ic_check
                }else{
                    R.drawable.ic_notes
                }
            )
        }
    }

    override fun getItemCount() = notesList.size


    interface ListItemListener {
        fun editNote(noteId:Int)
        fun onItemSelectionChanged()
    }

}

