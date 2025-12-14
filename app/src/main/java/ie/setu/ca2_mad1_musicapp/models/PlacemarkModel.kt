package ie.setu.ca2_mad1_musicapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlacemarkModel(var id: Long = 0,
                          var title: String = "",
                          var description: String = "") : Parcelable

