package ru.myacademyhomework.myfilms

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

class MainMovieActivity : AppCompatActivity(), FragmentMoviesList.Companion.Listener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null){
            changeFragment(FragmentMoviesList.newInstance("",""))
        }
    }

    fun changeFragment (fragment : Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .addToBackStack(null)
            .commit()
    }

    override fun itemClicked(fragment: Fragment) {
        changeFragment(fragment)
    }


}