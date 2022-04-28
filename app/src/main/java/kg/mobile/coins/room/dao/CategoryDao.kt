package kg.mobile.coins.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kg.mobile.coins.room.model.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao{
    @Query("SELECT * FROM category where isActive = 1")
    fun getAllCategories(): Flow<List<Category>>

    @Query("SELECT * FROM category where parentId = :parentId and isActive = 1")
    fun getCategoriesByParentId(parentId: Int): Flow<List<Category>>

    @Query("SELECT * FROM category where parentId IS NULL and isActive = 1")
    fun getMainCategories(): Flow<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(category: Category)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(categories: List<Category>)
}