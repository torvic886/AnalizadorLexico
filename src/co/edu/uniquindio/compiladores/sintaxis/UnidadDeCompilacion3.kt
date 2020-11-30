package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class UnidadDeCompilacion3(var listaDeclVar:ArrayList<Sentencia3>, var listaFunciones:ArrayList<Funcion3>) {
    override fun toString(): String {
        return "UnidadDeCompilacion(listaDeclVar=$listaDeclVar, listaFunciones=$listaFunciones)"
    }

    fun getArbolVisual():TreeItem<String> {
        var raiz = TreeItem("Unidad de Compilación")

        for (d in listaDeclVar) {
            raiz.children.add( d.getArbolVisual() )
        }

        for (f in listaFunciones) {
            raiz.children.add( f.getArbolVisual() )
        }
        return raiz
    }
    fun llenarTablaSimbolos( tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error> ) {
        for ( f in listaFunciones ) {
            f.llenarTablaSimbolos( tablaSimbolos, listaErrores, "unidadCompilacion" )
        }
    }
    fun analizarSemantica( tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error> ) {

    }
}