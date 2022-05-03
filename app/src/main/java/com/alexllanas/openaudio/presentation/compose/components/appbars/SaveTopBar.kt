package com.alexllanas.openaudio.presentation.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.alexllanas.openaudio.R

@Composable
fun SaveTopBar(
    barTitle: String,
    onSaveAction: () -> Unit = {},
    hasAction: Boolean,
    onActionClick: () -> Unit = {},
) {
    ConstraintLayout(
        Modifier.fillMaxWidth().height(height = 56.dp).background(MaterialTheme.colors.primary)
    ) {
        val (saveText, title, gearIcon) = createRefs()
        val interactionSource = remember { MutableInteractionSource() }

        Text(
            text = "Save",
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier
                .clickable(interactionSource = interactionSource, indication = null) {
                    onSaveAction()
                }
                .alpha(0.8f)
                .padding(start = 16.dp).constrainAs(saveText) {
                    start.linkTo(parent.start)
                    centerVerticallyTo(parent)
                })
        Text(
            text = barTitle,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier.constrainAs(title) {
                centerHorizontallyTo(parent)
                centerVerticallyTo(parent)
            },
            style = MaterialTheme.typography.h5
        )
        if (hasAction) {
            Icon(
                imageVector = Icons.Default.Settings,
                tint = MaterialTheme.colors.onPrimary,
                contentDescription = stringResource(R.string.settings),
                modifier = Modifier
                    .alpha(0.8f)
                    .clickable(interactionSource = interactionSource, indication = null) {
                        onActionClick()
                    }
                    .padding(end = 16.dp)
                    .constrainAs(gearIcon) {
                        end.linkTo(parent.end)
                        centerVerticallyTo(parent)
                    }
            )
        }
    }
}