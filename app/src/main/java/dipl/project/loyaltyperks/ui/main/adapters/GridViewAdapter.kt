package dipl.project.loyaltyperks.ui.main.adapters

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import com.bumptech.glide.Glide
import dipl.project.loyaltyperks.R
import dipl.project.loyaltyperks.model.CardData
import dipl.project.loyaltyperks.utils.Constants.ISSUER_ID


class GridViewAdapter(var context: Context, var arrayList: ArrayList<CardData>) : BaseAdapter() {

    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getItem(p0: Int): Any {
        return arrayList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var view: View = View.inflate(context, R.layout.grid_item_list, null)


        val encoder = QRGEncoder(arrayList[p0].objectId, null,QRGContents.Type.TEXT, 800)

        var alertView = View.inflate(context, R.layout.custom_alert_view, null)

        var imageHolder = alertView.findViewById<ImageView>(R.id.ivQRCode)
        var programNameText = alertView.findViewById<TextView>(R.id.tvAlertProgramName)
        var storeNameText = alertView.findViewById<TextView>(R.id.tvAlertStoreName)
        storeNameText.text = arrayList[p0].storeName
        programNameText.text = arrayList[p0].loyaltyProgramName
        imageHolder.setImageBitmap(encoder.bitmap)



        view.setOnClickListener {
            var alertDialogBuilder = AlertDialog.Builder(this.context)
            alertDialogBuilder.setTitle("Loyalty card")
            alertDialogBuilder.setView(alertView)
            alertDialogBuilder.setPositiveButton("Open loyalty card") {dialogInterface: DialogInterface, i: Int ->
                val uri = Uri.parse("https://pay.google.com/gp/v/object/$ISSUER_ID.${arrayList[p0].objectId}")
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = uri
                startActivity(context,intent, null)
            }
            alertDialogBuilder.show()

        }

        Glide.with(context)
            .load(arrayList[p0].image)
            .fitCenter()
            .into(view.findViewById(R.id.cardImage))
        return view
    }
}