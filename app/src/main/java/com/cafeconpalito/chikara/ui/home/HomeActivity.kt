package com.cafeconpalito.chikara.ui.home

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.cafeconpalito.chikara.R
import com.cafeconpalito.chikara.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {


    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()

    }

    private fun initUI() {

        initNavigation()

        //TODO FUNCIONA REGULAR.
        setupFocusListener()

        //TODO PRUEBO CON LA VARIANTE DE TECLADO, no termina de funcionar!
        //setupKeyboardListener()
    }

    private fun initNavigation() {

        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHost.navController
        binding.navBar.setupWithNavController(navController)

    }

    private fun setupFocusListener() {
        val rootView = findViewById<View>(android.R.id.content)
        //TODO MODIFICAR COMPORTAMIENTO
        rootView.viewTreeObserver.addOnGlobalFocusChangeListener { oldFocus, newFocus ->
            if (newFocus is EditText) {
                showNavBar(false)
            } else {
                showNavBar(true)
            }
        }
    }


    private fun setupKeyboardListener() {
        val rootView = findViewById<View>(android.R.id.content)
        rootView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val rect = Rect()
                rootView.getWindowVisibleDisplayFrame(rect)
                val screenHeight = rootView.height
                val visibleHeight = rect.bottom

                // El teclado está visible si la parte visible de la pantalla es menor que la altura de la pantalla total
                val keyboardVisible = screenHeight - visibleHeight > screenHeight * 0.15

                showNavBar(!keyboardVisible)
            }
        })
    }

    /**
     * NO LOGRO QUE FUNCIONE CORRECTAMENTE
     */
    private fun setupKeyboardListenerThreshold() {
        val rootView = findViewById<View>(android.R.id.content)
        rootView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            private val threshold = 100 // Umbral en píxeles para detectar el teclado

            override fun onGlobalLayout() {
                val rect = Rect()
                rootView.getWindowVisibleDisplayFrame(rect)
                val screenHeight = rootView.height
                val keypadHeight = screenHeight - rect.bottom

                if (keypadHeight > threshold) {
                    // El teclado está visible
                    showNavBar(false)
                } else {
                    // El teclado está oculto
                    showNavBar(true)
                }
            }
        })
    }


    /**
     * NO BORRAR!
     */
    private fun showNavBar(show: Boolean) {
        binding.navBar.visibility = if (show) View.VISIBLE else View.GONE
    }


    /**
     * NO BORRAR!
     */
    override fun onDestroy() {
        super.onDestroy()
        val rootView = findViewById<View>(android.R.id.content)
        rootView.viewTreeObserver.removeOnGlobalFocusChangeListener { oldFocus, newFocus ->
            // Este es un cierre que no hace nada, solo es necesario para eliminar el listener.
        }
    }


}