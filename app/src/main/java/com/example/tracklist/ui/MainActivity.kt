package com.example.tracklist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.example.tracklist.R
import com.example.tracklist.ui.dialogs.LoadingFragment
import com.example.tracklist.ui.dialogs.PositiveDialogFragment
import com.example.tracklist.ui.dialogs.PositiveDialogInterface
import com.example.tracklist.ui.list.TrackListFragment
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity(), BaseInterface, PositiveDialogInterface
{
    // START: Implement Required Methods
    override fun onLoading(isLoading: Boolean)
    {
        if (isLoading)
        {
            if (supportFragmentManager.findFragmentByTag(LoadingFragment.LOADING_DIALOG_IDENTIFIER) == null)
            {
                val loadingFragment: LoadingFragment = LoadingFragment.newInstance()
                loadingFragment.show(
                        supportFragmentManager,
                        loadingFragment.getUniqueIdentifier()
                )
            }
        }
        else
        {
            (supportFragmentManager.findFragmentByTag(LoadingFragment.LOADING_DIALOG_IDENTIFIER) as LoadingFragment?)?.dismiss()
        }
    }

    override fun onError(errorMessage: String)
    {
        if (errorMessage.isEmpty())
            return

        val dialogFragment: PositiveDialogFragment = PositiveDialogFragment.newInstance(
                100,
                getString(R.string.title_error_dialog),
                errorMessage,
                getString(R.string.button_ok)
        )

        dialogFragment.show(supportFragmentManager, dialogFragment.uniqueIdentifier)
    }

    override fun setAppBarTitle(title: String)
    {
        textview_appbar_title.text = title
    }

    override fun isBackButtonVisible(isVisible: Boolean)
    {
        toolbar?.navigationIcon = if (isVisible)
            AppCompatResources.getDrawable(this, R.drawable.ic_back)
        else
            null
    }

    override fun doPositiveDialogClick(id: Int)
    {

    }

    // END  : Implement Required Methods

    // START: Callbacks
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        with(toolbar)
        {
            setSupportActionBar(this)
            navigationIcon = AppCompatResources.getDrawable(this@MainActivity, R.drawable.ic_back)

            setNavigationOnClickListener {
                onBackPressed()
            }
        }

        supportActionBar?.apply {
            title = ""
        }
    }
    // END  D: Callbacks
}
