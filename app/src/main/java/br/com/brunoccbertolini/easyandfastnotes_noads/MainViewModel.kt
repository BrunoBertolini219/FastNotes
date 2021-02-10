package br.com.brunoccbertolini.easyandfastnotes_noads

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.com.brunoccbertolini.easyandfastnotes_noads.data.AppDateBase
import br.com.brunoccbertolini.easyandfastnotes_noads.data.NoteEntity
import br.com.brunoccbertolini.easyandfastnotes_noads.data.SampleDataProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val database = AppDateBase.getInstance(app)
    val notesList = database?.noteDao()?.getAll()

    fun addSampleData(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val sampleNotes = SampleDataProvider.getNotes()
                database?.noteDao()?.insertAll(sampleNotes)
            }
        }

    }

    fun deleteNotes(selectedNotes: List<NoteEntity>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                database?.noteDao()?.deleteNotes(selectedNotes)
            }
        }
    }

    fun deleteAllNotes() {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                database?.noteDao()?.deleteAll()
            }
        }
    }


}