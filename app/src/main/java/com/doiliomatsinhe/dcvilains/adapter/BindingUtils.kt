package com.doiliomatsinhe.dcvilains.adapter

import android.graphics.Bitmap
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.paging.LoadState
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.doiliomatsinhe.dcvilains.model.Villain


@BindingAdapter(value = ["villainImage", "cardView"], requireAll = false)
fun ImageView.setVillainImage(item: Villain, cardView: CardView) {

    Glide.with(context)
        .asBitmap()
        .load(item.images.md)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .listener(object : RequestListener<Bitmap?> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Bitmap?>?,
                isFirstResource: Boolean
            ): Boolean {
                cardView.setCardBackgroundColor(item.dominantcolor)
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
                    item.dominantcolor = p.getDarkMutedColor(Color.DKGRAY)
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

@BindingAdapter("visibility")
fun ProgressBar.setVisibility(item: LoadState) {

    when (item) {
        is LoadState.Loading -> {
            this.visibility = View.VISIBLE
        }
        is LoadState.Error -> {
            this.visibility = View.GONE
        }
        else -> this.visibility = View.GONE
    }
}

@BindingAdapter("visibility")
fun ConstraintLayout.setVisibility(item: LoadState) {

    when (item) {
        is LoadState.Error -> {
            this.visibility = View.VISIBLE
        }
        else -> this.visibility = View.GONE
    }
}

@BindingAdapter("visibility")
fun RecyclerView.setVisibility(item: LoadState) {

    when (item) {
        is LoadState.NotLoading -> {
            this.visibility = View.VISIBLE
        }
        is LoadState.Error -> {
            this.visibility = View.GONE
        }
        else -> this.visibility = View.GONE
    }
}
