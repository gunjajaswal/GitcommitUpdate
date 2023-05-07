package com.example.gotour.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gotour.models.User

class UserViewModel: ViewModel(){
 private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    fun setUser(user: User){
        _user.value = user


    }

}