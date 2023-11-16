package com.andy.rios.elektra.ui.views.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andy.rios.elektra.domain.model.ContactModel
import com.andy.rios.elektra.domain.usecase.GetContactUseCase
import com.andy.rios.elektra.domain.usecase.GetDeleteContactLocalUseCase
import com.andy.rios.elektra.ui.mapper.toPresentation
import com.andy.rios.elektra.ui.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactHomeVM @Inject constructor(
    private val getContactUseCase: GetContactUseCase,
    private val getDeleteContactLocalUseCase: GetDeleteContactLocalUseCase
) : ViewModel(){

    val stateContact: LiveData<State> get() = _stateContact
    private val _stateContact = MutableLiveData<State>()


    fun getContactData(){
        _stateContact.value = State.Loading
        viewModelScope.launch {
            getContactUseCase.getContactUseCase().either(
                ::failInitial,
                ::successInitial
            )
        }
    }

    private fun failInitial(fail: Throwable){
        _stateContact.value = State.Failed(fail.message?:"")
    }

    private fun successInitial(contactModel: List<ContactModel>){
        if(contactModel.isEmpty()){
            _stateContact.value = State.Empty
        }else{
            _stateContact.value = State.Success(contactModel.map { it.toPresentation() })
        }
    }

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