package dipl.project.loyaltyperks.ui.mainUser.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import dipl.project.loyaltyperks.R
import dipl.project.loyaltyperks.data.CardData

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
        Glide.with(context)
            .load(arrayList[p0].image)
            .fitCenter()
            .into(view.findViewById(R.id.cardImage))
        return view
    }
}