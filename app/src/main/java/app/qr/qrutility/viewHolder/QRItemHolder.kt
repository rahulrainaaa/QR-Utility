package app.qr.qrutility.viewHolder

import android.graphics.Bitmap
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.qr.qrutility.R
import app.qr.qrutility.roomDB.QRCodeModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter


class QRItemHolder(private val view: View, private val width: Int) : RecyclerView.ViewHolder(view) {

    private val imageView = view.findViewById(R.id.imgQr) as ImageView
    private val txtData: TextView = view.findViewById(R.id.txtData)

    fun setValues(model: QRCodeModel) {


        txtData.text = "${model.id}. ${model.data}"

        val bitMatrix = QRCodeWriter().encode("${model.data}", BarcodeFormat.QR_CODE, width, width)

        val bitmap = Bitmap.createBitmap(bitMatrix.width, bitMatrix.height, Bitmap.Config.ARGB_8888)

        for (x in 0 until bitMatrix.width)
            for (y in 0 until bitMatrix.height)
                bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)

        imageView.setImageBitmap(bitmap)
    }

    fun setPosition(position: Int) {
        view.tag = position
    }

}