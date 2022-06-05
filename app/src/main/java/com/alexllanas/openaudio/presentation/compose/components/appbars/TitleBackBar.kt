package com.alexllanas.openaudio.presentation.compose.components.appbars

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.alexllanas.openaudio.R

@Composable
fun TitleBackBar(barTitle: String? = null, onBackPressed: () -> Unit) {
    ConstraintLayout(
        Modifier.fillMaxWidth().height(height = 56.dp).background(MaterialTheme.colors.background)
    ) {
        val (backArrow, title) = createRefs()

        Icon(
            imageVector = Icons.Default.ArrowBack,
            tint = MaterialTheme.colors.onPrimary,
            contentDescription = stringResource(R.string.back_arrow),
            modifier = Modifier
                .alpha(0.8f)
                .clickable { onBackPressed() }
                .padding(start = 16.dp)
                .constrainAs(backArrow) {
                    start.linkTo(parent.start)
                    centerVerticallyTo(parent)
                }
        )
        Text(
            text = barTitle ?: "",
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier.constrainAs(title) {
                centerHorizontallyTo(parent)
                centerVerticallyTo(parent)
            },
            style = MaterialTheme.typography.h5
        )

    }
}