package ru.myacademyhomework.myfilms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val NAME_MOVIE = "nameMovie"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentMoviesDetails.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentMoviesDetails : Fragment() {
    // TODO: Rename and change types of parameters
    private var nameMovie: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            nameMovie = it.getString(NAME_MOVIE)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view: View = inflater.inflate(R.layout.fragment_movie_details, container, false)
        var ivImageMovie: ImageView = view.findViewById(R.id.main_image)
        var tvMovieDescription: TextView = view.findViewById(R.id.description)

        when (nameMovie) {
            "avengers" -> {
                ivImageMovie.setImageResource(R.drawable.avengers)
                tvMovieDescription.setText(R.string.description_avengers)
            }
            "tenet" -> {
                ivImageMovie.setImageResource(R.drawable.tenet_movie)
                tvMovieDescription.setText(R.string.description_tenet)
            }
            "black widow" -> {
                ivImageMovie.setImageResource(R.drawable.black_widow_movie)
                tvMovieDescription.setText(R.string.description_black_widow)
            }
            "wonder woman" -> {
                ivImageMovie.setImageResource(R.drawable.wonder_woman_movie)
                tvMovieDescription.setText(R.string.description_wonder_woman)
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var recycler: RecyclerView = view.findViewById(R.id.recycler_actor)
        recycler.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        recycler.adapter = ActorAdapter()
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
        fun newInstance(param1: String, param2: String) =
            FragmentMoviesDetails().apply {
                arguments = bundleOf(
                    NAME_MOVIE to param1,
                    ARG_PARAM2 to param2
                )
            }
    }
}