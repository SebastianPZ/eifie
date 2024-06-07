package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.data.mock.FormFakeData
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNotificationsUseCase @Inject constructor() {

    fun invoke() = flow {
        emit(FormFakeData.getNotificationsData())
    }

}