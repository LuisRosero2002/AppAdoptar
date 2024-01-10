package com.example.appfirebaselogin


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.appfirebaselogin.fragments.AdoptarFragment
import com.example.appfirebaselogin.fragments.DarAdopcionFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class PrincipalActivity : AppCompatActivity() {

    lateinit var navigation :BottomNavigationView
    private val bottomNavigationView =  BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.btnAdoptar -> {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<AdoptarFragment>(R.id.fragmetContainer)
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.btnDarAdopcion -> {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<DarAdopcionFragment>(R.id.fragmetContainer)
                }
                return@OnNavigationItemSelectedListener true
            }
            else -> false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<AdoptarFragment>(R.id.fragmetContainer)
        }

        navigation = findViewById(R.id.navMenu)
        navigation.setOnNavigationItemSelectedListener(bottomNavigationView)

    }

}