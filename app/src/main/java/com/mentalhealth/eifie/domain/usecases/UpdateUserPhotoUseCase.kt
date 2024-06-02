package com.mentalhealth.eifie.domain.usecases

import android.net.Uri
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.User
import com.mentalhealth.eifie.domain.repository.UserRepository
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class UpdateUserPhotoUseCase @Inject constructor(
    private val repository: UserRepository
) {

    fun invoke(user: User) = flow {

        val photo = Uri.parse(user.picture)

        val result = if(photo != null) {
             when(val response = repository.updateUserPicture(user.profileId, photo)) {
                is EResult.Success -> response.data.run {
                    EResult.Success(
                        user.apply {
                            picture = response.data.picture
                        }
                    )
                }
                is EResult.Error -> response.run { this }
            }
        } else {
            EResult.Error(IOException("No photo found"))
        }

        emit(result)
    }

}