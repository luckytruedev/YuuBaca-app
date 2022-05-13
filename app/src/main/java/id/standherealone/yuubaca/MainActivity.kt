package id.standherealone.yuubaca

import android.app.AlertDialog
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.standherealone.yuubaca.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_category, R.id.navigation_favorite, R.id.navigation_setting
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // if app detection no connection network
        if (!isConnect()) {
            AlertDialog.Builder(this)
                .setTitle(R.string.cek_jaringan)
                .setMessage(R.string.isi_tidakkoneksi)
                .setCancelable(false)
                .setPositiveButton("OK"
                ) { _: DialogInterface?, _: Int -> this.finish() }
                .show()
        }
    }

    @Suppress("DEPRECATION")
    fun isConnect(): Boolean {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = Objects.requireNonNull(cm).activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }

    var exitTime: Long = 0
    fun doExitApp() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(this, R.string.press_again_exit_app, Toast.LENGTH_SHORT).show()
            exitTime = System.currentTimeMillis()
        } else {
            finish()
        }
    }

    override fun onBackPressed() {
        doExitApp()
    }
}