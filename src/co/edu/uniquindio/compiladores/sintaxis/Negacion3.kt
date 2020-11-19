package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class Negacion3(var condicion: Any?) {
    override fun toString(): String {
        return "Negacion(condicion=$condicion)"
    }
    fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Negación")

        if (condicion is Token) {
            raiz.children.add(TreeItem("Booleano: ${(condicion as Token).lexema}"))
        }
        if (condicion is ExpresionRelacional3) {
            raiz.children.add((condicion as ExpresionRelacional3).getArbolVisual())
        }

        if (condicion is ExpresionLogica3) {
            raiz.children.add((condicion as ExpresionLogica3).getArbolVisual())
        }
        return raiz
    }
}