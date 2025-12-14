package ie.setu.ca2_mad1_musicapp.models

import timber.log.Timber.i


var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class PlacemarkMemStore : PlacemarkStore{
    val placemarks = ArrayList<PlacemarkModel>()


    override fun findAll(): List<PlacemarkModel> {
        return placemarks
    }

    override fun create(placemark: PlacemarkModel) {
        placemarks.add(placemark)
        logAll()
    }

    override fun update(placemark: PlacemarkModel) {
        var foundPlacemark: PlacemarkModel? = placemarks.find { p -> p.id == placemark.id }
        if (foundPlacemark != null) {
            foundPlacemark.title = placemark.title
            foundPlacemark.description = placemark.description
            logAll()
        }
    }

    fun logAll() {
        placemarks.forEach{ i("$it") }
    }

    fun clear() {
        placemarks.clear()
    }

    fun addAll(list: List<PlacemarkModel>) {
        placemarks.addAll((list))
    }


}