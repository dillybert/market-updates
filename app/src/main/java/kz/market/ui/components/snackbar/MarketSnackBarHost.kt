package kz.market.ui.components.snackbar

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kz.market.domain.model.ThemeOptions
import kz.market.ui.icons.MarketIcons
import kz.market.ui.preview.ThemedPreview
import kz.market.ui.theme.MarketTheme

// -----------------------------------------------------------------------------
// Snackbar Host
// -----------------------------------------------------------------------------
@Composable
fun MarketSnackBarHost(
    hostState: MarketSnackBarHostState,
    modifier: Modifier = Modifier,
) {
    val visuals by hostState.currentVisuals

    visuals?.let { v ->
        SnackBarLayout(
            modifier = modifier,
            visuals = v,
            colors = MarketSnackBarDefaults.colors(),
            icons = MarketSnackBarDefaults.icons(),
            shape = MarketSnackBarDefaults.Shape,
            duration = v.toMillis(),
            onPerformAction = { v.performAction() },
            onDismiss = { hostState.dismiss() }
        )
    }
}

// -----------------------------------------------------------------------------
// Snackbar Layout
// -----------------------------------------------------------------------------
@Composable
private fun SnackBarLayout(
    visuals: SnackBarVisuals,
    colors: SnackBarColors,
    icons: SnackBarIcons,
    shape: Shape,
    modifier: Modifier = Modifier,
    duration: Long,
    onPerformAction: () -> Unit,
    onDismiss: () -> Unit
) {
    val palette = when (visuals.type) {
        SnackBarType.Success -> colors.success
        SnackBarType.Error -> colors.error
        SnackBarType.Warning -> colors.warning
        SnackBarType.Info -> colors.info
        SnackBarType.Neutral -> colors.neutral
    }

    val icon = when (visuals.type) {
        SnackBarType.Success -> icons.success
        SnackBarType.Error -> icons.error
        SnackBarType.Warning -> icons.warning
        SnackBarType.Info -> icons.info
        SnackBarType.Neutral -> null
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .semantics { liveRegion = LiveRegionMode.Polite }
            .animateContentSize(),
        color = palette.container,
        contentColor = palette.onContainer,
        shape = shape
    ) {
        Box {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalAlignment = Alignment.End
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    icon?.let {
                        Icon(
                            modifier = Modifier.size(MarketSnackBarDefaults.IconSize),
                            painter = it,
                            contentDescription = null
                        )
                        Spacer(Modifier.width(MarketSnackBarDefaults.IconSpacing))
                    }

                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 8.dp),
                        text = visuals.message,
                        style = MarketSnackBarDefaults.TextStyle,
                        fontWeight = FontWeight.Bold
                    )

                    if (!visuals.actionsOnNewLine) {
                        SnackBarActions(
                            visuals = visuals,
                            palette = palette,
                            onPerformAction = onPerformAction,
                            onDismiss = onDismiss
                        )
                    }
                }

                if (visuals.actionsOnNewLine) {
                    SnackBarActions(
                        visuals = visuals,
                        palette = palette,
                        onPerformAction = onPerformAction,
                        onDismiss = onDismiss
                    )
                }
            }

            if (!visuals.withDismissAction) {
                SnackBarTimer(
                    modifier = Modifier.align(Alignment.BottomStart),
                    color = palette.onContainer,
                    duration = duration
                )
            }
        }
    }
}

// -----------------------------------------------------------------------------
// Snackbar Actions (Action + Dismiss Button)
// -----------------------------------------------------------------------------
@Composable
private fun SnackBarActions(
    visuals: SnackBarVisuals,
    palette: SnackBarColorSet,
    onPerformAction: () -> Unit,
    onDismiss: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        visuals.actionLabel?.takeIf { it.isNotEmpty() }?.let { label ->
            Button(
                modifier = Modifier.height(MarketSnackBarDefaults.ButtonHeight),
                onClick = {
                    onPerformAction()
                    onDismiss()
                },
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = palette.onContainer
                ),
                shape = MarketSnackBarDefaults.Shape
            ) {
                Text(
                    text = label,
                    style = MarketSnackBarDefaults.ActionButtonStyle,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        if (visuals.withDismissAction) {
            Spacer(Modifier.width(MarketSnackBarDefaults.IconSpacing))
            IconButton(
                modifier = Modifier.size(MarketSnackBarDefaults.ButtonHeight),
                onClick = onDismiss,
                colors = IconButtonDefaults.iconButtonColors(contentColor = palette.onContainer),
                shape = MarketSnackBarDefaults.Shape
            ) {
                Icon(
                    modifier = Modifier.size(MarketSnackBarDefaults.IconButtonSize),
                    painter = MarketIcons.X,
                    contentDescription = "Dismiss"
                )
            }
        }
    }
}

@Composable
internal fun SnackBarTimer(
    duration: Long,
    color: Color,
    modifier: Modifier = Modifier
) {
    if (duration == Long.MAX_VALUE) return

    val progress = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        progress.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = duration.toInt(), easing = LinearEasing)
        )
    }

    Box(
        modifier = modifier
            .fillMaxWidth(progress.value)
            .height(3.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(color)
    )
}

// -----------------------------------------------------------------------------
// Previews
// -----------------------------------------------------------------------------
@ThemedPreview
@Composable
fun SnackBarPreviewAll() {
    MarketTheme(themeOption = ThemeOptions.LIGHT) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            SnackBarType.entries.forEach { type ->
                SnackBarLayout(
                    visuals = SnackBarVisuals(
                        type = type,
                        actionsOnNewLine = type == SnackBarType.Warning, // пример
                        message = "${type.name} SnackBar",
                        actionLabel = "Action",
                        duration = SnackBarDuration.Indefinite,
                        withDismissAction = true,
                        performAction = {}
                    ),
                    colors = MarketSnackBarDefaults.colors(),
                    icons = MarketSnackBarDefaults.icons(),
                    shape = MarketSnackBarDefaults.Shape,
                    duration = 0L,
                    onPerformAction = {},
                    onDismiss = {}
                )
            }
        }
    }
}
