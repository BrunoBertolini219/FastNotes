package br.com.brunoccbertolini.easyandfastnotes_noads.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDateBase: RoomDatabase() {
    abstract fun noteDao(): NoteDao?

    companion object{
        private var INSTANCE: AppDateBase? = null

        fun getInstance(context: Context): AppDateBase?{
            if (INSTANCE == null){
                synchronized(AppDateBase::class){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDateBase::class.java,
                        "easyandfast.db"
                    ).build()
                }
            }
            return INSTANCE
        }

    }
}