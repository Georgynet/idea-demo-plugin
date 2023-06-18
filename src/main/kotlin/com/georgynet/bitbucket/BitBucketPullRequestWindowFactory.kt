package com.georgynet.bitbucket

import com.georgynet.bitbucket.services.BitBucketService
import com.georgynet.bitbucket.services.ConfigReader
import com.georgynet.bitbucket.ui.PullRequestToolWindow
import com.intellij.openapi.components.service
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.ScrollPaneFactory
import com.intellij.ui.components.JBPanel
import com.intellij.ui.content.Content
import com.intellij.ui.content.ContentFactory
import java.awt.BorderLayout
import javax.swing.BorderFactory

class BitBucketPullRequestWindowFactory : ToolWindowFactory, DumbAware {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val bitbucketWindowContent = BitBucketWindowContent(toolWindow)

        val config = ConfigReader()

        val content: Content = ContentFactory.getInstance().createContent(
            bitbucketWindowContent.getContent(config.getRepositoryName(), config.getWorkspaceName()),
            null,
            false
        )
        toolWindow.contentManager.addContent(content)
    }

    private class BitBucketWindowContent(toolWindow: ToolWindow) {
        private val bitBucketService = toolWindow.project.service<BitBucketService>()

        fun getContent(repositoryName:String, workspaceName:String) = JBPanel<JBPanel<*>>().apply {
            val prValues = bitBucketService.getPullRequestList(workspaceName, repositoryName)

            val pullRequestToolWindow = PullRequestToolWindow()
            val prPanel = pullRequestToolWindow.getPanelContent(repositoryName, prValues)

            layout = BorderLayout()

            val scrollPane = ScrollPaneFactory.createScrollPane(prPanel, false)
            scrollPane.border = BorderFactory.createEmptyBorder(0, 20, 20, 20)
            scrollPane.verticalScrollBar.unitIncrement = 16
            add(scrollPane)
        }
    }
}
