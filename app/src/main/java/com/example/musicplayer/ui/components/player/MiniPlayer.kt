package com.example.musicplayer.ui.components.player

import androidx.compose.foundation.Image
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MusicNote
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.musicplayer.ui.theme.AppIcons
import com.example.musicplayer.ui.theme.AppShapes
import com.example.musicplayer.ui.viewmodel.MiniPlayerViewModel

@Composable
fun MiniPlayer(
    modifier: Modifier = Modifier,
    onExpandClick: () -> Unit,
    viewModel: MiniPlayerViewModel = hiltViewModel()
) {
    val currentSong by viewModel.currentSong.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()
  val progress by viewModel.progress.collectAsState()

    val cornerRadius = 20.dp

    Card(
        modifier = modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp)
          .height(64.dp)
          .clip(AppShapes.large)
          .clickable { onExpandClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        shape = AppShapes.large
    ) {
        Column {
            MiniPlayerContent(
                isPlaying = isPlaying,
                onPlayPauseClick = { viewModel.togglePlayPause() },
                viewModel = viewModel,
                modifier = Modifier
                  .weight(1f)
                  .padding(horizontal = 16.dp)
            )

            Box(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(horizontal = cornerRadius + 5.dp)
            ) {
                MiniProgressTrack(progress = progress)
            }
        }
    }
}

@Composable
private fun MiniPlayerContent(
    isPlaying: Boolean,
    onPlayPauseClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel : MiniPlayerViewModel
) {
    Row(
        modifier = modifier
          .fillMaxSize()
          .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SongInfo(modifier = Modifier.weight(1f),     viewModel = viewModel
        )
        PlayPauseButton(
            isPlaying = isPlaying,
            onClick = onPlayPauseClick
        )
    }
}

@Composable
private fun SongInfo(
    modifier: Modifier = Modifier,
    viewModel: MiniPlayerViewModel
) {
    val songTitle by viewModel.songTitle.collectAsState()
    val artist by viewModel.artist.collectAsState()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        MiniAlbumArt(viewModel = viewModel)
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = songTitle,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.basicMarquee(
                    animationMode = MarqueeAnimationMode.Immediately,
                    iterations = Int.MAX_VALUE
                )

            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = artist,
                color = Color.White.copy(alpha = 0.7f),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun MiniAlbumArt(viewModel: MiniPlayerViewModel) {
    val albumArtUri by viewModel.artUri.collectAsState()

    Box(
        modifier = Modifier
          .size(44.dp)
          .background(
            color = Color.White.copy(alpha = 0.2f),
            shape = CircleShape
          ),
        contentAlignment = Alignment.Center
    ) {
        if (albumArtUri != null) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(albumArtUri)
                        .build()
                ),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            Icon(
                Icons.Rounded.MusicNote,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.7f),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}


@Composable
private fun PlayPauseButton(
    isPlaying: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
          .size(44.dp)
          .clip(CircleShape)
          .background(
            color = Color.White.copy(alpha = 0.2f),
            shape = CircleShape
          )
          .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(
                id = if (isPlaying) AppIcons.pause else AppIcons.playFilled
            ),
            contentDescription = if (isPlaying) "Pause" else "Play",
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.size(18.dp)
        )
    }
}
