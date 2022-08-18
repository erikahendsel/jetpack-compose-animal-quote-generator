package com.erikahendsel.cuteanimalquotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.erikahendsel.cuteanimalquotes.ui.theme.CuteAnimalQuotesTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            CuteAnimalQuotesTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    val viewModel: MainViewModel = hiltViewModel()
                    val animal = viewModel.state.value.animal
                    val isLoading = viewModel.state.value.isLoading
                    Box(
                    ) {
                        animal?.let {
                            Image(
                                painter = rememberImagePainter(
                                    data = animal.imageUrl,
                                    builder = {
                                        crossfade(true)
                                    },
                                ),
                                contentDescription = "Animal",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .drawWithCache {
                                        val gradient = Brush.verticalGradient(
                                            colors = listOf(Color.Transparent, Color.Black),
                                            startY = size.height / 3,
                                            endY = size.height
                                        )
                                        onDrawWithContent {
                                            drawContent()
                                            drawRect(gradient, blendMode = BlendMode.Multiply)
                                        }
                                    }
                            )
                            Column(
                                modifier = Modifier
                                    .padding(16.dp, 16.dp, 16.dp, 160.dp)
                                    .fillMaxWidth()
                                    .background(Color.Black)
                                    .padding(16.dp)
                                    .align(Alignment.BottomCenter)
                            ) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = animal.name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(text = "\"${animal.quote}\"", fontStyle = FontStyle.Italic, color = Color.White)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(text = "- ${animal.quoteAuthor}", color = Color.White)
                                Spacer(modifier = Modifier.height(8.dp))
                            }

                        }

                        Button(
                            onClick = viewModel::getRandomAnimal,
                            modifier = Modifier.align(Alignment.BottomCenter)
                                .padding(0.dp, 0.dp, 0.dp, 64.dp)
                        ) {
                            Text(text = "Generate random quote")
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        if (isLoading) {
                            CircularProgressIndicator()
                        }
                    }

                }
            }
        }
    }
}
