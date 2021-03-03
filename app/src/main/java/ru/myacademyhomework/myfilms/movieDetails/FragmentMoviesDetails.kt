package ru.myacademyhomework.myfilms.movieDetails

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.myacademyhomework.myfilms.BuildConfig
import ru.myacademyhomework.myfilms.R
import ru.myacademyhomework.myfilms.data.Actor
import ru.myacademyhomework.myfilms.data.Movie
import ru.myacademyhomework.myfilms.movie.MovieAdapter
import ru.myacademyhomework.myfilms.movie.MovieViewHolder.Companion.TAG
import ru.myacademyhomework.myfilms.network.ErrorResult
import ru.myacademyhomework.myfilms.network.MovieResult
import ru.myacademyhomework.myfilms.network.SuccessActorResult
import ru.myacademyhomework.myfilms.network.SuccessDetailResult

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val MOVIE_ID = "movie_id"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentMoviesDetails.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentMoviesDetails : Fragment() {
    // TODO: Rename and change types of parameters
    private var movieId: Int = -1
    private var param2: String? = null
    private var ivImageMovie: ImageView? = null
    private var tvMovieDescription: TextView? = null
    private var tvNameMovie: TextView? = null
    private var tvReview :TextView? = null
    private var tvGenre :TextView? = null
    private var tvMinimumAge :TextView? = null
    private var ratingBar :RatingBar? = null
    private var adapter: ActorAdapter? = null
    private var viewModel: MovieDetailViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieId = it.getInt(MOVIE_ID)
            param2 = it.getString(ARG_PARAM2)
        }
        viewModel = ViewModelProvider(this, MovieDetailViewModel.Factory(movieId))
            .get(MovieDetailViewModel::class.java)
//        if(savedInstanceState == null) {
//            viewModel?.loadMovieFromDb(movieId)
//            viewModel?.loadMovie(movieId)
//        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_movie_details, container, false)
        ivImageMovie = view.findViewById(R.id.main_image)
        tvMovieDescription = view.findViewById(R.id.description)
        tvNameMovie = view.findViewById(R.id.movieName)
        tvReview = view.findViewById(R.id.review)
        tvGenre = view.findViewById(R.id.movieGenre)
        tvMinimumAge = view.findViewById(R.id.tv_minimum_age)
        ratingBar = view.findViewById(R.id.rating_bar)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler(view)

//        viewModel?.liveActors?.observe(this.viewLifecycleOwner, Observer<MovieResult> {
//           when(it){
//               is SuccessActorResult -> updateRecycler(it.actors)
//               is ErrorResult -> Toast.makeText(context, "Ошибка при загузке, попробуйте снова", Toast.LENGTH_LONG)
//                   .show()
//           }
//
//        })
//        viewModel?.liveMovie?.observe(this.viewLifecycleOwner, {
//            when(it){
//                is SuccessDetailResult -> updateData(it.movieInfo)
//                is ErrorResult -> Toast.makeText(context, "Ошибка при загузке, попробуйте снова", Toast.LENGTH_LONG)
//                    .show()
//            }
//        })

        viewModel?.movieDetailLiveData?.observe(this.viewLifecycleOwner, {
            updateData(it)
            updateRecycler(it.actors)
            Log.d("ACTORS", "onViewCreated: ${it.actors}")
        })
        }


    private fun initRecycler(view: View){
        var recycler: RecyclerView = view.findViewById(R.id.recycler_actor)
        recycler.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        adapter = ActorAdapter()
        recycler.adapter = adapter
    }

    private fun updateRecycler(listActors: List<Actor>){
        adapter?.updateData(listActors)
    }

    private fun updateData(movie: Movie){
        Glide.with(context!!)
            .load(BuildConfig.BASE_IMAGE_URL + movie.backdrop)
            .into(ivImageMovie!!)
        tvMovieDescription?.text = movie.overview
        tvNameMovie?.text = movie.title
        tvReview?.text = movie.numberOfRatings.toString()
        tvGenre?.text = movie.genres.joinToString { genre -> genre.name }
        tvMinimumAge?.text = movie.minimumAge.toString() + "+"
        ratingBar?.rating = movie.ratings
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentMoviesDetails.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(movieId: Int, param2: String) =
            FragmentMoviesDetails()
                .apply {
                arguments = Bundle().apply {
                    putInt(MOVIE_ID, movieId)

                    putString(ARG_PARAM2, param2)
                }
            }
    }
}