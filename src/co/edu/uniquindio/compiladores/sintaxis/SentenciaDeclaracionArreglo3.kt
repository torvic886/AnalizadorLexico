package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class SentenciaDeclaracionArreglo3(var tipoDato: Token, var nombreVar: Token):Sentencia3() {
    override fun toString(): String {
        return "SentenciaDeclaracionArreglo3(tipoDato=$tipoDato, nombreVar=$nombreVar)"
    }
    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Declaración Conjunto")

        raiz.children.add(TreeItem("Tipo dato: ${tipoDato.lexema}"))
        raiz.children.add(TreeItem("Variable: ${nombreVar.lexema}"))
        return raiz
    }
}