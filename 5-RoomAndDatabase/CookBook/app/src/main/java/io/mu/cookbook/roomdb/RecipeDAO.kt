package io.mu.cookbook.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.mu.cookbook.model.Recipe
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

@Dao
interface RecipeDAO {
    @Query("Select * from recipe") fun getAll() : Flowable<List<Recipe>>
    @Query("Select * from recipe where id = :id") fun findById(id: Int) : Flowable<Recipe>
    @Insert fun insert(recipe: Recipe) : Completable
    @Delete fun delete(recipe: Recipe) : Completable
}