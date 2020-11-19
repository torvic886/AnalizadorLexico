package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

open class Termino3(var factores:ArrayList<Factor3>){
    override fun toString(): String {
        return "Termino(factores=$factores)"
    }
    fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Términos")

        var raizFactores = TreeItem("Factores")
        for (factor in factores) {
            raizFactores.children.add( factor.getArbolVisual() )
        }
        raiz.children.add(raizFactores)
        return raiz
    }
}