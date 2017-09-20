package com.ljb.mvp.kotlin.presenter

import com.ljb.mvp.kotlin.contract.MyContract
import com.ljb.mvp.kotlin.protocol.dao.UserDaoProtocol
import com.ljb.mvp.kotlin.utils.RxUtils
import com.wuba.weizhang.common.LoginUser
import com.wuba.weizhang.protocol.http.UserProtocol
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by L on 2017/7/18.
 */
class MyPresenter(private val mView: MyContract.IMyView) : MyContract.IMyPresenter {

    override fun getMvpView() = mView

    private var mUserInfoDisposable: Disposable? = null

    override fun startTask() {
        mUserInfoDisposable = Observable.concat(
                UserDaoProtocol.createObservable { UserDaoProtocol.findUserByName(LoginUser.name) },
                UserProtocol.getUserInfoByName(LoginUser.name))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { getMvpView().showUserInfo(it) },
                        { it.printStackTrace() }
                )
    }


    override fun logout() {
        LoginUser.name = ""
        getMvpView().logoutSuccess()
    }

    override fun onDestroy() {
        RxUtils.dispose(mUserInfoDisposable)
    }

}