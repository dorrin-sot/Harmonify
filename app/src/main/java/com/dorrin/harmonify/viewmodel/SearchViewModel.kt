package com.dorrin.harmonify.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dorrin.harmonify.apiservice.SearchApiService
import com.dorrin.harmonify.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
  private val searchApiService: SearchApiService
) : ViewModel() {
  private var _searchOn = MutableLiveData<Boolean>(false)
  val searchOn: LiveData<Boolean> get() = _searchOn

  private var _query = MutableLiveData<String?>("")
  val query: LiveData<String?> get() = _query

  private var _result = MutableLiveData<Result>()
  val result: LiveData<Result> get() = _result

  private var searchJob: Job? = null

  fun setSearchOn(searchOn: Boolean) {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }
    _searchOn.value = searchOn
  }

  fun search(query: String) {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }
    if (query.isEmpty() && this.query.value!!.isEmpty()) {
      setSearchOn(false)
    }
    _query.value = query
    if (searchOn.value!!) fetchResults()
  }

  private fun fetchResults() {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }
    viewModelScope.launch {
      delay(1500)

      if (searchJob != null) {
        searchJob?.cancelChildren()
        searchJob?.cancel()
        searchJob = null
      }

      searchJob = viewModelScope.launch {
        val result = Result(
          searchApiService.searchAlbums(query.value ?: ""),
          searchApiService.searchArtists(query.value ?: ""),
          searchApiService.searchTracks(query.value ?: ""),
        )
        println("search: $result")
        _result.value = result
        searchJob = null
      }
    }
  }
}