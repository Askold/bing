package ru.silonov.bing.repository;

import org.springframework.data.jpa.repository.JpaRepository
import ru.silonov.bing.model.dictionaries.ResponsibilityClass
import java.util.*

interface ResponsibilityClassRepository : JpaRepository<ResponsibilityClass, UUID> {
}