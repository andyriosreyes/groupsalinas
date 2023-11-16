package com.andy.rios.elektra.data.repository

import com.andy.rios.elektra.common.Either
import com.andy.rios.elektra.data.datasource.ContactDataSource
import com.andy.rios.elektra.data.model.ContactResponse
import com.andy.rios.elektra.domain.repository.ContactLocalRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ContactLocalRepositoryImp @Inject constructor(
    private val contactDataSource: ContactDataSource
    ) : ContactLocalRepository {

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun saveContactIdLocal(contactResponse: ContactResponse): Either<Throwable, Boolean> {
        return try {
            withContext(dispatcher) {
                contactDataSource.saveContactId(contactResponse).let {
                    Either.Success(true)
                }
            }
        } catch (ex: Throwable) {
            Either.Error(ex)
        }
    }

    override suspend fun deleteContact(contactResponse: ContactResponse): Either<Throwable, ArrayList<ContactResponse>> {
        return try {
            withContext(dispatcher) {
                contactDataSource.deleteContact(contactResponse).let {
                    Either.Success(it)
                }
            }
        } catch (ex: Throwable) {
            Either.Error(ex)
        }
    }

    override suspend fun editContactIdLocal(contactResponse: ContactResponse): Either<Throwable, Boolean> {
        return try {
            withContext(dispatcher) {
                contactDataSource.editContactId(contactResponse).let {
                    Either.Success(true)
                }
            }
        } catch (ex: Throwable) {
            Either.Error(ex)
        }
    }

}