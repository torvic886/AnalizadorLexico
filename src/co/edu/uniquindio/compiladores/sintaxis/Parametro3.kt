package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem


class Parametro3(var tipoDato: Token, var nombreParametro:Token) {
    override fun toString(): String {
        return "Parametro(tipoDato=$tipoDato, nombreParametro=$nombreParametro)"
    }
    fun getArbolVisual(): TreeItem<String> {
       return TreeItem("${tipoDato.lexema}: ${nombreParametro.lexema}")
    }
    fun getJavaCode(): String {
        return tipoDato.getJavaCode()+ " " + nombreParametro.getJavaCode()
    }
}