package com.example.kaagazscanner.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kaagazscanner.R
import com.example.kaagazscanner.activities.ImageViewModelFactory
import com.example.kaagazscanner.adapter.ImageAdapter
import com.example.kaagazscanner.database.ImageDao
import com.example.kaagazscanner.database.ImageDatabase
import com.example.kaagazscanner.database.ImageEntity
import com.example.kaagazscanner.repository.RepositoryKagaz
import com.example.kaagazscanner.viewmodel.ViewModelKaagaz
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var viewModelKaagaz: ViewModelKaagaz
    lateinit var imageDatabase: ImageDatabase
    lateinit var imageDao: ImageDao
    var imagetask = mutableListOf<ImageEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //database object creattion
        imageDatabase = ImageDatabase.getImageDatabase(requireActivity())
        imageDao = imageDatabase.getImageDao()

        //recyclerview object initilization
        val imageAdapter = ImageAdapter(imagetask)
        recyclerview.layoutManager = LinearLayoutManager(requireContext())
        recyclerview.adapter = imageAdapter


        //viewmodel object creation
        val repository = RepositoryKagaz(imageDao)
        val imageViewModelFactory = ImageViewModelFactory(repository)
        viewModelKaagaz = ViewModelProviders.of(requireActivity(), imageViewModelFactory).get(ViewModelKaagaz::class.java)


        //observing livedata
        viewModelKaagaz.getImageDetails().observe(requireActivity(), Observer {
            val newTask =it
            imagetask.clear()
            imagetask.addAll(newTask)
            imageAdapter.notifyDataSetChanged()
        }
        )
    }

}