package app.qr.qrutility.viewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.qr.qrutility.R
import app.qr.qrutility.roomDB.QRCodeModel
import app.qr.qrutility.viewModel.QRListActivityViewModel


class QRItemHolder(
    private val view: View,
    private val width: Int,
    private val viewModel: QRListActivityViewModel
) : RecyclerView.ViewHolder(view) {

    private val imageView = view.findViewById(R.id.imgQr) as ImageView
    private val txtData: TextView = view.findViewById(R.id.txtData)

    fun setValues(position: Int, model: QRCodeModel) {

        view.tag = position
        txtData.text = "${position + 1}. ${model.data}"
        viewModel.setQrBitmap(imageView, model, width)
    }

}