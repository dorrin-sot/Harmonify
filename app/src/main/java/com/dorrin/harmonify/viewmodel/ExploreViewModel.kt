package com.dorrin.harmonify.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.dorrin.harmonify.apiservice.ChartApiService
import com.dorrin.harmonify.model.Album
import com.dorrin.harmonify.model.Artist
import com.dorrin.harmonify.model.Result
import com.dorrin.harmonify.model.Track
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
  private val chartApiService: ChartApiService,
) : ViewModel() {
  private val _chart = MutableLiveData<Result>()
  val chart: LiveData<Result> get() = _chart

  fun getChart() {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }
    viewModelScope.launch {
      val chart = chartApiService.getChart()
      println("explore: $chart")
      _chart.value = chart
    }
  }
}