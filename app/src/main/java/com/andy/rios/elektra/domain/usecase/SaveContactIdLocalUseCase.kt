package com.andy.rios.elektra.domain.usecase

import com.andy.rios.elektra.common.Either
import com.andy.rios.elektra.domain.model.ContactModel
import com.andy.rios.elektra.domain.repository.ContactLocalRepository
import com.andy.rios.elektra.ui.mapper.toData

class SaveContactIdLocalUseCase(
    private val recipeRepository: ContactLocalRepository
) {
    suspend fun saveContactIdLocalUseCase(contactModel: ContactModel): Either<Throwable, Boolean> {
        return when(val response = recipeRepository.saveContactIdLocal(contactModel.toData())){
            is Either.Success -> Either.Success(true)
            is Either.Error -> Either.Error(response.a)
        }
    }


}