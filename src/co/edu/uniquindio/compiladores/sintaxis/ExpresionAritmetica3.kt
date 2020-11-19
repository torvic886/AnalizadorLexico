package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

open class ExpresionAritmetica3(var terminos:ArrayList<Termino3>):Expresion3()
{

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Expresión Aritmética")

        for (termino in terminos) {
            raiz.children.add(termino.getArbolVisual())
        }

        return raiz
    }

    override fun toString(): String {
        return "ExpresionAritmetica2(terminos=$terminos)"
    }
}