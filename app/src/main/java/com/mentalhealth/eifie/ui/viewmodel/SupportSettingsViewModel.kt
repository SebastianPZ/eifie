package com.mentalhealth.eifie.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.Supporter
import com.mentalhealth.eifie.domain.usecases.RetrieveSupporterUseCase
import com.mentalhealth.eifie.domain.usecases.UpdateSupporterUseCase
import com.mentalhealth.eifie.ui.common.LazyViewModel
import com.mentalhealth.eifie.ui.common.ViewState
import com.mentalhealth.eifie.ui.profile.UserPhoto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SupportSettingsViewModel @Inject constructor(
    private val retrieveSupporterUseCase: RetrieveSupporterUseCase,
    private val updateSupporterUseCase: UpdateSupporterUseCase
): LazyViewModel() {

    private val _supportPhoto: MutableStateFlow<UserPhoto?> = MutableStateFlow(null)

    val supportPhoto = _supportPhoto.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = null
    )

    private val _supportName: MutableStateFlow<String> = MutableStateFlow("")

    val supportName = _supportName.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = ""
    )

    lateinit var supporter: Supporter

    init {
        initSupporterData()
    }

    private fun initSupporterData() = viewModelScope.launch {
        retrieveSupporterUseCase.invoke()
            .onStart { viewState.value = ViewState.Loading }
            .onEach {
                when(it) {
                    is EResult.Error -> viewState.value = ViewState.Error(it.error.message ?: "")
                    is EResult.Success -> it.run {
                        _supportPhoto.value = UserPhoto(username = data.name, photoUri = data.photo)
                        _supportName.value= data.name
                        supporter = data
                        viewState.value = ViewState.Success
                    }
                }
            }
            .catch {
                viewState.value = ViewState.Error(it.message ?: "")
            }
            .launchIn(viewModelScope)
    }

    fun updateName(name: String) {
        updateSupporter(name = name)
    }

    fun updatePhoto(photo: String) {

    }

    private fun updateSupporter(name: String? = null, photo: String? = null) = viewModelScope.launch {
        name?.let { supporter.name = it }
        photo?.let { supporter.photo = it }

        updateSupporterUseCase.invoke(supporter)
            .onStart { viewState.value = ViewState.Loading }
            .onEach {
                when(it) {
                    is EResult.Error -> viewState.value = ViewState.Error(it.error.message ?: "")
                    is EResult.Success -> it.run {
                        _supportPhoto.value = UserPhoto(username = data.name, photoUri = data.photo)
                        _supportName.value= data.name
                        supporter = data
                        viewState.value = ViewState.Success
                    }
                }
            }
            .catch {
                viewState.value = ViewState.Error(it.message ?: "")
            }
            .launchIn(viewModelScope)
    }

}