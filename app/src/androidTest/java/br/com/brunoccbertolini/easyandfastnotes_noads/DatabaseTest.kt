package br.com.brunoccbertolini.easyandfastnotes_noads

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.brunoccbertolini.easyandfastnotes_noads.data.AppDateBase
import br.com.brunoccbertolini.easyandfastnotes_noads.data.NoteDao
import br.com.brunoccbertolini.easyandfastnotes_noads.data.NoteEntity
import br.com.brunoccbertolini.easyandfastnotes_noads.data.SampleDataProvider
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before


@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var dao: NoteDao
    private lateinit var database: AppDateBase
    private lateinit var note: NoteEntity


    @Before
    fun createDb(){
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(appContext, AppDateBase::class.java)
            .allowMainThreadQueries()
            .build()

        dao = database.noteDao()!!
    }

    @Test
    fun createNotes() {
        dao.insertAll(SampleDataProvider.getNotes())
        val count = dao.getCount()
        assertEquals(count, SampleDataProvider.getNotes().size)
    }

    @Test
    fun insertNote1(){
        note = NoteEntity()
        note.text = "Testing"
        dao.insertNote(note)
        val count = dao.getCount()
        assertEquals(count, 1)
    }

    @Test
    fun inserttNote2(){
        note = NoteEntity()
        note.text = "Testing"
        dao.insertNote(note)
        val savedNote = dao.getNote(1)
        assertEquals(savedNote?.id , 1)
    }

    @After
    fun closeDb(){
       database.close()
    }
}