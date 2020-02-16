package app.qr.qrutility.viewModel

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.qr.qrutility.activity.QRListActivity
import app.qr.qrutility.roomDB.AppDatabase
import app.qr.qrutility.roomDB.QRCodeModel
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QRListActivityPresenter(private val activity: QRListActivity) : ViewModel() {

    fun fabClickQrScan() {

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {

            Toast.makeText(activity, "Need Camera Permission", Toast.LENGTH_SHORT).show()
            return
        }

        var intentIntegrator = IntentIntegrator(activity)
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        intentIntegrator.setPrompt("Scan a barcode")
        intentIntegrator.setCameraId(0)  // Use a specific camera of the device
        intentIntegrator.setBeepEnabled(true)
        intentIntegrator.setBarcodeImageEnabled(true)
        intentIntegrator.initiateScan()
    }

    fun onQrScanResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(activity, "Cancelled", Toast.LENGTH_LONG).show()
            } else {

                val model = QRCodeModel()
                model.data = result.contents
                saveQrInDB(model)
                Toast.makeText(activity, result.contents, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun saveQrInDB(model: QRCodeModel) = viewModelScope.launch {

        withContext(Dispatchers.IO) {
            AppDatabase.getDatabase(activity).getQRCodeDao().insertAll(model)
        }
    }

    fun deleteQrFromDB(model: QRCodeModel) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            AppDatabase.getDatabase(activity).getQRCodeDao().delete(model)
        }
    }
}