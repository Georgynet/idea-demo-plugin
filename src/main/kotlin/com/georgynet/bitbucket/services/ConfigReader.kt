package com.georgynet.bitbucket.services

object Config {
    var token = "ATCTT3xFfGN0Awp2CI_rvqX37hS_Uu0HJZduC-jGXzIHcdXMWBHLtnG6MGa20R5seayXqefDRQ3UwpFuN_1Pszkecvr7P1z9eLRernLN_xnJeYGsAsoEY4csC2v4gZ16Vc9b7K2o-7DEdmO3mnf_Y-sjOlwiG9M67kNLx87w3_pY-g-G__Y0Ud0=AACE091B"

    var workspaceName = "georgynet"

    var repositoryName = "test"
}

class ConfigReader {
    fun getToken(): String {
        return Config.token
    }

    fun getWorkspaceName(): String {
        return Config.workspaceName
    }

    fun getRepositoryName(): String {
        return Config.repositoryName
    }
}