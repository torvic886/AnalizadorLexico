package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class ExpresionCadena3(var cadena:Token, var expresion:Expresion3?):Expresion3() {
    override fun toString(): String {
        return "ExpresionCadena(cadena=$cadena, expresion=$expresion)"
    }
    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Expresión Cadena")

        return raiz
    }
}