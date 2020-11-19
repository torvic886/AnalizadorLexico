package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem


class SentenciaInvocacionFuncion3(var nombreFuncion: Token, var argumentos:ArrayList<Expresion3>):Sentencia3() {
    override fun toString(): String {
        return "SentenciaInvocacionFuncion(nombreFuncion=$nombreFuncion, argumentos=$argumentos)"
    }
    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Invocación Función")

        raiz.children.add(TreeItem("Nombre: ${nombreFuncion.lexema}"))

        var raizArg = TreeItem("Argumentos")
        for (a in argumentos) {
            raizArg.children.add( a.getArbolVisual() )
        }
        raiz.children.add(raizArg)
        return raiz
    }
}