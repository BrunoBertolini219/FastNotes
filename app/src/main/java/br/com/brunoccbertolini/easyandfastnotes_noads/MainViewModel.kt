package br.com.brunoccbertolini.easyandfastnotes_noads

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.brunoccbertolini.easyandfastnotes_noads.data.NoteEntity
import br.com.brunoccbertolini.easyandfastnotes_noads.data.SampleDataProvider

class MainViewModel : ViewModel() {
    val notesList = MutableLiveData<List<NoteEntity>>()

    init {
        notesList.value = SampleDataProvider.getNotes()
    }

}