package com.doiliomatsinhe.dcvilains.adapter

import android.graphics.Bitmap
import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.palette.graphics.Palette
import coil.api.load
import coil.bitmappool.BitmapPool
import coil.size.Size
import coil.transform.Transformation
import com.doiliomatsinhe.dcvilains.model.Villain

@BindingAdapter(value = ["villainImage", "cardView"], requireAll = false)
fun ImageView.setVillainImage(item: Villain, cardView: CardView) {

    this.load(item.images.md) {

      transformations(object : Transformation {
            override fun key() = "dowyTransformation"

            override suspend fun transform(pool: BitmapPool, input: Bitmap, size: Size): Bitmap {
                val p = Palette.from(input).generate()
                if (p.darkMutedSwatch != null){
                    cardView.setCardBackgroundColor(p.getDarkMutedColor(0))
                }else{
                    cardView.setCardBackgroundColor(Color.DKGRAY)
                }

                return input
            }
        }).crossfade(true)

    }
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

