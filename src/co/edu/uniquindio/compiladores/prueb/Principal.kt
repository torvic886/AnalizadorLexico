package co.edu.uniquindio.compiladores.prueb

import co.edu.uniquindio.compiladores.lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.sintaxis.AnalizadorSintactico6

fun main() {
    val a = AnalizadorLexico(
            "" +
                    "cad: cadena_ " +
                    "Conjunto is entero: listaV_"+
                    "entero: cantidad_"+
                    "mtd is dec conRetorno [entero: a; entero: b]" +
                    "-->" +

                    "{ listaV @ entero: {$10,0_c; $101,0_c; $20,0_c}_"+
                    "Consultar[|Digite valor de: |, listaV]_"+
                    "invocacion2[|cadena|, a <+> b]_"+
                    "msg[|La suma de a y b, es: |, a <+> b]_"+
                    "suma @ a <+> b_"+
                    "invocacion[[[a<*>b ?? a<*>b] AND [a<*>b ?? a<*>b]]]_" +
                    "entero:resul_"+
                    "check [a <? $10]" +
                    "-->" +
                        "msg[|a es menor o igual aún |]_"+
                    "<-- other -->" +
                    "resul @ $12_"+
                    "<--" +

                    "whereas [resul<*>$10 ?? b] do -->"+
                        "resul @- resul <+> $2_"+
                    "<--"+

                    "dev resul_"+
                    "<--"+

                    "mtd sinRetorno []" +
                    "-->" +


                    "<--"

    )
    a.analizar()
    print(a.listaTokens)
    print("")
    print("_______________________________________________________________________________________________________")
    val b = AnalizadorSintactico6((a.listaTokens))
    print(b.esUnidadDeCompilacion5())
    print(b.listaErrores)
}