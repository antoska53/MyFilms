package ru.myacademyhomework.myfilms.movie

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Explode
import androidx.work.WorkInfo
import com.bumptech.glide.Glide
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.ListPreloader.PreloadSizeProvider
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.bumptech.glide.util.FixedPreloadSizeProvider
import com.bumptech.glide.util.ViewPreloadSizeProvider
import ru.myacademyhomework.myfilms.*
import ru.myacademyhomework.myfilms.data.Movie
import ru.myacademyhomework.myfilms.movieDetails.FragmentMoviesDetails
import ru.myacademyhomework.myfilms.network.*
import java.text.SimpleDateFormat
import java.util.*
import androidx.recyclerview.widget.GridLayoutManager as GridLayoutManager


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
    private val imageWidthPixels = 342;
    private val imageHeightPixels = 513;
    private var textViewMovieList: TextView? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler(view)
        progressBar = view.findViewById(R.id.progress_load)
        textViewMovieList = view.findViewById(R.id.tv_movie_list)

        viewModel.loadState.observe(this.viewLifecycleOwner, {
            progressLoading(it)
        })

        viewModel.allMovies.observe(this.viewLifecycleOwner, { listMovie ->
            if (listMovie.isEmpty()) {
                progressLoading(Loading())
            } else {
                updateRecycler(SuccessResult(listMovie))
            }
        })

        viewModel.statusLoad.observe(this.viewLifecycleOwner, {
            when (it.state) {
                WorkInfo.State.FAILED -> {
                    // updateRecycler(ErrorResult(it.outputData.keyValueMap[DataBaseUpdateService.UPDATE_ERROR].toString()))
                    progressLoading(Ready())
                }
                WorkInfo.State.SUCCEEDED -> {
                    Toast.makeText(context, "Список фильмов обновлён", Toast.LENGTH_SHORT).show()
                    progressLoading(Ready())
                }
            }
        })
    }

    private fun initRecycler(view: View) {
        recycler = view.findViewById(R.id.recycler_movie)
//        recycler?.setItemViewCacheSize(8)
        recycler?.setHasFixedSize(true)
        recycler?.layoutManager =
            when (resources.configuration.orientation) {
                Configuration.ORIENTATION_PORTRAIT ->
                    GridLayoutManager(view.context, spanCountVertical).apply {
                        initialPrefetchItemCount = 2
                    }
                else ->
                    GridLayoutManager(view.context, spanCountHorizontal).apply {
                        initialPrefetchItemCount = 4
                    }
            }
//        val sizeProvider = FixedPreloadSizeProvider<Movie>(imageWidthPixels, imageHeightPixels)
        val sizeProvider = ViewPreloadSizeProvider<Movie>()
        adapter = MovieAdapter(this, sizeProvider)

        val preloader = RecyclerViewPreloader<Movie>(Glide.with(this), adapter!!, sizeProvider, 12)
        recycler?.addOnScrollListener(preloader)
        recycler?.adapter = adapter
        val dividerItemDecoration: MovieItemDecoration = MovieItemDecoration(offset)
        recycler?.addItemDecoration(dividerItemDecoration)

    }

    private fun updateRecycler(result: MovieResult) {
        when (result) {
            is SuccessResult -> {
                adapter?.let {
//                    val movieListUpdateCallback = MovieListUpdateCallback(it)
                    val movieDiffUtil = MovieDiffUtil(it.listMovies, result.listMovies)
                    val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(movieDiffUtil)
                    val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z")
                    val currentDateAndTime: String = simpleDateFormat.format(Date())
                    textViewMovieList?.text = "Обновлено в $currentDateAndTime"
                    it.updateData(result.listMovies)
                    diffResult.dispatchUpdatesTo(it)
//                     diffResult.dispatchUpdatesTo(movieListUpdateCallback)
                    //recycler?.smoothScrollToPosition(movieListUpdateCallback.firstInsert);
                }
            }
            is ErrorResult -> {
                adapter?.updateData(emptyList())
                Toast.makeText(context, getString(R.string.internet_error), Toast.LENGTH_LONG)
                    .show()
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

    override fun itemClicked(id: Int) {
        changeFragment(FragmentMoviesDetails.newInstance(id))
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
         * @return A new instance of fragment FragmentMoviesList.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            FragmentMoviesList()
    }
}