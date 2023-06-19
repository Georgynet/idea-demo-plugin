package com.georgynet.bitbucket.components

import com.intellij.openapi.components.AbstractProjectComponent
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.XmlSerializerUtil
import java.io.Serializable

@State(name="BitBucketConfiguration", storages = [Storage(value = "bitbucketconfiguration.xml")])
class BitBucketComponent(project: Project? = null) :
    AbstractProjectComponent(project),
    Serializable,
    PersistentStateComponent<BitBucketComponent> {

    var token: String = ""
    var workspace: String = ""
    var repository: String = ""

    override fun getState(): BitBucketComponent = this

    override fun loadState(state: BitBucketComponent) = XmlSerializerUtil.copyBean(state, this)

    companion object {
        fun getInstance(project: Project): BitBucketComponent = project.getComponent(BitBucketComponent::class.java)
    }
}