package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

class SentenciaRetorno3(var expresion: Expresion3):Sentencia3() {
    override fun toString(): String {
        return "SentenciaRetorno(expresion=$expresion)"
    }
    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Sentencia Retorno")

        raiz.children.add( expresion.getArbolVisual() )
        return raiz
    }
}