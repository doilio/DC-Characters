package com.doiliomatsinhe.dcvilains.adapter

import android.graphics.Bitmap
import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.doiliomatsinhe.dcvilains.R
import com.doiliomatsinhe.dcvilains.model.Villain


@BindingAdapter(value = ["villainImage", "cardView"], requireAll = false)
fun ImageView.setVillainImage(item: Villain, cardView: CardView) {

    Glide.with(context)
        .asBitmap()
        .load(item.images.sm)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .listener(object : RequestListener<Bitmap?> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Bitmap?>?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                resource: Bitmap?,
                model: Any?,
                target: Target<Bitmap?>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {


                if (resource != null) {
                    val p = Palette.from(resource).generate()
                    // Use generated instance
                    cardView.setCardBackgroundColor(p.getDarkMutedColor(Color.DKGRAY))
                }
                return false
            }
        })
        .into(this)
}

@BindingAdapter("groupAffiliation")
fun TextView.setGroupAffiliation(item: Villain) {

    text = if (item.connections.groupAffiliation != "-") {
        item.connections.groupAffiliation
    } else {
        "No Affiliations"
    }

}

@BindingAdapter("villainName")
fun TextView.setVillainName(item: Villain) {
    text = item.name
}

@BindingAdapter("recyclerViewAdapter")
fun RecyclerView.setRecyclerViewAdapter(item: VillainAdapter) {

    adapter = item
    hasFixedSize()
    layoutManager = StaggeredGridLayoutManager(
        resources.getInteger(R.integer.span_count),
        StaggeredGridLayoutManager.VERTICAL
    )
}
