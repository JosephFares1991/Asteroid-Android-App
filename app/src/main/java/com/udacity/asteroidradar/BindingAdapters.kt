package com.udacity.asteroidradar

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("statusIcon")
fun ImageView.setStatusImage(item:Asteroid) {
    if (item.isPotentiallyHazardous) {
        setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("detailIcon")
fun ImageView.setDetailImage(item:Asteroid) {
    if (item.isPotentiallyHazardous) {
        setImageResource(R.drawable.asteroid_hazardous)
    } else {
        setImageResource(R.drawable.asteroid_safe)
    }
}


@BindingAdapter("asteroidId")
fun TextView.setAsteroidId(item: Asteroid?) {
    item?.let {
        text = item.id.toString()
    }
}

@BindingAdapter("approachDate")
fun TextView.setApproachId(item: Asteroid?) {
    item?.let {
        text = item.closeApproachDate
    }
}

@BindingAdapter("absoluteMagnitude")
fun TextView.setAbsoluteMagnitude(item: Asteroid?) {
    item?.let {
        text = item.absoluteMagnitude.toString()
    }
}

@BindingAdapter("estimatedDiameter")
fun TextView.setEstimatedDiameter(item: Asteroid?) {
    item?.let {
        text = item.estimatedDiameter.toString()
    }
}

@BindingAdapter("relativeVelocity")
fun TextView.setRelativeVelocity(item: Asteroid?) {
    item?.let {
        text = item.relativeVelocity.toString()
    }
}

@BindingAdapter("distanceFromEarth")
fun TextView.setDistanceFromEarthy(item: Asteroid?) {
    item?.let {
        text = item.distanceFromEarth.toString()
    }
}

//
//@BindingAdapter("asteroidStatusImage")
//fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
//    if (isHazardous) {
//        imageView.setImageResource(R.drawable.asteroid_hazardous)
//    } else {
//        imageView.setImageResource(R.drawable.asteroid_safe)
//    }
//}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

//@BindingAdapter("absoluteMagnitude")
//fun bindAbsoluteMagnitude(textView: TextView, number: Double) {
//    val context = textView.context
//    textView.text = String.format(context.getString(R.string.km_unit_format), number)
//}



@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}
