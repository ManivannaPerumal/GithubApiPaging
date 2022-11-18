package assessment.narayanagroup.githubapisearch.base

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import assessment.narayanagroup.githubapisearch.presentation.home.ViewModelFactory

abstract class BaseVMActivity<VM : BaseViewModel> : AppCompatActivity(), LifecycleObserver{

    lateinit var mViewModel: VM
    lateinit var mViewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initVM()
        startObserve()
        setContentView(getLayoutResId())
        initView()
    }

    private fun initVM() {
        providerVMClass()?.let {
            mViewModel = ViewModelProvider(this, factory = mViewModelFactory).get(it)
            mViewModel.let {
                lifecycle::addObserver
            }
        }
    }

  open fun providerVMClass(): Class<VM>? = null

    open fun startObserve() {
        mViewModel.mExceptions.observe(this) { t -> onError(t) }
    }

    open fun onError(e: Throwable) {}

    abstract fun getLayoutResId(): View
    abstract fun initView()
    abstract fun initData()

    override fun onDestroy() {
        lifecycle.removeObserver(mViewModel)
        super.onDestroy()
    }



}