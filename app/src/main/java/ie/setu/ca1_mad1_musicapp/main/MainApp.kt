package ie.setu.ca1_mad1_musicapp.main


import android.app.Application
import ie.setu.ca1_mad1_musicapp.models.PlacemarkModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val placemarks = ArrayList<PlacemarkModel>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Placemark started")

    }
}
