package hu.bme.aut.penzjegyzo.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.bme.aut.penzjegyzo.data.BejegyzesDatabase
import hu.bme.aut.penzjegyzo.databinding.FragmentHomeBinding
import hu.bme.aut.penzjegyzo.runOnUiThread
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread

class HomeFragment :Fragment(){

    private lateinit var binding: FragmentHomeBinding
    private lateinit var database: BejegyzesDatabase
    private var sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = activity?.let { BejegyzesDatabase.getDatabase(it.applicationContext) }!!

        thread {
            val osszeg =  database.BejegyzesDao().getSumBevetel() - database.BejegyzesDao().getSumKiadas()
            val maibejegyzesek = database.BejegyzesDao().getTodayCount(sdf.format(Date()))
            val bejegyzesek = database.BejegyzesDao().getCount()
            runOnUiThread {
                binding.tvMai.text = "Mai bejegyzések : $maibejegyzesek"
                binding.tvOszzes.text = "Összes bejegyzés: $bejegyzesek"
                binding.tvSummary.setTextColor(Color.GREEN)
                if(osszeg < 0){
                    binding.tvSummary.setTextColor(Color.RED)
                }
                binding.tvSummary.text = "$osszeg Ft"

            }
        }

    }

}