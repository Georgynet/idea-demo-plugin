package com.georgynet.bitbucket.settings

import com.georgynet.bitbucket.components.BitBucketComponent
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import javax.swing.event.DocumentListener
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField
import javax.swing.event.DocumentEvent

class BitbucketSettings(private val project: Project): Configurable, DocumentListener {
    private val tokenField: JTextField = JTextField()
    private val workspaceField: JTextField = JTextField()
    private val repositoryField: JTextField = JTextField()

    private var modified = false

    override fun isModified(): Boolean = modified

    override fun getDisplayName(): String = "BitBucket PRs"

    override fun apply() {
        val config = BitBucketComponent.getInstance(project)
        config.token = tokenField.text
        config.workspace = workspaceField.text
        config.repository = repositoryField.text

        modified = false
    }

    override fun createComponent(): JComponent {
        val mainPanel = JPanel()
        mainPanel.setBounds(0, 0, 452, 254)
        mainPanel.layout = null

        val labelPassword = JLabel("Token")
        labelPassword.setBounds(30, 25, 83, 16)
        mainPanel.add(labelPassword)

        val labelWorkspace = JLabel("Workspace")
        labelWorkspace.setBounds(30, 60, 83, 16)
        mainPanel.add(labelWorkspace)

        val labelRepository = JLabel("Repository")
        labelRepository.setBounds(30, 95, 83, 16)
        mainPanel.add(labelRepository)

        tokenField.setBounds(125, 20, 291, 26)
        mainPanel.add(tokenField)

        workspaceField.setBounds(125, 55, 291, 26)
        mainPanel.add(workspaceField)

        repositoryField.setBounds(125, 90, 291, 26)
        mainPanel.add(repositoryField)

        val config = BitBucketComponent.getInstance(project)
        tokenField.text = config.token
        workspaceField.text = config.workspace
        repositoryField.text = config.repository

        tokenField.document?.addDocumentListener(this)
        workspaceField.document?.addDocumentListener(this)
        repositoryField.document?.addDocumentListener(this)

        return mainPanel
    }

    override fun insertUpdate(e: DocumentEvent?) {
        modified = true
    }

    override fun removeUpdate(e: DocumentEvent?) {
        modified = true
    }

    override fun changedUpdate(e: DocumentEvent?) {
        modified = true
    }
}