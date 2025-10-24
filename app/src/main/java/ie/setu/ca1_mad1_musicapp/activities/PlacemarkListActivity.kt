package ie.setu.ca1_mad1_musicapp.activities

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ie.setu.ca1_mad1_musicapp.R
import ie.setu.ca1_mad1_musicapp.databinding.ActivityPlacemarkListBinding
import ie.setu.ca1_mad1_musicapp.databinding.CardPlacemarkBinding
import ie.setu.ca1_mad1_musicapp.main.MainApp
import ie.setu.ca1_mad1_musicapp.models.PlacemarkModel
class PlacemarkListActivity : AppCompatActivity() {

    lateinit var app: MainApp
    private lateinit var binding: ActivityPlacemarkListBinding

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.placemarks.size)
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
        binding.recyclerView.adapter = PlacemarkAdapter(app.placemarks)
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
}

class PlacemarkAdapter constructor(private var placemarks: MutableList<PlacemarkModel> ) :
    RecyclerView.Adapter<PlacemarkAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardPlacemarkBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        //Before this code below would not work with the delete function code, I used AI to help me create code that would function correctly.
        holder.bind(placemarks[position], placemarks, this)
    }

    override fun getItemCount(): Int = placemarks.size



    class MainHolder(
        private val binding : CardPlacemarkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(placemark: PlacemarkModel, placemarks: MutableList<PlacemarkModel>, adapter: PlacemarkAdapter) {
            binding.placemarkTitle.text = placemark.title
            binding.description.text = placemark.description

            // I used AI to help understand and create this code below.
            //For the this bit I used AI to help me create the delete functions that would work with the cardview button
            //I created the delete function in this class as the delete button is in the cardview xml

            //This is the preliminary code where if the button is pressed it will execute the code below
            binding.deletePlacemark.setOnClickListener {
                //This line gets the index position of the placemark in the RecyclerView list
                //So if it is the first placemark the position = 0 and if it is the second placemark the position = 1
                val position = adapterPosition
                //This line checks if the position is valid before trying to delete it with the code below
                if (position != RecyclerView.NO_POSITION) {
                    //This line removes the placemark at the position
                    placemarks.removeAt(position)
                    //This updates the list visually
                    adapter.notifyItemRemoved(position)
                    //This line is optional but essentially it refreshes all the items in the list.
                    adapter.notifyItemRangeChanged(position,placemarks.size)
                    //This line is is essential for persistence
                    //It calls the function savePlacemarks() in MainApp that saves the list to the JSON
                    //This makes the deletion permanent
                    (binding.root.context.applicationContext as MainApp).savePlacemarks()
                }
            }
        }
    }


}
