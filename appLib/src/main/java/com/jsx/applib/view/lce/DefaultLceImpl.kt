package com.jsx.applib.view.lce;

import android.view.View
import android.widget.TextView
import com.jsx.applib.R
import com.jsx.applib.view.LoadingView

/**
 * Author: JackPan
 * Date: 2021-11-05
 * Time: 11:48
 * Description:
 */
class DefaultLceImpl constructor(
    private val realContent: View?,
    private val loading: LoadingView?,
    private val loadErrorView: View?,
    private val badNetworkView: View?,
    private val noContentView: View?
) : ILce {

    override fun showLoading() {
        hideAll()
        loading?.show()
    }

    override fun loadFinished() {
        realContent?.visibility = View.VISIBLE;
        loading?.hide()
        badNetworkView?.visibility = View.GONE
        noContentView?.visibility = View.GONE
        loadErrorView?.visibility = View.GONE
    }

    override fun showLoadErrorView(tip: String) {
        hideAll()
        val loadErrorText = loadErrorView?.findViewById<TextView>(R.id.loadErrorText)
        loadErrorText?.text = tip
        loadErrorView?.visibility = View.VISIBLE
    }

    override fun showBadNetworkView(listener: View.OnClickListener) {
        hideAll()
        badNetworkView?.visibility = View.VISIBLE
        badNetworkView?.setOnClickListener(listener)
    }

    override fun showNoContentView(tip: String) {
        hideAll()
        val noContentText = noContentView?.findViewById<TextView>(R.id.noContentText)
        noContentText?.text = tip
        noContentView?.visibility = View.VISIBLE
    }

    private fun hideAll(){
        realContent?.visibility = View.GONE;
        loading?.hide()
        badNetworkView?.visibility = View.GONE
        noContentView?.visibility = View.GONE
        loadErrorView?.visibility = View.GONE
    }
}
