package br.com.brunoccbertolini.easyandfastnotes_noads.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Room
import br.com.brunoccbertolini.easyandfastnotes_noads.NEW_NOTE_ID
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var date: Date,
    var text: String
):Parcelable
{
    constructor(): this(NEW_NOTE_ID, Date(), "")
    constructor(date:Date, text: String): this(NEW_NOTE_ID, date, text)
}