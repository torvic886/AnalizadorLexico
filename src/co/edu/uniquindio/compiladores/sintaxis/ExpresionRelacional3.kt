package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class ExpresionRelacional3(var expresionAritmetica: ExpresionAritmetica3, var operardorRel:Token, var expresionAritmetica2: ExpresionAritmetica3):Expresion3() {
    override fun toString(): String {
        return "ExpresionRelacional(expresionAritmetica=$expresionAritmetica, operardorRel=$operardorRel, expresionAritmetica2=$expresionAritmetica2)"
    }
    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Expresión Relacional")
        var raizOp = TreeItem("Op. Relacional: ${operardorRel.lexema}")
        raizOp.children.add( expresionAritmetica.getArbolVisual() )
        raizOp.children.add( expresionAritmetica2.getArbolVisual() )
        raiz.children.add(raizOp)
        return raiz
    }
    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito: String ): String {
        return "centi"
    }
}