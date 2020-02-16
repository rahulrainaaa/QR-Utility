package app.qr.qrutility.activity

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import app.qr.qrutility.R
import app.qr.qrutility.adapter.QRItemDecoration
import app.qr.qrutility.adapter.QRRecyclerAdapter
import app.qr.qrutility.databinding.ActivityQrListBinding
import app.qr.qrutility.viewModel.QRListActivityPresenter
import app.qr.qrutility.viewModel.QRListActivityViewModel
import kotlinx.android.synthetic.main.activity_qr_list.*
import kotlinx.android.synthetic.main.content_qr_list.*


class QRListActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityQrListBinding
    private lateinit var mPresenter: QRListActivityPresenter
    private lateinit var mViewModel: QRListActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_qr_list)
        mPresenter = QRListActivityPresenter(this)
        mViewModel = QRListActivityViewModel(this)
        mBinding.qrViewPresenter = mPresenter
        mBinding.qrViewModel = mViewModel
        setSupportActionBar(toolbar)
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val columns = if (displayMetrics.widthPixels > 1200) 3 else 2
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(columns, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.addItemDecoration(QRItemDecoration())
        recyclerView.adapter = QRRecyclerAdapter(this, mViewModel, mPresenter)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu_qr_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_about ->
                Toast.makeText(this, R.string.under_dev, Toast.LENGTH_SHORT).show()
            R.id.action_rate_app ->
                Toast.makeText(this, R.string.under_dev, Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mPresenter.onQrScanResult(requestCode, resultCode, data)
    }
}
