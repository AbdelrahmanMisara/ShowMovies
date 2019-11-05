package com.moetaz.popularmoviesapp.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.moetaz.popularmoviesapp.R
import com.moetaz.popularmoviesapp.ui.fragments.FavouriteFragment
import com.moetaz.popularmoviesapp.ui.fragments.PopularMoviesFragment
import com.moetaz.popularmoviesapp.ui.fragments.TopMoviesFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity()  {


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.navigation_blog -> {
                val fragment = PopularMoviesFragment()

                supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_chapter -> {
                val fragment = TopMoviesFragment()

                supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_store -> {
                val fragment = FavouriteFragment()

                supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        if (savedInstanceState == null) {
            val fragment = PopularMoviesFragment()

            supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                .commit()
        }
        setSupportActionBar(toolbar)

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


    }

}
