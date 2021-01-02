package ru.myacademyhomework.myfilms.movieDetails

import androidx.lifecycle.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.myacademyhomework.myfilms.MyApplication
import ru.myacademyhomework.myfilms.data.Actor
import ru.myacademyhomework.myfilms.data.Movie
import ru.myacademyhomework.myfilms.data.loadMovies

class MovieDetailViewModel: ViewModel() {
    private var movie: Movie? = null
    private val liveActorList =  MutableLiveData<List<Actor>>()
    private val liveImageMovie = MutableLiveData<String>()
    private val liveMovieDescription = MutableLiveData<String>()
    private val liveNameMovie = MutableLiveData<String>()
    private val liveReview = MutableLiveData<String>()
    private val liveGenre = MutableLiveData<String>()
    private val liveMinimumAge = MutableLiveData<String>()
    private val liveRating = MutableLiveData<Float>()

    fun loadMovie(id: Int){
       viewModelScope.launch {
            movie = loadMovies(MyApplication.getInstance()).first { movie -> movie.id == id }
            bind()
        }

    }

    fun getLiveActorList(): LiveData<List<Actor>>{
        return liveActorList
    }

    fun getLiveImageMovie(): LiveData<String>{
        return liveImageMovie
    }

    fun getLiveMovieDescription(): LiveData<String>{
        return liveMovieDescription
    }

    fun getLiveNameMovie(): LiveData<String>{
        return liveNameMovie
    }

    fun getLiveReview(): LiveData<String>{
        return liveReview
    }

    fun getLiveGenre(): LiveData<String>{
        return liveGenre
    }

    fun getLiveMinimumAge(): LiveData<String>{
        return liveMinimumAge
    }

    fun getLiveRating(): LiveData<Float>{
        return liveRating
    }



    private fun bind(){
        liveActorList.value = movie?.actors
        liveGenre.value = movie?.genres?.joinToString { genre -> genre.name }
        liveImageMovie.value = movie?.backdrop
        liveMinimumAge.value = movie?.minimumAge.toString()
        liveNameMovie.value = movie?.title
        liveMovieDescription.value = movie?.overview
        liveRating.value = movie?.ratings
        liveReview.value = movie?.numberOfRatings.toString()
    }


}