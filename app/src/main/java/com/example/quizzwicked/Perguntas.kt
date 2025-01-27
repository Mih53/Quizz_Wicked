package com.example.quizzwicked

class Perguntas {
    val questions: Array<String> = arrayOf(
        "Qual é o nome da protagonista de Wicked que é conhecida por sua pele verde?",
        "Qual é o nome da música icônica cantada por Elphaba enquanto ela 'voa' no palco?",
        "Qual é a escola de magia onde Elphaba e Glinda se conhecem?"
    )

    val choices: Array<Array<String>> = arrayOf(
        arrayOf("Elphaba", "Glinda", "Nessarose"),
        arrayOf("For Good", "Popular", "Defying Gravity"),
        arrayOf("Emerald Academy", "Shiz University", "Oz High")
    )

    val correctAnswers: Array<String> = arrayOf(
        "Elphaba",
        "Defying Gravity",
        "Shiz University"
    )
}
