/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.myprojects.android.newsapp.overview

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.myprojects.android.newsapp.R
import com.myprojects.android.newsapp.databinding.FragmentOverviewBinding


/**
 * This fragment shows the the status of the News real-estate web services transaction.
 */
class OverviewFragment : Fragment() {

    /**
     * Lazily initialize our [OverviewViewModel].
     */
    private val viewModel: OverviewViewModel by lazy {
        //ViewModelProvider(this).get(OverviewViewModel::class.java)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onViewCreated()"
        }
        ViewModelProvider(this, OverviewViewModel.Factory(activity!!.application)).get(OverviewViewModel::class.java)
    }

    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null
    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the OverviewFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = FragmentOverviewBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel
        val adapter= PhotoGridAdapter(PhotoGridAdapter.OnClickListener {
            viewModel.displayPropertyDetails(it)
        })

        binding.photosGrid.adapter = adapter

//        viewModel.properties.observe(viewLifecycleOwner, Observer{
//            it?.let { adapter.submitList(it) }
//        })

        //setup the action bar with pull to refresh layout
        mSwipeRefreshLayout = binding.container as SwipeRefreshLayout
        mSwipeRefreshLayout!!.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                Log.e("myTag", "refresh")
                viewModel.updateFilter("android")
                if (mSwipeRefreshLayout!!.isRefreshing) {
                    mSwipeRefreshLayout!!.isRefreshing = false
                }
            }

        })

        viewModel.navigateToSelectedProperty.observe(this, Observer {
            if ( null != it ) {
                //this.findNavController().navigate(OverviewFragmentDirections.actionShowDetail(it))
                val builder= CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                this.activity?.let { it1 -> customTabsIntent.launchUrl(it1, Uri.parse(it.url)) }
                viewModel.displayPropertyDetailsComplete()
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

    /**
     * Inflates the overflow menu that contains filtering options.
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.updateFilter(
            when (item.itemId) {
                R.id.show_all_menu-> "a"
                R.id.show_food_menu -> "food"
                R.id.show_health_menu -> "health"
                R.id.show_tech_menu -> "technology"
                R.id.show_science_menu -> "science"
                R.id.show_android_menu -> "android"
                else -> "android"
            }
        )
        return true
    }
}
