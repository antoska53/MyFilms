package ru.myacademyhomework.myfilms.movie

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.util.TimeUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.WorkInfo
import androidx.work.WorkManager
import ru.myacademyhomework.myfilms.*
import ru.myacademyhomework.myfilms.data.Movie
import ru.myacademyhomework.myfilms.movie.MovieViewHolder.Companion.TAG
import ru.myacademyhomework.myfilms.network.*
import ru.myacademyhomework.myfilms.service.DataBaseUpdateService
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

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
    private var recycler: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    private var textViewMovieList: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)


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
        progressBar = view.findViewById(R.id.progress_load)
        textViewMovieList = view.findViewById(R.id.tv_movie_list)

//        viewModel?.liveData?.observe(this.viewLifecycleOwner, Observer<MovieResult> {
//            updateRecycler(it)
//        })
        viewModel?.loadState?.observe(this.viewLifecycleOwner, {
            progressLoading(it)
        })

        viewModel?.allMovies?.observe(this.viewLifecycleOwner, {
            Log.d(TAG, "onViewCreated: ALL MOVIES - $it")
            Toast.makeText(context, "Список фильмов обновлён", Toast.LENGTH_SHORT).show()
            updateRecycler(SuccessResult(it))
        })

        viewModel?.statusLoad?.observe(this.viewLifecycleOwner, {
            when (it.state) {
                    WorkInfo.State.FAILED -> {
                       // updateRecycler(ErrorResult(it.outputData.keyValueMap[DataBaseUpdateService.UPDATE_ERROR].toString()))
                        progressLoading(Ready())
                    }
                    WorkInfo.State.SUCCEEDED -> {
                        progressLoading(Ready())
                    }
                }
        })
    }

    private fun initRecycler(view: View) {
        recycler = view.findViewById(R.id.recycler_movie)
        recycler?.layoutManager =
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                GridLayoutManager(view.context, 2)
            else GridLayoutManager(view.context, 4)
        adapter = MovieAdapter(listener)
        recycler?.adapter = adapter
        val dividerItemDecoration: MovieItemDecoration = MovieItemDecoration(20)
        recycler?.addItemDecoration(dividerItemDecoration)
    }

    private fun updateRecycler(result: MovieResult) {
        when (result) {
            is SuccessResult -> {
                adapter?.let {
//                    val movieListUpdateCallback = MovieListUpdateCallback(it)
//                    val movieDiffUtil = MovieDiffUtil(it.listMovies, result.listMovies)
//                    val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(movieDiffUtil)
                    val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z")
                    val currentDateAndTime: String = simpleDateFormat.format(Date())
                    textViewMovieList?.text = "Обновлено в $currentDateAndTime"
                    it.updateData(result.listMovies)
                    //  diffResult.dispatchUpdatesTo(movieListUpdateCallback)
                    //recycler?.smoothScrollToPosition(movieListUpdateCallback.firstInsert);
                }
            }
            is ErrorResult -> {
                adapter?.updateData(emptyList())
                Toast.makeText(context, "Ошибка при загузке, попробуйте снова", Toast.LENGTH_LONG)
                    .show()
            }
            is TerminalError -> {
                adapter?.updateData(emptyList())
                Toast.makeText(context, "Неизвестная ошибка", Toast.LENGTH_LONG).show()
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