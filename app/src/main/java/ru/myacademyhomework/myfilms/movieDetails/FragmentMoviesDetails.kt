package ru.myacademyhomework.myfilms.movieDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.myacademyhomework.myfilms.R
import ru.myacademyhomework.myfilms.data.Actor

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
    private var viewModel: MovieDetailViewModel? = null
    private var liveActorList: LiveData<List<Actor>>? = null
    private var liveImageMovie: LiveData<String>? = null
    private var liveMovieDescription: LiveData<String>? = null
    private var liveNameMovie: LiveData<String>? = null
    private var liveReview: LiveData<String>? = null
    private var liveGenre: LiveData<String>? = null
    private var liveMinimumAge: LiveData<String>? = null
    private var liveRating: LiveData<Float>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieId = it.getInt(MOVIE_ID)
            param2 = it.getString(ARG_PARAM2)
        }
        viewModel = ViewModelProvider(this).get(MovieDetailViewModel::class.java)
        viewModel?.loadMovie(movieId)
        liveActorList = viewModel?.getLiveActorList()
        liveGenre = viewModel?.getLiveGenre()
        liveImageMovie = viewModel?.getLiveImageMovie()
        liveMovieDescription = viewModel?.getLiveMovieDescription()
        liveNameMovie = viewModel?.getLiveNameMovie()
        liveReview = viewModel?.getLiveReview()
        liveMinimumAge = viewModel?.getLiveMinimumAge()
        liveRating = viewModel?.getLiveRating()
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

        viewModel?.getLiveActorList()?.observe(this.viewLifecycleOwner, Observer<List<Actor>> {
            initRecycler(view)
        })
        viewModel?.getLiveGenre()?.observe(this.viewLifecycleOwner, { tvGenre?.text = it })
        viewModel?.getLiveNameMovie()?.observe(this.viewLifecycleOwner, { tvNameMovie?.text = it })
        viewModel?.getLiveReview()?.observe(this.viewLifecycleOwner, { tvReview?.text = it })
        viewModel?.getLiveMovieDescription()?.observe(this.viewLifecycleOwner, { tvMovieDescription?.text = it })
        viewModel?.getLiveMinimumAge()?.observe(this.viewLifecycleOwner, { tvMinimumAge?.text = it })
        viewModel?.getLiveImageMovie()?.observe(this.viewLifecycleOwner, {
            Glide.with(context!!)
            .load(it)
            .into(ivImageMovie!!)
        })
        viewModel?.getLiveRating()?.observe(this.viewLifecycleOwner, { ratingBar?.rating = it.div(2) ?: 0f })

    }


    private fun initRecycler(view: View){
        var recycler: RecyclerView = view.findViewById(R.id.recycler_actor)
        recycler.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        recycler.adapter = ActorAdapter(liveActorList?.value)
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