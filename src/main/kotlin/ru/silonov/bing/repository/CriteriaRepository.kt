package ru.silonov.bing.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.silonov.bing.model.dictionaries.Criteria
import ru.silonov.bing.model.dictionaries.HydroObject
import java.util.*

interface CriteriaRepository: JpaRepository<Criteria, UUID>