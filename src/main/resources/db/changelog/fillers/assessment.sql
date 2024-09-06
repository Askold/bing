CREATE TABLE IF NOT EXISTS bing.assessment
(
    id                              UUID PRIMARY KEY, -- Идентификатор шаблона
    technical_state                 DOUBLE PRECISION, -- Показатель технического состояния
    correction_factor_value         DOUBLE PRECISION, -- Корректирующий коэффициент от класса ответственности сооружения
    technical_state_with_correction DOUBLE PRECISION, -- Показатель технического состояния с учетом класса ответственности сооружения
    final_technical_state           VARCHAR(255),     -- Вид технического состояния
    term_of_use_state               DOUBLE PRECISION, -- Показатель условий эксплуатации
    term_of_use_factor              DOUBLE PRECISION, -- Коэффициент значимости показателя условия эксплуатации
    construction_state_without_e3   DOUBLE PRECISION, -- Показатель соответствия сооружения действующим нормам без учета критериев подгруппы Е3
    construction_state_with_e3      DOUBLE PRECISION, -- Показатель соответствия сооружения действующим нормам с учетом критериев подгруппы Е3
    safety_state_without_e3         DOUBLE PRECISION, -- Показатель безопасности сооружения без учета критериев подгруппы Е3
    safety_state_with_e3            DOUBLE PRECISION, -- Показатель безопасности сооружения с учетом критериев подгруппы Е3
    significance_factor             DOUBLE PRECISION, -- Коэффициент значимости, зависящий от уровня ответственности сооружения
    danger_accident_factor          DOUBLE PRECISION, -- Коэффициент, учитывающий степень опасности для сооружения и окружающей среды
    safety_scenario_group_state     DOUBLE PRECISION, -- Показатель безопасности для конкретного сценария в зависимости от группы аварии
    final_safety_level              VARCHAR(255),     -- Итоговый уровень безопасности
    accident_probability            DOUBLE PRECISION, -- Вероятность возникновения аварии
    object_id                       VARCHAR(255),     -- Идентификатор объекта
    fact_values                     VARCHAR(255),     -- Фактическое значение показателя оценки
    scenario_id                     VARCHAR(255),     -- Идентификатор сценария
    created_at                      TIMESTAMP,        -- Дата создания
    created_by                      VARCHAR(255)      -- Автор создания
);
