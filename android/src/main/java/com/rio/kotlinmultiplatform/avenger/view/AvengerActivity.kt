package com.rio.kotlinmultiplatform.avenger.view

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.badoo.reaktive.observable.observeOn
import com.badoo.reaktive.scheduler.mainScheduler
import com.rio.core.viewmodel.ViewModelBinding
import com.rio.core.viewmodel.common.ListViewModel
import com.rio.kotlinmultiplatform.R
import com.rio.kotlinmultiplatform.avenger.data.Avenger
import com.rio.kotlinmultiplatform.base.BaseActivity
import com.rio.kotlinmultiplatform.common.AdapterClickListener
import com.rio.kotlinmultiplatform.common.RecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_avenger.*
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named

class AvengerActivity : BaseActivity(), AdapterClickListener<Avenger> {

    private val mViewModelBinding: ViewModelBinding by inject()

    private val mViewModel: ListViewModel<String, Avenger> by inject(named("avengersViewModel"))

    private val mAdapter: RecyclerViewAdapter<AdapterClickListener<Avenger>, Avenger, RecyclerView.ViewHolder> by inject(
        named("avengerListAdapter")
    )

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mRefreshLayout: SwipeRefreshLayout

    private var mIsRefreshing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        binding()

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_avenger)

        mRecyclerView = findViewById(R.id.listing)
        mRefreshLayout = findViewById(R.id.refresh_layout)

        mRecyclerView.layoutManager = GridLayoutManager(this, 1)
        mRecyclerView.adapter = mAdapter
        mAdapter.listener = this
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val manager = listing.layoutManager as LinearLayoutManager

                val totalItemCount = manager.itemCount
                val lastVisibleItem = manager.findLastVisibleItemPosition()

                if (!mIsRefreshing && totalItemCount <= lastVisibleItem + 2) {
                    loadMore()
                }
            }
        })

        mRefreshLayout.setOnRefreshListener {
            mViewModel.inputs.get("avenger")
        }

        mViewModel.inputs.get("avenger")

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        mViewModelBinding.dispose()

        super.onDestroy()
    }

    override fun onItemClicked(model: Avenger) {
        Toast.makeText(this, model.title, Toast.LENGTH_LONG).show()
    }

    private fun binding() {
        mViewModelBinding.subscribe(
            mViewModel.outputs.loading.observeOn(mainScheduler),
            onNext = ::loading
        )
        mViewModelBinding.subscribe(
            mViewModel.outputs.result.observeOn(mainScheduler),
            onNext = ::result
        )
    }

    private fun loading(isLoading: Boolean) {
        mIsRefreshing = isLoading
        refresh_layout.isRefreshing = isLoading
    }

    private fun result(avengers: List<Avenger>) {
        mAdapter.setList(avengers)
    }

    private fun loadMore() {
        mViewModel.inputs.loadMore("avenger")
    }
}
