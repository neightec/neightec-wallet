package com.neightec.neightecavserver.services

import org.spockframework.spring.SpringBean
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile
import spock.lang.Specification

@SpringBootTest(classes = FileStorageService)
class FileStorageServiceSpec extends Specification {

    @SpringBean
    FileStorageService fileStorageService

    def setup() {
        fileStorageService = new FileStorageService()
    }

    def "upload file throws false when file is null"() {
        given:
        def file = null

        when:
        def result = fileStorageService.uploadFile(file)

        then:
        result == false
    }
}
