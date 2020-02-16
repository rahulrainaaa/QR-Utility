package app.qr.qrutility.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.DialogFragment
import app.qr.qrutility.R
import app.qr.qrutility.roomDB.QRCodeModel
import app.qr.qrutility.viewModel.QRListActivityViewModel

class QRImageDialogFragment(
    private val viewModel: QRListActivityViewModel,
    private val model: QRCodeModel
) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.dialog_qr_image, null)
        view.findViewById<ImageButton>(R.id.imageButton).setOnClickListener { dismiss() }
        val displayMetrics = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels - (displayMetrics.widthPixels / 10)
        val height = displayMetrics.widthPixels - (displayMetrics.widthPixels / 10)
        val side = if (width < height) width else height
        viewModel.setQrBitmap(view.findViewById<ImageButton>(R.id.imageView), model, side)
        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return AlertDialog.Builder(activity)
            .setCancelable(false)
            .show()
    }

    override fun onResume() {
        super.onResume()

        val displayMetrics = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels - (displayMetrics.widthPixels / 100)
        val height = displayMetrics.heightPixels - (displayMetrics.heightPixels / 100)
        val side = if (width < height) width else height
        val window = dialog!!.window!!
        window.setLayout(side, side)
        window.setGravity(Gravity.CENTER)
    }
}