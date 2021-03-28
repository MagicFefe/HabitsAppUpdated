package com.swaptech.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.swaptech.domain.DatabaseHabit
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "HabitsLocal")
data class HabitForLocal(
        override var color: Int = 0,
        override var count: Int,
        override var date: Int,
        override var description: String,
        @PrimaryKey (autoGenerate = true) override var doneDates: Int = 0,
        override var frequency: Int,
        override var priority: String,
        override var title: String,
        override var type: String,
        override var uid: String = "",

) : Parcelable, DatabaseHabit