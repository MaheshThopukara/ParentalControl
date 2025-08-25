package com.mahesh.parentalcontrol.data.local.dao

import androidx.room.*
import com.mahesh.parentalcontrol.data.local.entity.SecurityAnswerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SecurityDao {
    @Query("SELECT * FROM security_answers")
    suspend fun getAllAnswers(): List<SecurityAnswerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAnswers(answers: List<SecurityAnswerEntity>)
}