package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

class SentenciaWhereas3(var condicion: Condicion3, var sentencias: ArrayList<Sentencia3>):Sentencia3(){
    override fun toString(): String {
        return "SentenciaWhereas2(condicion=$condicion, sentencias=$sentencias)"
    }
    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Sentencia Whereas")

        raiz.children.add( condicion.getArbolVisual() )

        if (sentencias.size >= 1) {
            for (s in sentencias) {
                raiz.children.add( s.getArbolVisual() )
            }
        }
        return raiz
    }
}