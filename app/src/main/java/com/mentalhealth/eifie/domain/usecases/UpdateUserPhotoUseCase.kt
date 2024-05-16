package com.mentalhealth.eifie.domain.usecases

import android.net.Uri
import com.mentalhealth.eifie.data.network.DataResult
import com.mentalhealth.eifie.domain.entities.models.UserSession
import com.mentalhealth.eifie.domain.repository.UserRepository
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class UpdateUserPhotoUseCase @Inject constructor(
    private val repository: UserRepository
) {

    fun invoke(userSession: UserSession) = flow {

        val photo = Uri.parse(userSession.picture)

        val result = if(photo != null) {
             when(val response = repository.updateUserPicture(userSession.profileId, photo)) {
                is DataResult.Success -> response.data.run {
                    DataResult.Success(
                        userSession.apply {
                            picture = response.data.picture?.url
                        }
                    )
                }
                is DataResult.Error -> response.run { this }
                DataResult.Loading -> DataResult.Loading
            }
        } else {
            DataResult.Error(IOException("No photo found"))
        }

        emit(result)
    }

}