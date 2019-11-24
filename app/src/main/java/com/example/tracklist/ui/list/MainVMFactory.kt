package com.example.tracklist.ui.list

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tracklist.services.search.ITunesRepository
import com.example.tracklist.ui.details.TrackDetailsFragmentVM
import org.koin.dsl.module

val vmFactoryModule = module {
    factory { MainVMFactory(get(), get()) }
}

class MainVMFactory constructor(private val context: Context,
                                private val repo: ITunesRepository) : ViewModelProvider.Factory
{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        return when
        {
            (modelClass.isAssignableFrom(TrackListFragmentVM::class.java))    ->
                TrackListFragmentVM(context, repo) as T

            (modelClass.isAssignableFrom(TrackDetailsFragmentVM::class.java)) ->
                TrackDetailsFragmentVM(context) as T

            else                                                              ->
                throw IllegalArgumentException("Could not create $modelClass Unknown ViewModel Class. Creation not defined in $this")
        }
    }
}