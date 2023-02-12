rootProject.name = "dig-up-kotlin"
include("kotlin", "kotlinx")

pluginManagement {

    val kotlinVersion: String by settings

    plugins {
        // 하위 project 들에 id : version 을 정의
        // 따라서, 하위 프로젝트에서 id 명시는 필수 (version 만 일괄적으로 관리)
        kotlin("jvm") version "${kotlinVersion}"
    }
}