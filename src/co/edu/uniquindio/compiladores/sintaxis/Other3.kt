package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

class Other3(var bloqueSentencias: ArrayList<Sentencia3>)
{
    override fun toString(): String {
        return "Other(bloqueSentencias=$bloqueSentencias)"
    }
    fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Other")
        var raizSentencias = TreeItem("Sentencias no verdaderas")
        if (bloqueSentencias.size >= 1) {
            for (s in bloqueSentencias) {
                raizSentencias.children.add( s.getArbolVisual() )
            }
        }
        raiz.children.add(raizSentencias)
        return raiz
    }
}