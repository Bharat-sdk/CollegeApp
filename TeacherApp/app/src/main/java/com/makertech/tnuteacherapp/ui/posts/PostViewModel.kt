package com.makertech.tnuteacherapp.ui.posts

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makertech.tnuteacherapp.data.remote.response.Post
import com.makertech.tnuteacherapp.data.remote.response.SimpleResponse
import com.makertech.tnuteacherapp.other.Resource
import com.makertech.tnuteacherapp.repository.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val studentRepository: StudentRepository
):ViewModel(){
    private val _postStatus = MutableLiveData<Resource<List<Post>>>()
    val postStatus : LiveData<Resource<List<Post>>> = _postStatus

    private val _addPost = MutableLiveData<Resource<String>>()
    val addPost : LiveData<Resource<String>> = _addPost

    private val _uploadImage = MutableLiveData<Resource<String>>()
    val uploadImage : LiveData<Resource<String>> = _uploadImage

    fun getAllPosts()
    {
        _postStatus.postValue(Resource.loading(null))
        viewModelScope.launch {
            val result =  studentRepository.getAllPosts()
            _postStatus.postValue(result)
        }
    }
    fun insertPost(post: Post)
    {

        _addPost.postValue(Resource.loading(null))
        viewModelScope.launch {
            val result =  studentRepository.insertPost(post)
            _addPost.postValue(result)
        }
    }

    fun uploadPostImage(uri: Uri)
    {
        _uploadImage.postValue(Resource.loading(null))
        viewModelScope.launch {
           val result = studentRepository.uploadImage(uri)
            _uploadImage.postValue(result)
        }

    }

}