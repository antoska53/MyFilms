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

class MainMovieActivity : AppCompatActivity(), FragmentMoviesList.Companion.Listener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null){
            changeFragmentNull(FragmentMoviesList.newInstance("",""))
        }
    }

    fun changeFragment (fragment : Fragment){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fragment.enterTransition = Explode()
            fragment.exitTransition = Explode()
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            //.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .addToBackStack(null)
            .commit()
    }
    fun changeFragmentNull (fragment : Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun itemClicked(fragment: Fragment) {
        changeFragment(fragment)
    }


}