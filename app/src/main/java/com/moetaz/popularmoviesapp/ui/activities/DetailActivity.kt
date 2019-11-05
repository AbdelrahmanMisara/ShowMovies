package com.moetaz.popularmoviesapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.moetaz.popularmoviesapp.R
import com.moetaz.popularmoviesapp.ui.fragments.DetailFragment

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fdetail , DetailFragment()).commit()
        }
    }
}
