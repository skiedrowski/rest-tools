object Ver {
    const val kotlin = "1.7.10" //also update buildSrc/build.gradle.kts
    const val cdi_api = "2.0" //Java EE 8
    const val jaxrs_api = "2.1" //Java EE 8
    const val javax_annotation = "1.3" //Java EE 8
    const val javax_xml_bind = "2.2.12" //Java EE 8

    const val kt_tools = "20220905"
    const val cdi_tools = "20220905"

    //test
    const val junit = "5.8.+"
    const val mockk = "1.12.7"
    const val kotest_assertions = "5.4.2"
    const val payara = "5.191"
}

object Deps {
    const val kt_stdlib_jdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Ver.kotlin}"
    const val kt_tools = "com.github.skiedrowski.tools:kotlin-tools:${Ver.kt_tools}"
    const val cdi_tools = "com.github.skiedrowski.tools.cdi:cdi-properties:${Ver.cdi_tools}"

    const val cdi_api = "javax.enterprise:cdi-api:${Ver.cdi_api}"
    const val jaxrs_api = "javax.ws.rs:javax.ws.rs-api:${Ver.jaxrs_api}"
    const val javax_annotation = "javax.annotation:javax.annotation-api:${Ver.javax_annotation}"
    const val javax_xml_bind = "javax.xml.bind:jaxb-api:${Ver.javax_xml_bind}"

    const val junit = "org.junit.jupiter:junit-jupiter-api:${Ver.junit}"
    const val junit_engine = "org.junit.jupiter:junit-jupiter-engine:${Ver.junit}"
    const val mockk = "io.mockk:mockk:${Ver.mockk}"
    const val kotest_assertions = "io.kotest:kotest-assertions-core:${Ver.kotest_assertions}"
    const val payara_embedded = "fish.payara.extras:payara-embedded-all:${Ver.payara}"
}
