package com.andy.rios.elektra.domain.usecase

import com.andy.rios.elektra.common.Either
import com.andy.rios.elektra.data.mapper.toDomain
import com.andy.rios.elektra.domain.model.ContactModel
import com.andy.rios.elektra.domain.repository.ContactLocalRepository
import com.andy.rios.elektra.ui.mapper.toData

class GetDeleteContactLocalUseCase(
    private val recipeRepository: ContactLocalRepository
) {
    suspend fun deleteContactLocalUseCase(contactModel: ContactModel): Either<Throwable, List<ContactModel>> {
        return when(val response = recipeRepository.deleteContact(contactModel.toData())){
            is Either.Success -> Either.Success(response.b.map { it.toDomain() } )
            is Either.Error -> Either.Error(response.a)
        }
    }
}