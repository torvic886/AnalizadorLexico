package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class Factor3(var identificador:Token?, var valorNum: ValorNumerico3?, var expresionAritme: ExpresionAritmetica3?){

    fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Factor")

        if (identificador != null) {
            raiz.children.add(TreeItem("Identificador: ${identificador!!.lexema}"))
        }
        if (valorNum != null) {
            raiz.children.add( valorNum!!.getArbolVisual() )
        }
        if (expresionAritme != null) {
            raiz.children.add( expresionAritme!!.getArbolVisual() )
        }
        return raiz
    }

    override fun toString(): String {
        return "Factor2(identificador=$identificador, valorNum=$valorNum, expresionAritme=$expresionAritme)"
    }
}