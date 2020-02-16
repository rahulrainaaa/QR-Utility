package app.qr.qrutility.adapter

import android.os.Handler
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import app.qr.qrutility.R
import app.qr.qrutility.activity.QRListActivity
import app.qr.qrutility.dialog.QRImageDialogFragment
import app.qr.qrutility.roomDB.QRCodeModel
import app.qr.qrutility.viewHolder.QRItemHolder
import app.qr.qrutility.viewModel.QRListActivityPresenter
import app.qr.qrutility.viewModel.QRListActivityViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.content_qr_list.*

class QRRecyclerAdapter(
    private val activity: QRListActivity,
    private val viewModel: QRListActivityViewModel,
    private val presenter: QRListActivityPresenter
) :
    RecyclerView.Adapter<QRItemHolder>(), View.OnClickListener, View.OnLongClickListener {

    private var mList: List<QRCodeModel>? = null

    init {

        val qrObserver = Observer<List<QRCodeModel>> { updatedQrList ->
            mList = updatedQrList
            notifyDataSetChanged()
        }
        val qrListLiveData = viewModel.currentQRData
        qrListLiveData.observe(activity, qrObserver)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QRItemHolder {

        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)

        val width = displayMetrics.widthPixels - (displayMetrics.widthPixels / 10)
        val height = displayMetrics.widthPixels - (displayMetrics.widthPixels / 10)
        val smallSide = if (width < height) width else height

        val side = if (smallSide > 1200)
            (parent.width / 3) - (parent.width / 20)
        else (parent.width / 2) - (parent.width / 20)

        val view = LayoutInflater.from(activity).inflate(R.layout.item_qr_list, null)
        view.setOnClickListener(this)
        view.setOnLongClickListener(this)
        return QRItemHolder(view, side, viewModel)

    }

    override fun onBindViewHolder(holder: QRItemHolder, position: Int) {

        mList?.get(position)?.let { holder.setValues(position, it) }
    }

    override fun getItemCount(): Int {

        return if (mList == null) 0 else mList!!.size
    }

    override fun onClick(itemView: View?) {

        val position = itemView!!.tag as Int
        val model = mList!![position]
        QRImageDialogFragment(viewModel, model).show(activity.supportFragmentManager, "$position")
    }

    override fun onLongClick(itemView: View?): Boolean {

        Snackbar.make(activity.recyclerView, "Are you sure to delete this", Snackbar.LENGTH_SHORT)
            .setAction("Delete") {

                val position = itemView!!.tag as Int
                val model = mList!![position]
                var undoDelete = false

                val snackBar =
                    Snackbar.make(
                        activity.recyclerView,
                        "Deleting: ${model.data}",
                        Snackbar.LENGTH_INDEFINITE
                    ).setAction("Undo") { undoDelete = true }
                snackBar.show()

                Handler().postDelayed({

                    snackBar.dismiss()
                    if (!undoDelete) presenter.deleteQrFromDB(model)

                }, 1600)

            }.show()
        return true
    }
}