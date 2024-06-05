package com.example.demo

import androidx.lifecycle.ViewModel


class NavViewModel() : ViewModel(){
    var fetchReturn:String? = null

    fun sendFetchReturn(output:String?, viewModel: NavViewModel) {
        viewModel.fetchReturn = output
    }
}


