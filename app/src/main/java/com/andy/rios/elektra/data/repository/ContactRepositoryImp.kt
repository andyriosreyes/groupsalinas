package com.andy.rios.elektra.data.repository

import com.andy.rios.elektra.data.network.ApiInterface
import com.andy.rios.elektra.domain.repository.ContactRepository
import com.andy.rios.elektra.common.Either
import com.andy.rios.yapechallenge.common.NoConnectivityException
import com.andy.rios.elektra.common.fromJson
import com.andy.rios.elektra.common.haveError
import com.andy.rios.elektra.data.datasource.ContactDataSource
import com.andy.rios.elektra.data.model.ContactMain
import com.andy.rios.elektra.data.model.ContactResponse
import io.reactivex.internal.util.NotificationLite.getError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class ContactRepositoryImp @Inject constructor(
    private val apiInterface: ApiInterface,
    private val contactDataSource: ContactDataSource
    ) : ContactRepository {

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun getContacts(): Either<Throwable, ArrayList<ContactResponse>> {
        return try {
            withContext(dispatcher) {
//                apiInterface.getContacts().let {
//                    if (haveError(it)) {
//                        Either.Error(IOException(getError(it)))
//                    } else {
//                        //val listMain = fromJson(it, ContactMain::class.java)
//                        val list = fromJson(it, Array<ContactResponse>::class.java) as ArrayList<ContactResponse>
//                        contactDataSource.saveContact(list)
//                        val listDaoRecipe = contactDataSource.getAllContact()
//                        Either.Success(listDaoRecipe)
//                    }
//                }
                Either.Success(contactDataSource.getAllContact())
            }
        } catch (ex: Throwable) {
            when(ex){
                ex as NoConnectivityException -> {
                    Either.Success(contactDataSource.getAllContact())
                }
                else -> {
                    Either.Error(ex)
                }
            }
        }
    }


}