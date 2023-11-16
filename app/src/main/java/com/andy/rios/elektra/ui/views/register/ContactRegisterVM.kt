package com.andy.rios.elektra.ui.views.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andy.rios.elektra.domain.usecase.GetEditContactLocalUseCase
import com.andy.rios.elektra.domain.usecase.SaveContactIdLocalUseCase
import com.andy.rios.elektra.ui.model.Contact
import com.andy.rios.elektra.ui.model.toPresentation
import com.andy.rios.elektra.ui.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactRegisterVM @Inject constructor(
    private val getContactIdLocalUseCase: SaveContactIdLocalUseCase,
    private val getEditContactLocalUseCase: GetEditContactLocalUseCase
) : ViewModel(){

    val stateRegisterContact: LiveData<State> get() = _stateRegisterContact
    private val _stateRegisterContact = MutableLiveData<State>()


    fun saveContactLocalId(contact : Contact){
        _stateRegisterContact.value = State.Loading
        viewModelScope.launch {

            getContactIdLocalUseCase.saveContactIdLocalUseCase(contact.toPresentation()).either(
                ::failRegisterInitial,
                ::successRegisterInitial
            )
        }
    }

    private fun failRegisterInitial(fail: Throwable){
        _stateRegisterContact.value = State.Failed(fail.message?:"")
    }

    private fun successRegisterInitial(response : Boolean){
        _stateRegisterContact.value = State.Success(response)
    }

    val stateEditContact: LiveData<State> get() = _stateEditContact
    private val _stateEditContact = MutableLiveData<State>()


    fun editContactLocalId(contact : Contact){
        _stateRegisterContact.value = State.Loading
        viewModelScope.launch {

            getEditContactLocalUseCase.editContactLocalUseCase(contact.toPresentation()).either(
                ::failRegisterInitial,
                ::successRegisterInitial
            )
        }
    }

    private fun failEditContact(fail: Throwable){
        _stateRegisterContact.value = State.Failed(fail.message?:"")
    }

    private fun successEditContact(response : Boolean){
        _stateRegisterContact.value = State.Success(response)
    }
}