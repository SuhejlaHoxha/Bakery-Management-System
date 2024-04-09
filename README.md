**Implementimi i Flyway në Projektin "Cafe Management System"**

*Përshkrim* <br>
Flyway është një mjet për menaxhimin e versioneve të bazave të të dhënave që na lejon të kontrollojmë ndryshimet në skemën e bazës së të dhënave përmes skriptave migrimi. <br><br>
*Pse Flyway?* <br>
Zgjodhëm Flyway për shkak të thjeshtësisë së tij, mbështetjes së gjerë të bazave të të dhënave dhe integrimit të lehtë me Spring Boot. <br>

*Konfigurimi* <br>
Për të integruar Flyway në projektin tonë Spring Boot, ne ndoqëm hapat e mëposhtëm: <br>

1) Shtimi i Varësive: Fillimisht, shtojmë varësinë e Flyway në skedarin 'pom.xml' të projektit tonë. <br>

<**dependency**> <br>
    <**groupId**>org.flywaydb</**groupId**> <br>
    <**artifactId**>flyway-core</**artifactId**> <br>
</**dependency**> <br>
 
2) Konfigurimi i Property-ve: Konfigurojmë propertitë e Flyway në skedarin application.properties të Spring Boot. <br>
spring.flyway.enabled=true <br>
spring.flyway.baseline-on-migrate=true <br>
spring.datasource.url=jdbc:mysql://localhost:3306/database_name <br>
spring.datasource.username=db_user <br>
spring.datasource.password=db_password <br>

3) Skriptat e Migrimit: Skriptat e migrimit vendosen në direktorinë src/main/resources/db/migration. Emërtimi i skriptave ndjek konvencionin V<**VERSION**>__<**DESCRIPTION**>.sql, ku <**VERSION**> është numri i versionit të migrimit dhe <**DESCRIPTION**> është një përshkrim i shkurtër i migrimit. <br>

*Përdorimi:* <br>
Pasi të kemi konfiguruar dhe shtuar skriptat e migrimit, thjesht ekzekutojmë aplikacionin tonë. Flyway do të kontrollojë bazën e të dhënave për të përcaktuar nëse ndonjë migrim është i nevojshëm dhe do të aplikojë çdo migrim të papërdorur në rendin e duhur.
