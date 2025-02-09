package com.example.testvk.imagelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testvk.R
import com.example.testvk.network.ApiService
import com.example.testvk.network.ImageListViewModelFactory
import com.example.testvk.network.ImageRepository

class ImageListFragment : Fragment() {

    private lateinit var imageRecycler: RecyclerView
    private lateinit var retryButton: Button
    private lateinit var viewModel: ImageListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_image_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageRecycler = view.findViewById(R.id.imageList)
        retryButton = view.findViewById(R.id.retry_button)

        viewModel = ViewModelProvider(
            requireActivity(),
            ImageListViewModelFactory(ImageRepository(ApiService.create(requireActivity().application)))
        )[ImageListViewModel::class.java]

        val adapter = ImageAdapter()
        val loadStateAdapter = LoadStateAdapter { viewModel.retry() }
        val concatAdapter = adapter.withLoadStateFooter(loadStateAdapter)

        imageRecycler.adapter = concatAdapter
        val layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)

        imageRecycler.layoutManager = layoutManager

        viewModel.images.observe(viewLifecycleOwner) { pagingData ->
            adapter.submitData(lifecycle, pagingData)
        }

        adapter.addLoadStateListener { loadState ->
            val isLoading = loadState.source.refresh is LoadState.Loading
            val isError = loadState.source.refresh is LoadState.Error

            view.findViewById<View>(R.id.progress_bar)?.visibility =
                if (isLoading) View.VISIBLE else View.GONE

            view.findViewById<View>(R.id.error_layout)?.visibility =
                if (isError && adapter.itemCount == 0) View.VISIBLE else View.GONE
        }
        retryButton.setOnClickListener {
            viewModel.retry()
        }
    }
}
