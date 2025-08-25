package com.mahesh.parentalcontrol.data.repository

import android.content.Context
import com.mahesh.parentalcontrol.R
import com.mahesh.parentalcontrol.core.security.PinCrypto
import com.mahesh.parentalcontrol.data.local.dao.SecurityDao
import com.mahesh.parentalcontrol.data.local.entity.SecurityAnswerEntity
import com.mahesh.parentalcontrol.domain.model.SecurityQuestion
import com.mahesh.parentalcontrol.domain.repository.SecurityQuestionsRepository

class SecurityQuestionsRepositoryImpl(
    private val context: Context,
    private val dao: SecurityDao
) : SecurityQuestionsRepository {

    override suspend fun getPredefinedQuestions(): List<SecurityQuestion> {
        val keys = context.resources.getStringArray(R.array.security_question_keys)
        return keys.map { key ->
            val resId = context.resources.getIdentifier(key, "string", context.packageName)
            val text = if (resId != 0) context.getString(resId) else key // fallback
            SecurityQuestion(key, text)
        }
    }

    override suspend fun saveAnswers(answers: Map<String, String>) {
        val entities = answers.map { (key, raw) ->
            val normalized = raw.trim().lowercase()
            val h = PinCrypto.hash(normalized)
            SecurityAnswerEntity(
                questionKey = key,
                hashB64 = h.hashB64,
                saltB64 = h.saltB64,
                iterations = h.iterations
            )
        }
        dao.upsertAnswers(entities)
    }

    override suspend fun verifyAnswers(answers: Map<String, String>): Int {
        val stored = dao.getAllAnswers().associateBy { it.questionKey }
        var correct = 0
        answers.forEach { (key, raw) ->
            val s = stored[key] ?: return@forEach
            val ok = PinCrypto.verify(
                raw.trim().lowercase(),
                PinCrypto.PinHash(s.hashB64, s.saltB64, s.iterations)
            )
            if (ok) correct++
        }
        return correct
    }

}