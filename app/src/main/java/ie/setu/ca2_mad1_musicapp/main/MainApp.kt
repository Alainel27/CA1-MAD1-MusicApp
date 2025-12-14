package ie.setu.ca2_mad1_musicapp.main


import android.app.Application
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ie.setu.ca2_mad1_musicapp.models.PlacemarkMemStore
import ie.setu.ca2_mad1_musicapp.models.PlacemarkModel
import timber.log.Timber
import timber.log.Timber.i
import java.io.File

class MainApp : Application() {
    val placemarks = PlacemarkMemStore()


    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        //loads the placemarks saved
        loadPlacemarks()
        i("Placemark started")

    }

    //I used AI to help create this code involving the JSON persistence for the two functions
    //All the code below is created with the help of AI
    //The json gets stored in the device apps storage
    //How to access to
    //Go to View -> Tools windows -> Device Explorer -> Chose the emulator
    //In the Files navigate to data -> data -> ie.setu.ca2_mad1_musicapp -> files -> placemarks.json

    //Declares the function kotlin function as savePlacemarks()
    fun savePlacemarks() {
        //Gson is the library from Google for converting between Kotlin/Java and JSON and .toJson(placemarks) takes the list of placemarks abd converts it to a JSON string
        val jsonString = Gson().toJson(placemarks)
        //points to the apps internal Storage directory
        //creates the file named placemarks.json inside that directory, and .writeText writes the JSON string to that file
        File(filesDir, "placemarks.json").writeText(jsonString)
    }

    //All the code below is created with the help of AI
    fun loadPlacemarks() {
        //This line points to the file that was saved earlier
        val file = File(filesDir, "placemarks.json")
        //This line checks if the file exists, if any files were created before to be loaded
        if(file.exists()) {
            //This line creates a variable that represents the list, It is important because it without it wont be able to convert the GSON object into the placemarkModel and to put it in a array list
            val type = object : TypeToken<ArrayList<PlacemarkModel>>() {}.type
            //This line reads all the JSON texts from the file and converts it into it back into the kotlin list
            val savedPlacemarks: ArrayList<PlacemarkModel> = Gson().fromJson(file.readText(), type)
            //Removes anything already in the memory so no duplicate data is formed
            placemarks.clear()
            //Updates the placemarks loaded from the JSON
            placemarks.addAll(savedPlacemarks)
        }
    }

}
