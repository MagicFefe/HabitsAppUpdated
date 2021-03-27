package com.swaptech.data.di

import android.content.Context
import androidx.room.Room
import com.swaptech.data.database.HabitsDatabase
import com.swaptech.data.database.HabitsFromServerDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): HabitsDatabase {
        return Room.databaseBuilder(context, HabitsDatabase::class.java, "fromServer").allowMainThreadQueries().build()
    }

    @Provides
    fun provideDao(db: HabitsDatabase): HabitsFromServerDao {
        return db.habitsFromServerDao()
    }
}