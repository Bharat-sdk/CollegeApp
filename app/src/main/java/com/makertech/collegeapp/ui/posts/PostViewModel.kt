package com.makertech.collegeapp.ui.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makertech.collegeapp.data.remote.response.Post
import com.makertech.collegeapp.other.Resource
import com.makertech.collegeapp.repository.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val studentRepository: StudentRepository
):ViewModel(){
    private val _postStatus = MutableLiveData<Resource<List<Post>>>()
    val postStatus : LiveData<Resource<List<Post>>> = _postStatus

    fun getAllPosts()
    {
        _postStatus.postValue(Resource.loading(null))
        viewModelScope.launch {
            val result =  studentRepository.getAllPosts()
            _postStatus.postValue(result)
        }
    }
}