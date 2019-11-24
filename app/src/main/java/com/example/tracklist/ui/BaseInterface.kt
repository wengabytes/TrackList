package com.example.tracklist.ui

interface BaseInterface
{
    fun onLoading(isLoading: Boolean)
    fun onError(errorMessage: String)
    fun setAppBarTitle(title: String)
    fun isBackButtonVisible(isVisible: Boolean)
}