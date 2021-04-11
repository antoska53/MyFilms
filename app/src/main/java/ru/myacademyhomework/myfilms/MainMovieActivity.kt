package ru.myacademyhomework.myfilms

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.transition.Explode
import androidx.transition.Fade
import androidx.transition.Slide
import ru.myacademyhomework.myfilms.movie.FragmentMoviesList
import ru.myacademyhomework.myfilms.movieDetails.FragmentMoviesDetails

class MainMovieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FragmentMoviesList.newInstance())
                .commitNow()
            intent.let(::handleIntent)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if(intent != null) {
            handleIntent(intent)
        }
    }

    private fun handleIntent(intent: Intent){
        when (intent.action) {
            Intent.ACTION_VIEW -> {
                val id = intent.data?.lastPathSegment?.toIntOrNull()
                if (id != null) {
                    supportFragmentManager.popBackStack(
                        FragmentMoviesList.DETAILS_MOVIE,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                    supportFragmentManager.commit {
                        addToBackStack(FragmentMoviesList.DETAILS_MOVIE)
                        replace(R.id.fragment_container, FragmentMoviesDetails.newInstance(id))
                    }
                }
            }
        }
    }
}