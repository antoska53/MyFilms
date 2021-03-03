package ru.myacademyhomework.myfilms

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.transition.Explode
import androidx.transition.Fade
import androidx.transition.Slide
import ru.myacademyhomework.myfilms.movie.FragmentMoviesList

class MainMovieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FragmentMoviesList.newInstance("", ""))
                .commit()
        }
    }
}