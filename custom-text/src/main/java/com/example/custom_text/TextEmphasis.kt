package com.example.custom_text

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

val boldRegex = Regex("(?<!\\*)\\*\\*(?!\\*).*?(?<!\\*)\\*\\*(?!\\*)")

@Composable
fun TextEmphasis(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center,
    fontSize: TextUnit = 14.sp,
    annotatedFontSize: TextUnit = 14.sp,
    annotatedFontColor: Color = Color.Black,
    style: TextStyle = MaterialTheme.typography.body1,
) {

    var results: MatchResult? = boldRegex.find(text)

    val boldIndexes = mutableListOf<Pair<Int, Int>>()

    val keywords = mutableListOf<String>()

    var customText = text

    while (results != null) {
        keywords.add(results.value)
        results = results.next()
    }

    keywords.forEach { keyword ->
        val indexOf = customText.indexOf(keyword)

        val newKeyword = keyword.removeSurrounding("**")

        customText = customText.replace(keyword, newKeyword)

        boldIndexes.add(Pair(indexOf, indexOf + newKeyword.length))

    }

    //generate a custom text with bold font in a sentence
    val annotatedString = buildAnnotatedString {
        append(customText)

        boldIndexes.forEach {
            addStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold,
                    color = annotatedFontColor,
                    fontSize = annotatedFontSize,
                ),
                start = it.first,
                end = it.second
            )
        }
    }

    Text(
        modifier = modifier,
        fontSize = fontSize,
        text = annotatedString,
        style = style,
        textAlign = textAlign
    )
}