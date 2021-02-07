package br.com.brunoccbertolini.easyandfastnotes_noads.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Room
import br.com.brunoccbertolini.easyandfastnotes_noads.NEW_NOTE_ID
import java.util.*

@Entity
data class NoteEntity(

    var id:Int,
    var date: Date,
    var text: String
){
    constructor(): this(NEW_NOTE_ID, Date(), "")
    constructor(date:Date, text: String): this(NEW_NOTE_ID, date, text)
}