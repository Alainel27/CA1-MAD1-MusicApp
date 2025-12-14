package ie.setu.ca2_mad1_musicapp.models

interface PlacemarkStore {
    fun findAll(): List<PlacemarkModel>
    fun create(placemark: PlacemarkModel)

    fun update(placemark: PlacemarkModel)
}