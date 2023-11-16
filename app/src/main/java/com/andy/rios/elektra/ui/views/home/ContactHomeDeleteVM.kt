package com.andy.rios.elektra.ui.views.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andy.rios.elektra.domain.model.ContactModel
import com.andy.rios.elektra.domain.usecase.GetDeleteContactLocalUseCase
import com.andy.rios.elektra.ui.mapper.toPresentation
import com.andy.rios.elektra.ui.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactHomeDeleteVM @Inject constructor(
    private val getDeleteContactLocalUseCase: GetDeleteContactLocalUseCase
) : ViewModel(){

    val stateDeleteContact: LiveData<State> get() = _stateDeleteContact
    private val _stateDeleteContact = MutableLiveData<State>()


    fun getDeleteContact(contactModel:ContactModel){
        _stateDeleteContact.value = State.Loading
        viewModelScope.launch {
            getDeleteContactLocalUseCase.deleteContactLocalUseCase(contactModel).either(
                ::failDelete,
                ::successDelete
            )
        }
    }

    private fun failDelete(fail: Throwable){
        _stateDeleteContact.value = State.Failed(fail.message?:"")
    }

    private fun successDelete(contactModel: List<ContactModel>){
        if(contactModel.isEmpty()){
            _stateDeleteContact.value = State.Empty
        }else{
            _stateDeleteContact.value = State.Success(contactModel.map { it.toPresentation() })
        }
    }


}