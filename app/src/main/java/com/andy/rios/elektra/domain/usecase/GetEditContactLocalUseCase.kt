package com.andy.rios.elektra.domain.usecase

import com.andy.rios.elektra.common.Either
import com.andy.rios.elektra.domain.model.ContactModel
import com.andy.rios.elektra.domain.repository.ContactLocalRepository
import com.andy.rios.elektra.ui.mapper.toData

class GetEditContactLocalUseCase(
    private val recipeRepository: ContactLocalRepository
) {
    suspend fun editContactLocalUseCase(contactModel: ContactModel): Either<Throwable, Boolean> {
        return when(val response = recipeRepository.editContactIdLocal(contactModel.toData())){
            is Either.Success -> Either.Success(true)
            is Either.Error -> Either.Error(response.a)
        }
    }
}