package io.mu.cookbook.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Recipe(
    @ColumnInfo("name") var name: String,
    @ColumnInfo("ingredients") var ingredients: String,
    @ColumnInfo("image") var image: ByteArray,
){
    @PrimaryKey(autoGenerate = true) var id = 0
}
