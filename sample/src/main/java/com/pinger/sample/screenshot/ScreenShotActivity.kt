package com.pinger.sample.screenshot

import android.graphics.Bitmap
import android.os.Build
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.webkit.WebView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.ScrollView
import com.fungo.baselib.base.basic.BaseActivity
import com.pinger.sample.R
import com.pinger.sample.data.DataProvider
import kotlinx.android.synthetic.main.activity_screen_shot.*


/**
 * @author Pinger
 * @since 2018/7/1 上午1:43
 *
 */
class ScreenShotActivity : BaseActivity() {

    private var mBitmap: Bitmap? = null

    override val layoutResID: Int
        get() = R.layout.activity_screen_shot

    override fun initView() {
        setToolBar(getString(R.string.sample_screen_shot), true)
    }

    /**
     * 截取当前窗体
     */
    fun onScreenShot(view: View) {
        mBitmap = ScreenShotUtils.captureWindow(this)
        showView.setImageBitmap(mBitmap)
    }


    /**
     * 截取当前窗体，不带状态栏,状态栏是空白的
     */
    fun onScreenShotNoStatus(view: View) {
        mBitmap = ScreenShotUtils.captureWindow(this, false)
        showView.setImageBitmap(mBitmap)
    }


    /**
     * 常用的View截图，包括TextView，ImageView，FrameLayout，LinearLayout，RelativeLayout等
     */
    fun onScreenView(view: View) {
        showProgress()
        container.removeAllViews()
        val rootView = layoutInflater.inflate(R.layout.screen_shot_view, container)
        val layout = rootView.findViewById<LinearLayout>(R.id.layout)
        layout.post({
            mBitmap = ScreenShotUtils.captureView(layout)
            showView.setImageBitmap(mBitmap)
            dismissProgress()
        })
    }

    /**
     * 截取ScrollView，包括未展示的内容
     */
    fun onScreenScrollView(view: View) {
        showProgress()
        container.removeAllViews()
        val rootView = layoutInflater.inflate(R.layout.screen_shot_scroll_view, container)
        val scrollView = rootView.findViewById<ScrollView>(R.id.scroll_view)
        scrollView.post({
            mBitmap = ScreenShotUtils.captureScrollView(scrollView)
            showView.setImageBitmap(mBitmap)
            dismissProgress()
        })
    }


    /**
     * 截取ListView，包括未展示的内容
     */
    fun onScreenListView(view: View) {
        showProgress()
        container.removeAllViews()
        val rootView = layoutInflater.inflate(R.layout.screen_shot_list_view, container)
        val listView = rootView.findViewById<ListView>(R.id.listView)
        val adapter = ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, DataProvider.getData())
        listView.adapter = adapter

        listView.post({
            mBitmap = ScreenShotUtils.captureListView(listView)
            showView.setImageBitmap(mBitmap)
            dismissProgress()
        })
    }


    /**
     * 截取RecyclerView，包括未展示的内容
     */
    fun onScreenRecyclerView(view: View) {
        showProgress()
        container.removeAllViews()
        val rootView = layoutInflater.inflate(R.layout.screen_recycler_view, container)
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecyclerAdapter(this, DataProvider.getData())

        recyclerView.post({
            mBitmap = ScreenShotUtils.captureRecyclerView(recyclerView)
            showView.setImageBitmap(mBitmap)
            dismissProgress()
        })
    }


    /**
     * 截取WebView，包括整个高度，需要监听WebView加载完成，并且渲染完成才能够生成截图
     *
     */
    fun onScreenWebView(view: View) {
        startActivity(ScreenShotWebViewActivity::class.java)
    }
}