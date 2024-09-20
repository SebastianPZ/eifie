package com.mentalhealth.eifie.service

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.repository.ChatRepository
import com.mentalhealth.eifie.domain.repository.MessageRepository
import com.mentalhealth.eifie.domain.repository.SupporterRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

@HiltWorker
class UploadMessagesWorker @AssistedInject constructor(
    private val messageRepository: MessageRepository,
    private val supporterRepository: SupporterRepository,
    private val chatRepository: ChatRepository,
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        return try {
            backUpChats()
            Result.success()
        } catch (exception: Exception) {
            Result.failure()
        }
    }

    private suspend fun backUpChats() = withContext(Dispatchers.IO) {
        supporterRepository.getByUser().let {
            when(val supporterData = it) {
                is EResult.Error -> Unit
                is EResult.Success -> {
                    if(supporterData.data.id != null) {
                        when(supporterRepository.backup()) {
                            is EResult.Error -> Unit
                            is EResult.Success -> {
                                when(val chatResult = chatRepository.backup()) {
                                    is EResult.Error -> Unit
                                    is EResult.Success -> chatResult.run {
                                        messageRepository.backup(data, supporterData.data.id ?: 0)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}