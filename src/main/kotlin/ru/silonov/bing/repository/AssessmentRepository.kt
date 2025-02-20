package ru.silonov.bing.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.silonov.bing.model.dictionaries.CriteriaGroup
import ru.silonov.bing.model.fillers.Assessment
import java.util.*

interface AssessmentRepository : JpaRepository<Assessment, UUID>