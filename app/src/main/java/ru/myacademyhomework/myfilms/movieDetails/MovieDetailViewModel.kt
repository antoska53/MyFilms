package ru.myacademyhomework.myfilms.movieDetails

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.myacademyhomework.myfilms.BuildConfig
import ru.myacademyhomework.myfilms.data.Actor
import ru.myacademyhomework.myfilms.data.ActorsInfo
import ru.myacademyhomework.myfilms.movie.MovieInfo
import ru.myacademyhomework.myfilms.network.RetrofitModule

class MovieDetailViewModel: ViewModel() {
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
           val movieInfo: MovieInfo
           val actors: ActorsInfo
            withContext(Dispatchers.IO){
                movieInfo = RetrofitModule.movieApi.getMovieInfo(id, BuildConfig.API_KEY)
                actors = RetrofitModule.movieApi.getActors(id, BuildConfig.API_KEY)
            }
           bind(movieInfo, actors)
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



    private fun bind(movieInfo: MovieInfo, actors: ActorsInfo){
        liveActorList.value = actors.cast
        liveGenre.value = movieInfo.genres.joinToString { genre -> genre.name }
        liveImageMovie.value = movieInfo.backdropPath
        liveMinimumAge.value = if(movieInfo.adult) "16+" else "13+"
        liveNameMovie.value = movieInfo.title
        liveMovieDescription.value = movieInfo.overview
        liveRating.value = movieInfo.voteAverage
        liveReview.value = movieInfo.voteCount.toString()
    }


}