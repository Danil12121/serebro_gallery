package ru.null_checkers.user_profile.presentation

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import ru.null_checkers.common.entity.SharedViewModel
import ru.null_checkers.common.shared_view_model.LoadFromLocalStorage
import ru.null_checkers.common.shared_view_model.PhotoSharedViewModel
import ru.null_checkers.user_profile.R
import ru.null_checkers.user_profile.presentation.recycler.adapter.PhotoAdapter


class GalleryFragment : Fragment(R.layout.fragment_gallery) {
    private lateinit var adapter: PhotoAdapter
    private lateinit var rcvGallery: RecyclerView
    private lateinit var addBtn: Button
    private lateinit var viewModel: PhotoSharedViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as SharedViewModel).getSharedPhotoViewModel()
        adapter = PhotoAdapter()
        rcvGallery = view.findViewById(R.id.rcv_gallery)
        addBtn = view.findViewById(R.id.AddPhotoBtn)
        rcvGallery.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = this@GalleryFragment.adapter
        }
        val load = LoadFromLocalStorage(this, viewModel)
        load.addImageFromLocal(addBtn)
        viewModel.getGalleryPhotos()
        viewModel.currPhoto.observe(viewLifecycleOwner) { photo ->
            viewModel.addItem(photo)
            viewModel.getGalleryPhotos()
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    adapter.submitList(it)
                }
            }
        }
    }

    companion object {
        fun newInstance(): GalleryFragment {
            return GalleryFragment()
        }
    }
}