package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

open class Expresion3 {
    open fun getArbolVisual(): TreeItem<String> {
        return TreeItem("Expresión")
    }
    open fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito: String ): String {
        return ""
    }

}