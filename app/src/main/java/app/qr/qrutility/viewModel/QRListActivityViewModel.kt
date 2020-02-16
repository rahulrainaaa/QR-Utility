package app.qr.qrutility.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import app.qr.qrutility.activity.QRListActivity
import app.qr.qrutility.roomDB.AppDatabase
import app.qr.qrutility.roomDB.QRCodeModel

class QRListActivityViewModel(private val activity: QRListActivity) : ViewModel() {

    val currentQRData: LiveData<List<QRCodeModel>> by lazy {

        AppDatabase.getDatabase(activity).getQRCodeDao().selectAll()
    }
}