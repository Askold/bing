package ru.silonov.bing.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.silonov.bing.model.fillers.Report
import java.util.UUID

interface ReportRepository : JpaRepository<Report, UUID>