package com.swaptech.habitstwo.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Habits")
data class HabitEntity(
        @PrimaryKey(autoGenerate = true) val id: Int,
        @ColumnInfo(name = "color")  val color: Int,
        @ColumnInfo(name = "name") val name: String,
        @ColumnInfo(name = "description") val description: String,
        @ColumnInfo(name = "priority") val priority: String,
        @ColumnInfo(name = "typeOfHabit") val typeOfHabit: String,
        @ColumnInfo(name = "periodicity") val periodicity: String,
        @ColumnInfo(name = "countOfExecs") val countOfExecs: Int,
        @ColumnInfo(name = "frequencyOfExecs") val frequencyOfExecs: Int
) : Parcelable

