// ============================================================
//  THE PASSWORD OVERLORD — Validador de Senhas Dinâmico
//  Disciplina: Soluções Mobile | Prof. Thyerri Mezzari
//  CENTRO UNIVERSITÁRIO SATC — Engenharia de Software
// ============================================================

data class Requisito(
    val mensagemErro: String,
    val validacao: (String) -> Boolean
)

// ─── Utilitários ─────────────────────────────────────────────

fun toRomano(n: Int): String {
    val valores = listOf(1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1)
    val simbolos = listOf("M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I")
    var resultado = ""
    var restante = n
    for (i in valores.indices) {
        while (restante >= valores[i]) {
            resultado += simbolos[i]
            restante -= valores[i]
        }
    }
    return resultado
}

fun somaDigitos(senha: String): Int =
    senha.filter { it.isDigit() }.sumOf { it.digitToInt() }

fun contemEmoji(senha: String): Boolean =
    senha.codePoints().anyMatch { cp ->
        cp in 0x1F300..0x1FAFF ||  // Emoticons, símbolos diversos, pictogramas
        cp in 0x2600..0x27BF  ||   // Símbolos variados (☀ ❄ ✅ etc)
        cp in 0x2B00..0x2BFF  ||   // Setas e símbolos suplementares
        cp in 0x1F000..0x1F02F     // Mahjong / dominó
    }

fun contemPalindromo(senha: String, tamanhoMinimo: Int = 3): Boolean {
    val pts = senha.codePoints().toArray()
    for (i in pts.indices) {
        for (j in i + tamanhoMinimo..pts.size) {
            val sub = pts.sliceArray(i until j)
            if (sub.contentEquals(sub.reversedArray())) return true
        }
    }
    return false
}

// ─── Regras ──────────────────────────────────────────────────

fun construirRegras(): List<Requisito> {
    val diaAtual = java.time.LocalDate.now().dayOfMonth
    val diaRomano = toRomano(diaAtual)

    return listOf(

        // ── Obrigatórias ──────────────────────────────────────

        Requisito("A senha precisa ter pelo menos 5 caracteres.") {
            it.length >= 5
        },

        Requisito("A senha deve conter pelo menos uma letra MAIÚSCULA.") {
            it.any { c -> c.isUpperCase() }
        },

        Requisito("A senha deve conter pelo menos um número.") {
            it.any { c -> c.isDigit() }
        },

        Requisito("A senha deve conter a palavra 'SATC' (maiúsculas ou minúsculas).") {
            it.contains("satc", ignoreCase = true)
        },

        Requisito("A senha deve conter o ano do Hexa do Brasil") {
            it.contains("2026")
        },

        Requisito("A senha deve conter pelo menos um emoji (ex: ❄ 🔥 ⭐).") {
            contemEmoji(it)
        },

        // ── Criativas ─────────────────────────────────────────

        Requisito(
            "A senha deve conter o dia de hoje em algarismos romanos."
        ) {
            it.contains(diaRomano, ignoreCase = true)
        },

        Requisito(
            "A soma de todos os dígitos da senha deve ser pelo menos 20. "
        ) {
            somaDigitos(it) >= 20
        },

        Requisito(
            "A senha deve conter seu próprio comprimento como número. " +
            "Ex: se a senha tiver 42 caracteres, deve conter '42'."
        ) {
            it.contains(it.length.toString())
        },

        Requisito(
            "A senha deve conter um palíndromo de pelo menos 3 caracteres " +
            "(ex: 'aba', 'racecar', '121', 'ABBA')."
        ) {
            contemPalindromo(it)
        }
    )
}

// ─── Main ─────────────────────────────────────────────────────

fun main() {
    println(
        """
        ╔══════════════════════════════════════════════════════╗
        ║          🔐  THE PASSWORD OVERLORD  🔐               ║
        ║   Prove que você é digno de criar a senha absoluta!  ║
        ╚══════════════════════════════════════════════════════╝
        """.trimIndent()
    )

    val listaDeRegras = construirRegras()
    var senhaAprovada = false

    do {
        println("\n➤  Digite sua senha:")
        val entrada = readLine() ?: ""

        var erroEncontrado: String? = null

        for (regra in listaDeRegras) {
            if (!regra.validacao(entrada)) {
                erroEncontrado = regra.mensagemErro
                break
            }
        }

        if (erroEncontrado != null) {
            println("\n❌  REGRA VIOLADA: $erroEncontrado")
        } else {
            println(
                """

                ✅  SENHA ACEITA PELO OVERLORD!
                ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
                   Senha: $entrada
                   Comprimento: ${entrada.length} caracteres
                   Soma dos dígitos: ${somaDigitos(entrada)}
                ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
                """.trimIndent()
            )
            senhaAprovada = true
        }
    } while (!senhaAprovada)
}
