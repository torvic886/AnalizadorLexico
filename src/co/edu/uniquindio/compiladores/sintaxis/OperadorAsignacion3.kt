package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class OperadorAsignacion3(var operadorAsignacion: Token)
{
    override fun toString(): String {
        return "OperadorAsignacion(operadorAsignacion=$operadorAsignacion)"
    }
    fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Operador Asignación")

        return raiz
    }
}