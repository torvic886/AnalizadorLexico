package co.edu.uniquindio.compiladores.prueb

import co.edu.uniquindio.compiladores.lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.sintaxis.AnalizadorSintactico6

fun main() {
    val a = AnalizadorLexico(
            "" +
                    "cad: cadena @ $12 _"

    )
    a.analizar()
    print(a.listaTokens)
    print("")
    print("_______________________________________________________________________________________________________")
    val b = AnalizadorSintactico6((a.listaTokens))
    print(b.esUnidadDeCompilacion5())
    print(b.listaErrores)
}