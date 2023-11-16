package com.andy.rios.elektra.domain.usecase

import com.andy.rios.elektra.common.Either
import com.andy.rios.elektra.data.mapper.toDomain
import com.andy.rios.elektra.domain.model.ContactModel
import com.andy.rios.elektra.domain.repository.ContactRepository

class GetContactUseCase(
    private val contactRepository: ContactRepository
) {
    suspend fun getContactUseCase(): Either<Throwable, List<ContactModel>> {
        return when(val response = contactRepository.getContacts()){
            is Either.Success -> Either.Success(response.b.map { it.toDomain()})
            is Either.Error -> Either.Error(response.a)
        }

    }
}