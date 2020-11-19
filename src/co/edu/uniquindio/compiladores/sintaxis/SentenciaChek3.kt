package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

class SentenciaChek3(var condicion: Condicion3, var bloqueSentencias: ArrayList<Sentencia3>, var other: Other3?):Sentencia3() {
    override fun toString(): String {
        return "SentenciaChek(condicion=$condicion, bloqueSentencias=$bloqueSentencias, other=$other)"
    }
    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Sentencia Check")

        raiz.children.add( condicion.getArbolVisual() )

        var raizSentencias = TreeItem("Sentencias verdaderas")
        for (s in bloqueSentencias) {
            raizSentencias.children.add( s.getArbolVisual() )
        }
        raiz.children.add(raizSentencias)

        if (other != null) {
            raiz.children.add( other!!.getArbolVisual() )
        }
        return raiz
    }
}