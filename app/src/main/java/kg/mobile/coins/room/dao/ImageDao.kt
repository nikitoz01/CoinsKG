package kg.mobile.coins.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kg.mobile.coins.room.model.Image
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {

    @Query("SELECT * FROM image where id = :imageId")
    fun getImageById(imageId: Int): Flow<Image>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(image: Image)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(images: List<Image>)
}