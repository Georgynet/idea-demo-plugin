package com.georgynet.bitbucket

import com.georgynet.bitbucket.services.BitBucketService
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.components.service
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.openapi.wm.ex.ToolWindowEx
import com.intellij.ui.ScrollPaneFactory
import com.intellij.ui.components.JBPanel
import com.intellij.ui.content.Content
import com.intellij.ui.content.ContentFactory
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.dsl.gridLayout.HorizontalAlign
import kong.unirest.json.JSONArray
import kong.unirest.json.JSONObject
import java.awt.BorderLayout
import javax.swing.BorderFactory

class BitBucketPullRequestWindowFactory : ToolWindowFactory, DumbAware {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val tw = toolWindow as ToolWindowEx?
        val width = tw!!.component.width
        val height = tw!!.component.height

        val bitbucketWindowContent = BitBucketWindowContent(toolWindow)
        val content: Content = ContentFactory.getInstance().createContent(
            bitbucketWindowContent.getContent("test", width, height),
            null,
            false
        )
        toolWindow.contentManager.addContent(content)
    }

    private class BitBucketWindowContent(toolWindow: ToolWindow) {
        private val bitBucketService = toolWindow.project.service<BitBucketService>()
        fun getContent(repositoryName:String, width:Int, height:Int) = JBPanel<JBPanel<*>>().apply {
            val prPanel = panel {
                row {
                    val action = object : DumbAwareAction("Refresh PRs", "Refresh PRs", AllIcons.Actions.Refresh) {
                        override fun actionPerformed(e: AnActionEvent) {
                        }
                    }
                    actionButton(action)
                }

                row("Repository:") {
                    label(
                        repositoryName
                    )
                }

                val prValues = bitBucketService.getPullRequestList("georgynet", repositoryName)
                if (prValues is JSONObject) {
                    val pullRequests = prValues.getJSONArray("values")

                    if (pullRequests is JSONArray) {
                        for (i in 1..5) {
                            for (pullRequest in pullRequests) {
                                if (pullRequest is JSONObject) {
                                    group("PR: " + pullRequest.get("title").toString()) {
                                        row {
                                            browserLink(
                                                "Open in browser",
                                                pullRequest.getJSONObject("links").getJSONObject("html").get("href").toString()
                                            )
                                        }

                                        row("Author:") {
                                            label(
                                                pullRequest.getJSONObject("author").get("display_name").toString()
                                            )
                                            text("<img width=32 src='" + pullRequest.getJSONObject("author").getJSONObject("links").getJSONObject("avatar").get("href").toString() + "'>")
                                                .horizontalAlign(HorizontalAlign.RIGHT)
                                        }

                                        row("State:") {
                                            label(
                                                pullRequest.get("state").toString()
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            layout = BorderLayout()

            val scrollPane = ScrollPaneFactory.createScrollPane(prPanel, false)
            scrollPane.border = BorderFactory.createEmptyBorder(20, 20, 20, 20)
            scrollPane.verticalScrollBar.unitIncrement = 16
            add(scrollPane)
        }
    }
}
