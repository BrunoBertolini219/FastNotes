package br.com.brunoccbertolini.easyandfastnotes_noads

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.brunoccbertolini.easyandfastnotes_noads.data.AppDateBase
import br.com.brunoccbertolini.easyandfastnotes_noads.data.NoteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class EditorViewModel(app: Application) : AndroidViewModel(app) {

    private val database = AppDateBase.getInstance(app)
    val currentNote = MutableLiveData<NoteEntity>()

    fun getNoteById(noteId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val note =
                    if (noteId != NEW_NOTE_ID) {
                        database?.noteDao()?.getNote(noteId)
                    } else {
                        NoteEntity()
                    }
                currentNote.postValue(note)
            }
        }
    }

    fun updateNote() {
        currentNote.value?.let {
            Log.i(TAG, "updateNote: ${it.text.trim()}")
            it.text = it.text.trim()
            if (it.id == NEW_NOTE_ID && it.text.isEmpty()) {
                return
            }

            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    if (it.text.isEmpty()) {
                        database?.noteDao()?.deleteNote(it)
                    } else {
                        database?.noteDao()?.insertNote(it)
                    }
                }
            }
        }
    }
}