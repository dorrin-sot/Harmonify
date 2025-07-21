package com.dorrin.harmonify.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Stack
import javax.inject.Inject

@HiltViewModel
class BottomSheetViewModel @Inject constructor() : ViewModel() {
  private val _stack = MutableLiveData<Stack<Pair<BottomSheetType, Any?>>>(Stack())
  private val stack: LiveData<Stack<Pair<BottomSheetType, Any?>>> get() = _stack

  val backStack: LiveData<List<BottomSheetType>>
    get() = _stack.map { it.map { it.first }.toList() }
  val extras: LiveData<List<Any?>>
    get() = _stack.map { it.map { it.second }.toList() }

  fun showBottomSheet(type: BottomSheetType, extra: Any? = null) {
    _stack.value = stack.value!!.apply { push(type to extra) }
  }

  fun hideBottomSheet() {
    _stack.value = stack.value!!.apply { pop() }
  }
}

enum class BottomSheetType {
  ARTIST_BOTTOM_SHEET,
  ALBUM_BOTTOM_SHEET,
  DURATION_EDITOR_BOTTOM_SHEET,
}