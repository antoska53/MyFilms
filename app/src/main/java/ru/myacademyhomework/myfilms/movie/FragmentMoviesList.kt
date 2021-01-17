package ru.myacademyhomework.myfilms.movie

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.myacademyhomework.myfilms.R
import ru.myacademyhomework.myfilms.RetrofitModule
import ru.myacademyhomework.myfilms.data.Movie
import ru.myacademyhomework.myfilms.movie.MovieViewHolder.Companion.TAG

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentMoviesList.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentMoviesList : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: Listener? = null
    private var adapter: MovieAdapter? = null
    private var viewModel: MovieViewModel? = null
    private var liveData: LiveData<List<Movie>>? = null
    private var recycler: RecyclerView? =null
    private val movieApi = RetrofitModule.movieApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        liveData = viewModel?.getData()
        
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler(view)

        viewModel?.liveData?.observe(this.viewLifecycleOwner, Observer<List<Movie>> {
            updateRecycler(it)
        })
    }

    private fun initRecycler(view: View) {
        recycler = view.findViewById(R.id.recycler_movie)
        recycler?.layoutManager = GridLayoutManager(view.context, 2)
        adapter = MovieAdapter(listener)
        recycler?.adapter = adapter//MovieAdapter(listener) //, liveData?.value)
        val dividerItemDecoration: MovieItemDecoration = MovieItemDecoration(20)
        recycler?.addItemDecoration(dividerItemDecoration)
    }

    private fun updateRecycler(list: List<Movie>){
        adapter?.updateData(list)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as Listener
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recycler = null
        adapter = null
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentMoviesList.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentMoviesList().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

        interface Listener {
            fun itemClicked(fragment: Fragment)
        }
    }
}