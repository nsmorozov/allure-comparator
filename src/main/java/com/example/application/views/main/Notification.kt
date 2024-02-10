package com.example.application.views.main

import com.vaadin.flow.component.Text
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.html.Anchor
import com.vaadin.flow.component.html.AnchorTarget
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout


class CompareNotification(leftUrl: String, rightUrl: String) {

    val notification = Notification()
    init {
        val text = Div(
            Text("Разница "),
            Anchor(leftUrl, "Слева ", AnchorTarget.BLANK),
            Anchor(rightUrl, "Справа", AnchorTarget.BLANK)
        )

        val closeButton = Button(Icon("lumo", "cross"))
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE)
        closeButton.setAriaLabel("Close")
        closeButton.addClickListener { notification.close() }

        val layout = HorizontalLayout(text, closeButton)
        layout.alignItems = FlexComponent.Alignment.CENTER
        notification.position = Notification.Position.MIDDLE
        notification.add(layout)
    }
    fun show() {
        notification.open()
    }
}