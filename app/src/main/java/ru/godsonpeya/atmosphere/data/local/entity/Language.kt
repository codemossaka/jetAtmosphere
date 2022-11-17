package ru.godsonpeya.atmosphere.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "language")
class Language(@PrimaryKey var id: Int? = null, var code: String? = null, var name: String? = null)
