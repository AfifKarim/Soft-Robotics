package com.soft.robotics.Data.Local.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class Country(
    @PrimaryKey val code: String,
    val name: String
)