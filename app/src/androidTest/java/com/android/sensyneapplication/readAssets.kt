package com.android.sensyneapplication

import android.content.Context
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

fun String.readAssets(context: Context, fileName: String): String {
    val CHARACTER_NEWLINE = '\n'
    var inputStream: InputStream = context.assets.open(fileName)
    val r = BufferedReader(InputStreamReader(inputStream))
    val total = StringBuilder()

    var line: String?
    while (r.readLine().also { line = it } != null) {
        total.append(line).append(CHARACTER_NEWLINE)
    }
    return total.toString()
}
