package io.mu.cookbook.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import io.mu.cookbook.model.Recipe

@Database(entities = [Recipe::class], version = 1)
abstract class RecipeDatabase : RoomDatabase(){
    abstract fun recipeDao(): RecipeDAO
}