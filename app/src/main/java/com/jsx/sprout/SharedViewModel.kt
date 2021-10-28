package com.jsx.sprout

import androidx.lifecycle.ViewModel
import com.jsx.applib.callback.UnPeekLiveData

/**
 * Author: JackPan
 * Date: 2021-10-14
 * Time: 14:04
 * Description:
 */
class SharedViewModel : ViewModel() {

    val loginState = UnPeekLiveData<Boolean>()
}