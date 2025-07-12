package com.example.latihandesign1

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserViewModel:ViewModel(){
    var user = mutableStateListOf<User>()
        private  set
    var isLoading by mutableStateOf(false)
        private  set
    var isRefresh by mutableStateOf(false)
        private set
    var page = 1
    private var totalpages = 1
    var isEndReached by mutableStateOf(false)
    private set

    var selectedUserName = mutableStateListOf<String>()
    private set
    fun getUsers(refresh:Boolean= false){
        viewModelScope.launch {
            if(refresh){
                page = 1
                isRefresh = true
                user.clear()
                isEndReached = false
            }
            try {
                val response = RetrofitInstance.api.getUsers(page =page, perPage = 10)
                user.addAll(response.data)
                totalpages = response.total_pages
                if(page > totalpages){
                   isEndReached = true
                }

            }catch(e:Exception){
                Log.e("UserViewModel", "Error: ${e.message}")
            }finally {
                isLoading = false
                isRefresh = false
            }
        }
    }
    fun selectuser(user: User){
        val fullname =  "${user.first_name} ${user.last_name}"
        if (!selectedUserName.contains(fullname)) {
            selectedUserName.add(fullname)
        }
    }

}