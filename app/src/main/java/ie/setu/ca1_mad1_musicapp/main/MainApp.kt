package ie.setu.ca1_mad1_musicapp.main


import android.app.Application
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ie.setu.ca1_mad1_musicapp.models.PlacemarkModel
import timber.log.Timber
import timber.log.Timber.i
import java.io.File

class MainApp : Application() {
    val placemarks = ArrayList<PlacemarkModel>()


    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        loadPlacemarks()
        i("Placemark started")

    }

    //I used AI to generate this code involving the JSON persistence

    fun savePlacemarks() {
        val jsonString = Gson().toJson(placemarks)
        File(filesDir, "placemarks.json").writeText(jsonString)
    }

    fun loadPlacemarks() {
        val file = File(filesDir, "placemarks.json")
        if(file.exists()) {
            val type = object : TypeToken<ArrayList<PlacemarkModel>>() {}.type
            val savedPlacemarks: ArrayList<PlacemarkModel> = Gson().fromJson(file.readText(), type)
            placemarks.clear()
            placemarks.addAll(savedPlacemarks)
        }
    }

}
