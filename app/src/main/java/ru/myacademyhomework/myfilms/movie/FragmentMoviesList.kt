package ru.myacademyhomework.myfilms.movie

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Explode
import ru.myacademyhomework.myfilms.*
import ru.myacademyhomework.myfilms.data.Movie
import ru.myacademyhomework.myfilms.movie.MovieViewHolder.Companion.TAG
import ru.myacademyhomework.myfilms.network.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentMoviesList.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentMoviesList : Fragment(R.layout.fragment_movies_list), MovieListListener {
    // TODO: Rename and change types of parameters
    private var adapter: MovieAdapter? = null
    private var recycler: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    private val viewModel: MovieViewModel by viewModels()
    private val offset = 20
    private val spanCountVertical = 2
    private val spanCountHorizontal = 4


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.getDataFromDb()
            viewModel.getData()
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler(view)
        progressBar = view.findViewById(R.id.progress_load)

        viewModel.liveData.observe(this.viewLifecycleOwner, Observer<MovieResult> {
            updateRecycler(it)
        })
        viewModel.loadState.observe(this.viewLifecycleOwner, {
            progressLoading(it)
        })
    }

    private fun initRecycler(view: View) {
        recycler = view.findViewById(R.id.recycler_movie)
        recycler?.layoutManager =
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                GridLayoutManager(view.context, spanCountVertical)
            else GridLayoutManager(view.context, spanCountHorizontal)
        adapter = MovieAdapter(this)
        recycler?.adapter = adapter
        val dividerItemDecoration: MovieItemDecoration = MovieItemDecoration(offset)
        recycler?.addItemDecoration(dividerItemDecoration)
    }

    private fun updateRecycler(result: MovieResult) {
        when (result) {
            is SuccessResult -> {
                adapter?.let { movieAdapter ->
                    val movieDiffUtil = MovieDiffUtil(movieAdapter.listMovies, result.listMovies)
                    val diffResult = DiffUtil.calculateDiff(movieDiffUtil)
                    movieAdapter.updateData(result.listMovies)
                    diffResult.dispatchUpdatesTo(movieAdapter)
                }
            }
            is ErrorResult -> {
                adapter?.updateData(emptyList())
                Toast.makeText(context, getString(R.string.internet_error), Toast.LENGTH_LONG)
                    .show()
                Log.d(TAG, "updateRecycler: ${result.e}")
            }
            is TerminalError -> {
                adapter?.updateData(emptyList())
                Toast.makeText(context, getString(R.string.terminal_error), Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun progressLoading(loadState: LoadState) {
        when (loadState) {
            is Loading -> {
                recycler?.visibility = View.GONE
                progressBar?.visibility = View.VISIBLE
            }
            is Ready -> {
                recycler?.visibility = View.VISIBLE
                progressBar?.visibility = View.GONE
            }
        }
    }

    override fun itemClicked(fragment: Fragment) {
        changeFragment(fragment)
    }

    private fun changeFragment(fragment: Fragment) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fragment.enterTransition = Explode()
            fragment.exitTransition = Explode()
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            //.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .addToBackStack(null)
            .commit()
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
            FragmentMoviesList()
    }
}