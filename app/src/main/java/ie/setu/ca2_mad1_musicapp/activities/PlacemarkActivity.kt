package ie.setu.ca2_mad1_musicapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import ie.setu.ca2_mad1_musicapp.R
import ie.setu.ca2_mad1_musicapp.databinding.ActivityPlacemarkBinding
import ie.setu.ca2_mad1_musicapp.main.MainApp
import ie.setu.ca2_mad1_musicapp.models.PlacemarkModel
import timber.log.Timber.i

class PlacemarkActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlacemarkBinding
    var placemark = PlacemarkModel()
    lateinit var app: MainApp




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlacemarkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        if (intent.hasExtra("placemark_edit")) {
            placemark = intent.extras?.getParcelable("placemark_edit")!!
            binding.placemarkTitle.setText(placemark.title)
            binding.description.setText(placemark.description)
        }

        i("Placemark Activity started...")

        binding.btnAdd.setOnClickListener() {
            placemark.title = binding.placemarkTitle.text.toString()
            placemark.description = binding.description.text.toString()
            if (placemark.title.isNotEmpty()) {
                app.placemarks.create(placemark.copy())

                //persistence so it saves the inputted data, called from the MainApp savePlacemarks() function previously created
                app.savePlacemarks()

                i("add Button Pressed: ${placemark}")
                val list = app.placemarks.findAll()

                for (i in list.indices) {
                    i("Placemark[$i]:${list[i]}")
                }
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar.make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }




    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_placemark, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }







}

