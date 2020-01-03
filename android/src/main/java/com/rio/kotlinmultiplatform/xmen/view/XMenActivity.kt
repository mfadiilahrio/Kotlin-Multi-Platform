package com.rio.kotlinmultiplatform.xmen.view

import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.badoo.reaktive.observable.observeOn
import com.badoo.reaktive.scheduler.mainScheduler
import com.rio.core.viewmodel.ViewModelBinding
import com.rio.core.viewmodel.common.ListViewModel
import com.rio.kotlinmultiplatform.R
import com.rio.kotlinmultiplatform.base.BaseActivity
import com.rio.kotlinmultiplatform.common.RecyclerViewAdapter
import com.rio.kotlinmultiplatform.xmen.data.Xmen
import kotlinx.android.synthetic.main.activity_xmen.*
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named

class XMenActivity : BaseActivity() {

    private val scope = getKoin().getOrCreateScope("XMenActivity", named("XMenActivity"))

    private val mViewModelBinding: ViewModelBinding by inject()

    private val mViewModel: ListViewModel<String, Xmen> by scope.inject(named("xmensViewModel"))

    private val mAdapter: RecyclerViewAdapter<Nothing, Xmen, *> by scope.inject(
        named("xmenListAdapter")
    )

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mRefreshLayout: SwipeRefreshLayout

    private var mIsRefreshing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        binding()

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_xmen)

        mRecyclerView = findViewById(R.id.listing)
        mRefreshLayout = findViewById(R.id.refresh_layout)

        mRecyclerView.layoutManager = GridLayoutManager(this, 1)
        mRecyclerView.adapter = mAdapter
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
            mViewModel.inputs.get("x-men")
        }

        mViewModel.inputs.get("x-men")

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

    private fun result(xmens: List<Xmen>) {
        mAdapter.setList(xmens)
    }

    private fun loadMore() {
        mViewModel.inputs.loadMore("x-men")
    }
}
