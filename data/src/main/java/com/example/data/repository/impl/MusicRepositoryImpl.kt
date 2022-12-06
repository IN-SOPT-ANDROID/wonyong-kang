package com.example.data.repository.impl

import com.example.data.datasource.remote.MusicDataSource
import com.example.data.entity.Music
import com.example.data.repository.MusicRepository
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor(
    private val musicDataSource: MusicDataSource
) : MusicRepository {
    override suspend fun getMusics(): Result<List<Music>> =
        runCatching { musicDataSource.getMusics().musics }
}
