package com.roc.gank.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.roc.gank.R
import com.roc.gank.adapter.GankAdapter
import com.roc.gank.entity.GankBean
import com.roc.gank.mvp.presenter.CollectionPresenter
import com.roc.gank.mvp.view.CollectionView
import com.roc.gank.utils.ToastUtils
import com.roc.gank.widget.dialogs.OnDialogActionListener
import com.roc.gank.widget.dialogs.SingleChooseDialog
import com.roc.gank.widget.itemdecoration.NormalDividerDecoration
import com.scwang.smartrefresh.header.BezierCircleHeader
import com.scwang.smartrefresh.layout.footer.BallPulseFooter
import kotlinx.android.synthetic.main.activity_collection.*
import kotlinx.android.synthetic.main.content_scrolling.*

class CollectionActivity : BaseActivity<CollectionView, CollectionPresenter>(), CollectionView, GankAdapter.OnItemClickListener {

    private var mAdapter: GankAdapter? = null
    private var mDeleteDialog: SingleChooseDialog<GankBean>? = null

    override fun initPresenter(): CollectionPresenter {
        return CollectionPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)
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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
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
            //            mPresenter?.loadMoreData()
            refreshLayout.finishLoadmore()
        }
    }

    override fun showLoading() {
        refreshLayout.autoRefresh()
    }

    override fun hideLoading() {
        refreshLayout.finishRefresh()
    }


    override fun onItemClick(url: String) {
        startActivity(Intent(this, WebViewActivity::class.java).putExtra("URL", url))
    }

    override fun onItemLongClick(bean: GankBean) {
        showDeleteDialog(bean)
    }

    /**
     * 显示移除收藏对话框
     */
    private fun showDeleteDialog(gankBean: GankBean) {
        if (mDeleteDialog == null) {
            mDeleteDialog = SingleChooseDialog(this, "移除", true)
            mDeleteDialog?.extra = gankBean
            mDeleteDialog?.setOnDialogActionListener(object : OnDialogActionListener<GankBean>() {
                override fun onConfirmed(gankBean: GankBean?) {
                    super.onConfirmed(gankBean)
                    if (gankBean != null) {
                        mPresenter?.delete(gankBean)
                    } else {
                        ToastUtils.showToast("移除失败")
                    }
                }
            })
        } else {
            mDeleteDialog?.extra = gankBean
        }
        mDeleteDialog?.show()
    }

    /**
     * 显示收藏夹数据
     */
    override fun showCollectedGanks(ganks: List<GankBean>?) {
        mAdapter?.initData(ganks)
    }

    /**
     * 提示没有收藏夹数据
     */
    override fun showNotCollectedGanks(throwable: Throwable?) {
        ToastUtils.showToast("没有收藏的干货")
    }

    override fun showDeleteSuccess() {
        ToastUtils.showToast("移除成功")
        mPresenter?.initData()
    }

    override fun showDeleteError() {
        ToastUtils.showToast("移除失败")
    }
}
