package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

open class Sentencia3 {
    open fun getArbolVisual(): TreeItem<String> {
        return TreeItem("Sentencia")
    }
    open  fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito ) {

    }
    open fun analizarSemantica( tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito ) {

    }
    open fun getJavaCode(): String {
        return ""
    }
}