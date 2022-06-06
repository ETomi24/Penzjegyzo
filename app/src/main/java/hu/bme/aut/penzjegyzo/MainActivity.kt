package hu.bme.aut.penzjegyzo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import hu.bme.aut.penzjegyzo.databinding.ActivityMainBinding
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import hu.bme.aut.penzjegyzo.data.Bejegyzes
import hu.bme.aut.penzjegyzo.fragments.NewBejegyzesItemDialogFragment
import hu.bme.aut.penzjegyzo.fragments.ValtoFragment

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.bottomNavigation

        val navController = findNavController(R.id.fragment_container)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_egyenleg, R.id.navigation_lista, R.id.navigation_valto
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }
}