package ru.silonov.bing.repository;

import org.springframework.data.jpa.repository.JpaRepository
import ru.silonov.bing.model.dictionaries.CriteriaGroup
import java.util.*

interface CriteriaGroupRepository : JpaRepository<CriteriaGroup, UUID>