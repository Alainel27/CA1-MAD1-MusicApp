package ie.setu.ca2_mad1_musicapp.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ie.setu.ca2_mad1_musicapp.R
import ie.setu.ca2_mad1_musicapp.adapters.PlacemarkAdapter
import ie.setu.ca2_mad1_musicapp.databinding.ActivityPlacemarkListBinding
import ie.setu.ca2_mad1_musicapp.databinding.CardPlacemarkBinding
import ie.setu.ca2_mad1_musicapp.main.MainApp
import ie.setu.ca2_mad1_musicapp.models.PlacemarkModel
class PlacemarkListActivity : AppCompatActivity(), PlacemarkAdapter.PlacemarkListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityPlacemarkListBinding

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.placemarks.findAll().size)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlacemarkListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = PlacemarkAdapter.PlacemarkAdapter(ArrayList(app.placemarks.findAll()),this)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, PlacemarkActivity::class.java)
                getResult.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPlacemarkClick(placemark: PlacemarkModel) {
        val launcherIntent = Intent(this, PlacemarkActivity::class.java)
        launcherIntent.putExtra("placemark_edit", placemark)
        getClickResult.launch(launcherIntent)
    }


    private val getClickResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.placemarks.findAll().size)
            }
        }



}
