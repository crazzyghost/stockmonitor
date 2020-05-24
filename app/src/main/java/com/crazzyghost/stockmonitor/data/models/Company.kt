package com.crazzyghost.stockmonitor.data.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Company (
    @Id var id: Long = 0,
    var symbol: String? = null,
    var name: String? = null
)