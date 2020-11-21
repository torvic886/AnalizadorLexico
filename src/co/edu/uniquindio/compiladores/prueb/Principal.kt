package co.edu.uniquindio.compiladores.prueb

import co.edu.uniquindio.compiladores.lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.sintaxis.AnalizadorSintactico6

fun main() {
    val a = AnalizadorLexico(
            "cad: cadena_ " +
                    "Conjunto is entero: arreglo_"+
                    "entero: cantidad_"+
                    "mtd is entero suma [entero:b; entero: b]" +
                    "-->" +

                    "{ lista @ entero: {$10,0_c; $101,0_c; $20,0_c}_"+
                    "Consultar[a]_"+
                    "sdasd[|cadena|, a <+> b]_"+
                    "msg[|cadena|, a <+> b]_"+
                    "suma @ a <+> b_"+
                    "invocacion[[[a<*>b ?? a<*>b] AND [a<*>b ?? a<*>b]]]_" +
                    "check [a <? b]" +
                    "-->" +
                    "<-- other -->" +
                    "entero:a_"+
                    "a @ $12_"+
                    "<--" +

                    "whereas [a<*>$10 ?? b] do -->"+
                    "<--"+

                    "dev a_"+

                    "<--")
    a.analizar()
    print(a.listaTokens)
    print("")
    print("_______________________________________________________________________________________________________")
    val b = AnalizadorSintactico6((a.listaTokens))
    print(b.esUnidadDeCompilacion5())
    print(b.listaErrores)
}