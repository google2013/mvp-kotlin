package com.ljb.mvp.kotlin.contract

import com.ljb.mvp.kotlin.domain.Starred
import com.ljb.mvp.kotlin.mvp.IBaseView
import com.ljb.mvp.kotlin.presenter.base.BaseRxLifePresenter

/**
 * Created by L on 2017/9/21.
 */
interface StarredContract {

    interface IStarredView : IBaseView {
        fun showPage(data: MutableList<Starred>, page: Int)
        fun errorPage(t: Throwable, page: Int)
    }

    abstract class IStarredPresenter(mvpView: IStarredView) : BaseRxLifePresenter<IStarredView>(mvpView) {
        abstract fun onRefresh()
        abstract fun onLoadMore()
    }
}