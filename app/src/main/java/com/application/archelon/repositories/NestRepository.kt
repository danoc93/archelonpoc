package com.application.archelon.repositories

import com.application.archelon.models.Nest
import com.application.archelon.services.ArchelonApi

/**
 * NestRepository
 * Manage all data for nests
 */

object NestRepository {
    suspend fun getAll(): List<Nest> {
        return ArchelonApi.service.getNestList();
    }
}