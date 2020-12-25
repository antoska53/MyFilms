package ru.myacademyhomework.myfilms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.myacademyhomework.myfilms.data.Movie

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
    private var movieId: Int? =null
    private var param2: String? = null
    private var movie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieId = it.getInt(MOVIE_ID)
            param2 = it.getString(ARG_PARAM2)
        }
       movie = MovieRepository.moviesList.first{ it.id == movieId }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view: View = inflater.inflate(R.layout.fragment_movie_details, container, false)
        var ivImageMovie: ImageView = view.findViewById(R.id.main_image)
        var tvMovieDescription: TextView = view.findViewById(R.id.description)
        val tvNameMovie: TextView = view.findViewById(R.id.movieName)
        val tvReview :TextView = view.findViewById(R.id.review)
        val tvGenre :TextView = view.findViewById(R.id.movieGenre)
        val tvMinimumAge :TextView = view.findViewById(R.id.tv_minimum_age)

        Glide.with(context!!)
            .load(movie?.backdrop)
            .into(ivImageMovie)

        tvMovieDescription.setText(movie?.overview)
        tvNameMovie.setText(movie?.title)
        tvReview.setText(movie?.numberOfRatings.toString())
        tvGenre.setText(movie?.genres?.joinToString { genre -> genre.name })
        tvMinimumAge.setText(movie?.minimumAge.toString())

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var recycler: RecyclerView = view.findViewById(R.id.recycler_actor)
        recycler.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        recycler.adapter = ActorAdapter(movie?.actors)
        //recycler.addItemDecoration(MovieItemDecoration(20))

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