package com.alexllanas.openaudio.presentation.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.alexllanas.openaudio.R

@Composable
fun OpenAudioAppBar(
    barTitle: String,
    navigationAction: () -> Unit = {},
    navigationIcon: ImageVector,
    hasAction: Boolean,
    actionIcon: ImageVector? = null,
    onActionClick: () -> Unit = {},
) {
    ConstraintLayout(
        Modifier.fillMaxWidth().height(height = 56.dp)
//            .background(MaterialTheme.colors.primary)
    ) {
        val (navIcon, title, actionIcn) = createRefs()
        Icon(
            imageVector = navigationIcon,
            tint = MaterialTheme.colors.onPrimary,
            contentDescription = stringResource(R.string.nav_icon),
            modifier = Modifier
                .alpha(0.8f)
                .clickable { navigationAction() }
                .padding(start = 8.dp)
                .constrainAs(navIcon) {
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
            actionIcon?.let {
                Icon(
                    imageVector = actionIcon,
                    tint = MaterialTheme.colors.onPrimary,
                    contentDescription = stringResource(R.string.settings),
                    modifier = Modifier
                        .alpha(0.8f)
                        .clickable { onActionClick() }
                        .padding(end = 16.dp)
                        .constrainAs(actionIcn) {
                            end.linkTo(parent.end)
                            centerVerticallyTo(parent)
                        }
                )
            }
        }
    }
}
