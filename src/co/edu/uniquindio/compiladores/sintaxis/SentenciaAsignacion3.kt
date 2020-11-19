package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class SentenciaAsignacion3(var nombre:Token, var operadorAsignacion: Token, var expresion: Expresion3):Sentencia3()
{
    override fun toString(): String {
        return "SentenciaAsignacion(nombre=$nombre, operadorAsignacion=$operadorAsignacion, expresion=$expresion)"
    }
    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Sentencia Asignación")

        var raizOp = TreeItem("Operador:  ${operadorAsignacion.lexema}")
        raizOp.children.add( TreeItem("Nombre: ${nombre.lexema}") )
        raizOp.children.add( expresion.getArbolVisual() )
        raiz.children.add( raizOp )
        return raiz
    }
}