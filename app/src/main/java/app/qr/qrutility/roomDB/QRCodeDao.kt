package app.qr.qrutility.roomDB

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface QRCodeDao {

    @Query("Select * from qr_code_table")
    fun selectAll(): LiveData<List<QRCodeModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg obj: QRCodeModel)

    @Delete
    fun delete(obj: QRCodeModel)

    @Query("Delete from qr_code_table")
    fun deleteAll()
}