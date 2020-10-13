package co.edu.uniquindio.compiladores.prueba

import co.edu.uniquindio.compiladores.lexico.AnalizadorLexico

fun main( args:Array<String>){
    var lexico:AnalizadorLexico = AnalizadorLexico("@ @* <? <?? |asd| .sa. <+> ajajjaja victor ")
    lexico.analizar()
    print( lexico.listaTokens )
}