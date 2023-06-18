package com.georgynet.bitbucket.ui

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.dsl.gridLayout.HorizontalAlign
import kong.unirest.json.JSONArray
import kong.unirest.json.JSONObject

class PullRequestToolWindow {
    fun getPanelContent(repositoryName:String, prValues:JSONObject?): DialogPanel {
        return panel {
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

            if (prValues is JSONObject) {
                val pullRequests = prValues.getJSONArray("values")

                if (pullRequests is JSONArray) {
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
}