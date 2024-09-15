package com.assignment.bookspot.ViewModel

import Book
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.bookspot.API.RetrofitInstance
import kotlinx.coroutines.launch

class BookListViewModel:ViewModel() {
    private val TAG = BookListViewModel::class.java.simpleName

    private val storeBookList = MutableLiveData<List<Book>>()
    val fetchBookListFromModel:LiveData<List<Book>> = storeBookList

    private val storeBookDetails = MutableLiveData<Book?>()
    val fetchBookDetailFromModel: MutableLiveData<Book?> = storeBookDetails

    private val isLoadingStore = MutableLiveData(false)
    val isLoadingFetch:LiveData<Boolean> get() =  isLoadingStore

    private val errorMessageStore = MutableLiveData<String?>(null)
    val errorMessageFetch: LiveData<String?> get() = errorMessageStore

    
    fun fetchBookList(){
        viewModelScope.launch {
            isLoadingStore.value = true
            errorMessageStore.value = null
            try {
                val response = RetrofitInstance.api.getBooks()
                Log.d(TAG, "fetchBookList: $response")
                storeBookList.postValue(response)
                isLoadingStore.value = false
            }
            catch (e:Exception){
                e.printStackTrace()
                errorMessageStore.value = e.message
                isLoadingStore.value = false
            }
        }
    }

    fun fetchBookDetail(isbn:String){
        viewModelScope.launch {
            try {
                storeBookDetails.value = null
                val detailResponse = RetrofitInstance.api.getBookDetails(isbn)
                storeBookDetails.postValue(detailResponse)
                Log.d(TAG, "fetchBookDetail: $detailResponse")
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}