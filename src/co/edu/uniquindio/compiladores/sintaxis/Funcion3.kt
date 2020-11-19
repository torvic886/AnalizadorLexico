package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class Funcion3(var nombreFuncion: Token, var tipoRetorno:Token, var listaParametros:ArrayList<Parametro3>, var bloqueSentencias:ArrayList<Sentencia3>) {
    override fun toString(): String {
        return "Funcion(nombreFuncion=$nombreFuncion, tipoRetorno=$tipoRetorno, listaParametros=$listaParametros, bloqueSentencias=$bloqueSentencias)"
    }

    fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Función")

        raiz.children.add(TreeItem("Nombre: ${nombreFuncion.lexema}"))
        raiz.children.add(TreeItem("Tipo retorno: ${tipoRetorno.lexema}"))
        // Para los parámetros
        var raizParametro = TreeItem("Parámetros")
        for (d in listaParametros) {
            raizParametro.children.add( d.getArbolVisual() )
        }
        raiz.children.add(raizParametro)

        // Para las sentencias
        var raizSentencias = TreeItem("Sentencias")
        for (f in bloqueSentencias) {
            raizSentencias.children.add( f.getArbolVisual() )
        }
        raiz.children.add(raizSentencias)
        return raiz
    }

}