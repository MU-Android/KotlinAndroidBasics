package io.mu.cookbook.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.mu.cookbook.model.Recipe

@Dao
interface RecipeDAO {
    @Query("Select * from recipe") fun getAll() : List<Recipe>
    @Query("Select * from recipe where id = :id") fun findById(id: Int) : Recipe?
    @Insert fun insert(recipe: Recipe)
    @Delete fun delete(recipe: Recipe)
}