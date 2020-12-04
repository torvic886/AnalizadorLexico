package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class ValorNumerico3(var valor:Token) {
    override fun toString(): String {
        return "ValorNumerico(valor=$valor)"
    }
    fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Valor Numérico")
        if (valor != null) {
            raiz.children.add(TreeItem("Valor: ${valor.lexema}"))
        }

        return raiz
    }
    fun getJavaCode(): String {
        return valor.getJavaCode()
    }

}