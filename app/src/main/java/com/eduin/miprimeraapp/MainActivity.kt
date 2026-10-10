package com.eduin.miprimeraapp

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*

import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.content.Context

import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {

    private var mapa: MapView? = null

    private lateinit var locationManager:
        LocationManager

    private var marcadorUsuario:
        Marker? = null

    private lateinit var contenido: FrameLayout

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        solicitarUbicacion()

        crearAplicacion()
    }


    private fun crearAplicacion() {

        val root = LinearLayout(this)

        root.orientation =
            LinearLayout.VERTICAL

        root.setBackgroundColor(
            Color.rgb(10, 5, 20)
        )


        contenido = FrameLayout(this)

        root.addView(
            contenido,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                1f
            )
        )


        val navegacion =
            crearNavegacion()

        root.addView(navegacion)


        setContentView(root)


        mostrarInicio()
    }


    // ========================================
    // LANDING
    // ========================================

    private fun mostrarInicio() {

        contenido.removeAllViews()


        val scroll =
            ScrollView(this)


        val layout =
            LinearLayout(this)

        layout.orientation =
            LinearLayout.VERTICAL

        layout.setPadding(
            40,
            70,
            40,
            40
        )


        // LOGO

        val logo =
            TextView(this)

        logo.text =
            "🎃  Rutas de los Dulces"

        logo.textSize = 24f

        logo.setTextColor(
            Color.WHITE
        )

        logo.setTypeface(
            null,
            android.graphics.Typeface.BOLD
        )


        layout.addView(logo)


        espacio(layout, 60)


        // HERO

        val halloween =
            TextView(this)

        halloween.text =
            "🎃  👻  🍬"

        halloween.textSize = 52f

        halloween.gravity =
            Gravity.CENTER


        layout.addView(halloween)


        espacio(layout, 30)


        val titulo =
            TextView(this)

        titulo.text =
            "La aventura más dulce\ncomienza aquí"

        titulo.textSize = 34f

        titulo.gravity =
            Gravity.CENTER

        titulo.setTextColor(
            Color.WHITE
        )

        titulo.setTypeface(
            null,
            android.graphics.Typeface.BOLD
        )


        layout.addView(titulo)


        espacio(layout, 20)


        val descripcion =
            TextView(this)

        descripcion.text =
            "Descubre casas, negocios y lugares que regalan dulces cerca de ti."

        descripcion.textSize = 17f

        descripcion.gravity =
            Gravity.CENTER

        descripcion.setTextColor(
            Color.LTGRAY
        )


        layout.addView(descripcion)


        espacio(layout, 35)


        // BOTÓN

        val botonMapa =
            Button(this)

        botonMapa.text =
            "🗺  Explorar el mapa"

        botonMapa.textSize = 17f

        botonMapa.setTextColor(
            Color.WHITE
        )

        botonMapa.setBackgroundResource(
            R.drawable.button_orange
        )


        botonMapa.setOnClickListener {

            mostrarMapa()
        }


        layout.addView(
            botonMapa,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                150
            )
        )


        espacio(layout, 50)


        // ESTADÍSTICAS

        val estadisticas =
            LinearLayout(this)

        estadisticas.orientation =
            LinearLayout.HORIZONTAL


        estadisticas.addView(
            tarjeta(
                "📍",
                "50+",
                "Lugares"
            )
        )


        estadisticas.addView(
            tarjeta(
                "🍬",
                "120+",
                "Dulces"
            )
        )


        layout.addView(estadisticas)


        espacio(layout, 50)


        // SECCIÓN CERCANOS

        val cerca =
            TextView(this)

        cerca.text =
            "Cerca de ti"

        cerca.textSize = 22f

        cerca.setTextColor(
            Color.WHITE
        )

        cerca.setTypeface(
            null,
            android.graphics.Typeface.BOLD
        )


        layout.addView(cerca)


        espacio(layout, 20)


        layout.addView(
            lugar(
                "🎃",
                "Casa del Terror",
                "500 m"
            )
        )


        espacio(layout, 15)


        layout.addView(
            lugar(
                "👻",
                "Dulces Fantasma",
                "1.2 km"
            )
        )


        scroll.addView(layout)

        contenido.addView(scroll)
    }


    // ========================================
    // TARJETA LIQUID GLASS
    // ========================================

    private fun tarjeta(
        icono: String,
        numero: String,
        texto: String
    ): View {

        val card =
            LinearLayout(this)

        card.orientation =
            LinearLayout.VERTICAL

        card.gravity =
            Gravity.CENTER

        card.setPadding(
            20,
            30,
            20,
            30
        )

        card.setBackgroundResource(
            R.drawable.glass_card
        )


        val icon =
            TextView(this)

        icon.text = icono

        icon.textSize = 30f

        icon.gravity =
            Gravity.CENTER


        val numeroView =
            TextView(this)

        numeroView.text =
            numero

        numeroView.textSize = 24f

        numeroView.gravity =
            Gravity.CENTER

        numeroView.setTextColor(
            Color.WHITE
        )


        val descripcion =
            TextView(this)

        descripcion.text =
            texto

        descripcion.gravity =
            Gravity.CENTER

        descripcion.setTextColor(
            Color.LTGRAY
        )


        card.addView(icon)

        card.addView(numeroView)

        card.addView(descripcion)


        val params =
            LinearLayout.LayoutParams(
                0,
                250,
                1f
            )

        params.setMargins(
            10,
            10,
            10,
            10
        )

        card.layoutParams =
            params


        return card
    }


    // ========================================
    // LUGAR
    // ========================================

    private fun lugar(
        icono: String,
        nombre: String,
        distancia: String
    ): View {

        val card =
            LinearLayout(this)

        card.orientation =
            LinearLayout.HORIZONTAL

        card.gravity =
            Gravity.CENTER_VERTICAL

        card.setPadding(
            30,
            30,
            30,
            30
        )

        card.setBackgroundResource(
            R.drawable.glass_card
        )


        val icon =
            TextView(this)

        icon.text =
            icono

        icon.textSize =
            30f


        val nombreView =
            TextView(this)

        nombreView.text =
            nombre

        nombreView.textSize =
            17f

        nombreView.setTextColor(
            Color.WHITE
        )


        val distanciaView =
            TextView(this)

        distanciaView.text =
            distancia

        distanciaView.setTextColor(
            Color.rgb(
                255,
                120,
                0
            )
        )


        card.addView(icon)


        card.addView(
            nombreView,
            LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
        )


        card.addView(
            distanciaView
        )


        return card
    }


    // ========================================
    // NAVEGACIÓN
    // ========================================

    private fun crearNavegacion(): View {

        val nav =
            LinearLayout(this)

        nav.orientation =
            LinearLayout.HORIZONTAL

        nav.gravity =
            Gravity.CENTER

        nav.setPadding(
            10,
            20,
            10,
            25
        )

        nav.setBackgroundColor(
            Color.rgb(
                18,
                10,
                30
            )
        )


        nav.addView(
            botonNav(
                "🏠\nInicio"
            ) {

                mostrarInicio()
            }
        )


        nav.addView(
            botonNav(
                "🗺\nMapa"
            ) {

                mostrarMapa()
            }
        )


        nav.addView(
            botonNav(
                "👤\nPerfil"
            ) {

                Toast.makeText(
                    this,
                    "Perfil próximamente",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )


        return nav
    }


    private fun botonNav(
        texto: String,
        accion: () -> Unit
    ): Button {

        val boton =
            Button(this)

        boton.text =
            texto

        boton.setTextColor(
            Color.WHITE
        )

        boton.setBackgroundColor(
            Color.TRANSPARENT
        )

        boton.setOnClickListener {

            accion()
        }


        boton.layoutParams =
            LinearLayout.LayoutParams(
                0,
                140,
                1f
            )


        return boton
    }


    // ========================================
    // MAPA
    // ========================================


    private fun mostrarMapa() {

        contenido.removeAllViews()

        Configuration.getInstance().userAgentValue =
            packageName

        mapa = MapView(this)

        mapa?.setTileSource(
            TileSourceFactory.MAPNIK
        )

        mapa?.setMultiTouchControls(true)

        mapa?.controller?.setZoom(17.0)

        contenido.addView(
            mapa,
            FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
        )

        obtenerUbicacion()
    }

    private fun obtenerUbicacion() {

        if (
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                100
            )

            return
        }


        locationManager =
            getSystemService(
                Context.LOCATION_SERVICE
            ) as LocationManager


        val listener =
            object : LocationListener {

                override fun onLocationChanged(
                    location: Location
                ) {

                    mostrarMiUbicacion(
                        location.latitude,
                        location.longitude
                    )
                }
            }


        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            2000L,
            2f,
            listener
        )


        val ultimaUbicacion =
            locationManager.getLastKnownLocation(
                LocationManager.GPS_PROVIDER
            )


        ultimaUbicacion?.let {

            mostrarMiUbicacion(
                it.latitude,
                it.longitude
            )
        }
    }

    private fun mostrarMiUbicacion(
        latitud: Double,
        longitud: Double
        ) {

        val punto =
            GeoPoint(
                latitud,
                longitud
            )


        mapa?.controller?.animateTo(
            punto
        )


        if (marcadorUsuario == null) {

            marcadorUsuario =
                Marker(mapa)

            marcadorUsuario?.title =
                "📍 Mi ubicación"

            marcadorUsuario?.setAnchor(
                Marker.ANCHOR_CENTER,
                Marker.ANCHOR_BOTTOM
            )

            mapa?.overlays?.add(
                marcadorUsuario
            )
        }


        marcadorUsuario?.position =
            punto


        mapa?.invalidate()
    }

    // ========================================
    // GPS
    // ========================================

    private fun solicitarUbicacion() {

        if (
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            !=
            PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                100
            )
        }
    }


    // ========================================
    // UTILIDAD
    // ========================================

    private fun espacio(
        layout: LinearLayout,
        alto: Int
    ) {

        val espacio =
            Space(this)

        espacio.layoutParams =
            LinearLayout.LayoutParams(
                1,
                alto
            )

        layout.addView(
            espacio
        )
    }
}