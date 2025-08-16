package com.ichin23.salbum.ui.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.ichin23.salbum.R

/**
 * Um componente de avaliação com 5 estrelas que permite avaliações de meia estrela.
 *
 * @param rating A avaliação atual a ser exibida (entre 0.0f e 5.0f).
 * @param onRatingChanged Um callback que é invocado quando a avaliação muda.
 * @param modifier O modificador a ser aplicado a este layout.
 * @param starSize O tamanho de cada ícone de estrela.
 * @param starColor A cor dos ícones de estrela preenchidos.
 */
@Composable
fun FiveStarRating(
    rating: Double,
    onRatingChanged: (Double) -> Unit,
    modifier: Modifier = Modifier,
    starSize: Dp = 36.dp,
    starColor: Color = MaterialTheme.colorScheme.primary
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        // Itera para criar as 5 estrelas
        (1..5).forEach { starIndex ->
            // Determina qual ícone de estrela usar (cheia, metade, ou vazia)
            val icon = when {
                rating >= starIndex -> ImageVector.vectorResource(R.drawable.estrela_fill)
                rating >= starIndex - 0.5f -> ImageVector.vectorResource(R.drawable.estrela_half)
                else -> ImageVector.vectorResource(R.drawable.estrela_outlined)
            }

            Icon(
                imageVector = icon,
                contentDescription = "Estrela de avaliação",
                tint = starColor,
                modifier = Modifier
                    .size(starSize)

                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = { offset ->
                                // Calcula a nova avaliação com base na posição do toque
                                val newRating = if (offset.x < size.width / 2) {
                                    // O toque foi na primeira metade da estrela
                                    starIndex - 0.5
                                } else {
                                    // O toque foi na segunda metade da estrela
                                    starIndex.toDouble()
                                }

                                // Se o usuário tocar na mesma avaliação, zera a avaliação.
                                // Caso contrário, atualiza para a nova avaliação.
                                if (newRating == rating) {
                                    onRatingChanged(0.toDouble())
                                } else {
                                    onRatingChanged(newRating)
                                }
                            }
                        )
                    }
            )
            Spacer(
                Modifier.width(
                    if(starIndex<5){
                        8.dp
                    }else{
                        0.dp
                    }
                )
            )
        }
    }
}