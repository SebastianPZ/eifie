package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.data.mock.FormFakeData
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFormDataUseCase @Inject constructor() {

    fun invoke(formId: Int) = flow {
        emit(FormFakeData.getFormData())
    }

}