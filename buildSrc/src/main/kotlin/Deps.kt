object Ver {
    const val kotlin = "1.7.10" //also update buildSrc/build.gradle.kts
    const val j_cdi_api = "4.0.1"
    const val j_ws_rs_api = "3.1.0"
    const val j_annotation_api = "2.1.1"
    const val j_xml_bind_api = "4.0.0"

    const val kt_tools = "20220905"
    const val cdi_tools = "20220905.3-jee10"

    //test
    const val junit = "5.8.+"
    const val mockk = "1.12.7"
    const val kotest_assertions = "5.4.2"
    const val payara = "6.2023.1"
}

object Deps {
    const val kt_stdlib_jdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Ver.kotlin}"
    const val kt_tools = "com.github.skiedrowski.tools:kotlin-tools:${Ver.kt_tools}"
    const val cdi_tools = "com.github.skiedrowski.tools.cdi:cdi-properties:${Ver.cdi_tools}"

    const val j_cdi_api = "jakarta.enterprise:jakarta.enterprise.cdi-api:${Ver.j_cdi_api}"
    const val j_ws_rs_api = "jakarta.ws.rs:jakarta.ws.rs-api:${Ver.j_ws_rs_api}"
    const val j_annotation_api = "jakarta.annotation:jakarta.annotation-api:${Ver.j_annotation_api}"
    const val j_xml_bind_api = "jakarta.xml.bind:jakarta.xml.bind-api:${Ver.j_xml_bind_api}"

    const val junit = "org.junit.jupiter:junit-jupiter-api:${Ver.junit}"
    const val junit_engine = "org.junit.jupiter:junit-jupiter-engine:${Ver.junit}"
    const val mockk = "io.mockk:mockk:${Ver.mockk}"
    const val kotest_assertions = "io.kotest:kotest-assertions-core:${Ver.kotest_assertions}"
    const val payara_embedded = "fish.payara.extras:payara-embedded-all:${Ver.payara}"
}
