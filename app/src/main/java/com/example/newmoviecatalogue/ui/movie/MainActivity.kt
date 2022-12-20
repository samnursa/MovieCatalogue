package com.example.newmoviecatalogue.ui.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newmoviecatalogue.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var movieViewModel: MovieViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        init()
    }

    private fun init(){
        val viewModelFactory = MovieViewModelFactory(this)
        movieViewModel = ViewModelProvider(this, viewModelFactory)[MovieViewModel::class.java]

        movieAdapter = MovieAdapter {
//            val controller = Navigation.findNavController(view)
//            controller.navigate(MovieFragmentDirections.actionMovieFragmentToDetailMovieFragment(it.id))
        }

        binding.rvMovie.layoutManager = LinearLayoutManager(this)
        binding.rvMovie.setHasFixedSize(true)
        binding.rvMovie.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                        //movieViewModel.page.value = movieViewModel.page.value?.plus(1)
                    }
                }
            }
        )
        binding.rvMovie.adapter = movieAdapter

        // Start a coroutine in the lifecycle scope
        lifecycleScope.launch {
            // repeatOnLifecycle launches the block in a new coroutine every time the
            // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Trigger the flow and start listening for values.
                // Note that this happens when lifecycle is STARTED and stops
                // collecting when the lifecycle is STOPPED
                movieViewModel.getAllMovie().collectLatest { movies ->
                    movieAdapter.submitData(movies)
                }
//                movieViewModel .uiState.collect { uiState ->
//                    // New value received
//                    when (uiState) {
//                        is MovieUiState.Success -> {
//                            movieAdapter.submitData(uiState.movies)
//                            movieAdapter.notifyDataSetChanged()
//                        }
//                        is MovieUiState.Error -> showError(uiState.exception)
//                    }
//                }
            }
        }
    }

    private fun showError(t: Throwable){
        Toast.makeText(this, t.message, Toast.LENGTH_LONG).show()
    }
}