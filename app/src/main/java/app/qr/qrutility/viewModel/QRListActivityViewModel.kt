package app.qr.qrutility.viewModel

import android.graphics.Bitmap
import android.graphics.Color
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.qr.qrutility.activity.QRListActivity
import app.qr.qrutility.roomDB.AppDatabase
import app.qr.qrutility.roomDB.QRCodeModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QRListActivityViewModel(private val activity: QRListActivity) : ViewModel() {

    val currentQRData: LiveData<List<QRCodeModel>> by lazy {
        AppDatabase.getDatabase(activity).getQRCodeDao().selectAll()
    }

    private companion object {
        val bitmapImagesCache: HashMap<Int, Bitmap> by lazy {
            HashMap<Int, Bitmap>()
        }
    }

    fun setQrBitmap(imageView: ImageView, model: QRCodeModel, width: Int) = viewModelScope.launch {

        var bitmap =
            if (bitmapImagesCache.containsKey(model.id)) bitmapImagesCache[model.id]
            else withContext(Dispatchers.IO) {
                val bitMatrix =
                    QRCodeWriter().encode("${model.data}", BarcodeFormat.QR_CODE, width, width)
                val bitmap =
                    Bitmap.createBitmap(bitMatrix.width, bitMatrix.height, Bitmap.Config.ARGB_8888)
                for (x in 0 until bitMatrix.width)
                    for (y in 0 until bitMatrix.height)
                        bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
                bitmapImagesCache[model.id] = bitmap
                bitmap
            }

        imageView.setImageBitmap(bitmap)
    }
}
