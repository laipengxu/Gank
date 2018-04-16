package com.roc.gank.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.roc.gank.R
import com.roc.gank.adapter.GankAdapter
import com.roc.gank.entity.GankBean
import com.roc.gank.entity.GankPage
import com.roc.gank.mvp.presenter.MainPresenter
import com.roc.gank.mvp.view.MainView
import com.roc.gank.utils.ToastUtils
import com.roc.gank.widget.dialogs.OnDialogActionListener
import com.roc.gank.widget.dialogs.SingleChooseDialog
import com.roc.gank.widget.itemdecoration.NormalDividerDecoration
import com.scwang.smartrefresh.header.BezierCircleHeader
import com.scwang.smartrefresh.layout.footer.BallPulseFooter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_scrolling.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * 主界面
 */
class MainActivity : BaseActivity<MainView, MainPresenter>(), MainView, GankAdapter.OnItemClickListener {

    var action_folder: MenuItem? = null
    var mAdapter: GankAdapter? = null
    var mSingleChooseDialog: SingleChooseDialog<GankBean>? = null

    override fun initPresenter(): MainPresenter {
        return MainPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        mPresenter?.initData()
    }

    private fun initView() {
        initToolbar()
        initRecyclerView()
        initRefreshLayout()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = SimpleDateFormat("MM月dd日", Locale.CHINA).format(Date())
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.addItemDecoration(NormalDividerDecoration(this))
        mAdapter = GankAdapter()
        recyclerView.adapter = mAdapter
        mAdapter?.setOnItemClickListener(this)
    }

    private fun initRefreshLayout() {

        // 自定义刷新头部
        val bezierCircleHeader = BezierCircleHeader(this)
        bezierCircleHeader.setPrimaryColors(resources.getColor(R.color.colorPrimary))
        refreshLayout.refreshHeader = bezierCircleHeader

        // 自定义加载更多尾部
        val ballPulseFooter = BallPulseFooter(this)
        ballPulseFooter.setNormalColor(resources.getColor(R.color.colorPrimary))
        ballPulseFooter.setAnimatingColor(resources.getColor(R.color.colorPrimary))
        refreshLayout.refreshFooter = ballPulseFooter

        /*
        刷新与加载更多
         */
        refreshLayout.setOnRefreshListener {
            mPresenter?.initData()
        }
        refreshLayout.setOnLoadmoreListener {
            mPresenter?.loadMoreData()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        action_folder = menu?.findItem(R.id.action_folder)
        action_folder?.setOnMenuItemClickListener {
            startActivity(Intent(this, CollectionActivity::class.java))
            true
        }
        return true
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun onItemClick(url: String) {
        startActivity(Intent(this, WebViewActivity::class.java).putExtra("URL", url))
    }

    override fun onItemLongClick(bean: GankBean) {
        showCollectDialog(bean)
    }

    override fun setupView(page: GankPage) {
        refreshLayout.finishRefresh()
        if (!page.error)
            mAdapter?.initData(page.results)
    }

    override fun updateView(page: GankPage) {
        refreshLayout.finishLoadmore()
        if (!page.error)
            mAdapter?.update(page.results)
    }

    override fun showRequestDataFail(throwable: String) {
        if (refreshLayout.isRefreshing) {
            refreshLayout.finishRefresh()
        }
        if (refreshLayout.isLoading) {
            refreshLayout.finishLoadmore()
        }
        Toast.makeText(this, throwable, Toast.LENGTH_LONG).show()
    }

    override fun showSaveGankSuccess() {
        ToastUtils.showToast("收藏成功")
    }

    override fun showSaveGankError() {
        ToastUtils.showToast("收藏失败")
    }

    private fun showCollectDialog(bean: GankBean) {
        if (mSingleChooseDialog == null) {
            mSingleChooseDialog = SingleChooseDialog(this, "收藏", true)
            mSingleChooseDialog?.extra = bean
            mSingleChooseDialog?.setOnDialogActionListener(object : OnDialogActionListener<GankBean>() {
                override fun onConfirmed(gankBean: GankBean?) {
                    // 加入收藏夹
                    mPresenter?.saveGank(gankBean)
                }
            })
        } else {
            mSingleChooseDialog?.extra = bean
        }
        mSingleChooseDialog?.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mSingleChooseDialog?.isShowing ?: false) {
            mSingleChooseDialog?.dismiss()
            mSingleChooseDialog?.cancel()
            mSingleChooseDialog = null
        }
    }

}
