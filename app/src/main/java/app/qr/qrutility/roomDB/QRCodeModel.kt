package app.qr.qrutility.roomDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "qr_code_table")
class QRCodeModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "qr_id")
    var id: Int = 0
    @ColumnInfo(name = "qr_data")
    var data: String = ""
    @ColumnInfo(name = "qr_type")
    var type: String = ""
    @ColumnInfo(name = "qr_date_time")
    var datetime: String = ""
    @ColumnInfo(name = "qr_desc")
    var description: String = ""
    @ColumnInfo(name = "qr_meta_data")
    var meta: String = ""
}