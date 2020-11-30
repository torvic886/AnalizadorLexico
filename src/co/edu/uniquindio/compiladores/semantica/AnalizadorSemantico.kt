package co.edu.uniquindio.compiladores.semantica

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.sintaxis.UnidadDeCompilacion3

class AnalizadorSemantico (var unidadCompilacion: UnidadDeCompilacion3) {
    var listaErrores: ArrayList<Error> = ArrayList()
    var tablaSimbolos: TablaSimbolos = TablaSimbolos( listaErrores )

    fun llenarTablaSimbolos() {
        unidadCompilacion.llenarTablaSimbolos( tablaSimbolos, listaErrores )
    }
    fun analizarSemantica() {
        unidadCompilacion.analizarSemantica( tablaSimbolos, listaErrores )
    }
}