package kg.mobile.coins.room.dao

import androidx.room.*
import kg.mobile.coins.room.model.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao{
    @Query("SELECT * FROM category cat WHERE isActive = 1 ")
    fun getAllCategories(): Flow<List<Category>>

    @Query("SELECT * FROM category cat WHERE cat.id = :id")
    suspend fun getById(id: Int): Category

    @Query("SELECT * FROM category cat WHERE parentId = :parentId AND isActive = 1" +
            " AND (EXISTS (SELECT * FROM coin coin WHERE cat.Id = coin.categoryId AND isActive = 1)" +
            " OR EXISTS (SELECT * FROM category catChild WHERE cat.Id = catChild.parentId AND isActive = 1))")
    fun getCategoriesByParentId(parentId: Int): Flow<List<Category>>

    @Query("SELECT * FROM category cat WHERE parentId IS NULL AND isActive = 1" +
            " AND (EXISTS (SELECT * FROM coin coin WHERE cat.Id = coin.categoryId AND isActive = 1)" +
            " OR EXISTS (SELECT * FROM category catChild WHERE cat.Id = catChild.parentId AND isActive = 1))")
    fun getMainCategories(): Flow<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: Category)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(categories: List<Category>)

    @Update()
    suspend fun update(category: Category)

    @Update()
    suspend fun update(categories: List<Category>)

    @Delete()
    suspend fun delete(category: Category)

    @Delete()
    suspend fun delete(categories: List<Category>)
}