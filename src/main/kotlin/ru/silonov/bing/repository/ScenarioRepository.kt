package ru.silonov.bing.repository;

import org.springframework.data.jpa.repository.JpaRepository
import ru.silonov.bing.model.dictionaries.Scenario
import java.util.*

interface ScenarioRepository : JpaRepository<Scenario, UUID>