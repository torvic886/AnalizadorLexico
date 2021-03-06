package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class ExpresionRelacional3(var expresionAritmetica: ExpresionAritmetica3, var operardorRel:Token, var expresionAritmetica2: ExpresionAritmetica3):Expresion3() {
    override fun toString(): String {
        return "ExpresionRelacional(expresionAritmetica=$expresionAritmetica, operardorRel=$operardorRel, expresionAritmetica2=$expresionAritmetica2)"
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito) {
        if (expresionAritmetica != null && expresionAritmetica2 != null) {
            expresionAritmetica.analizarSemantica(tablaSimbolos, listaErrores, ambito)
            expresionAritmetica2.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        }
    }
    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Expresión Relacional")
        var raizOp = TreeItem("Op. Relacional: ${operardorRel.lexema}")
        raizOp.children.add( expresionAritmetica.getArbolVisual() )
        raizOp.children.add( expresionAritmetica2.getArbolVisual() )
        raiz.children.add(raizOp)
        return raiz
    }
    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito: Ambito, listaErrores: ArrayList<Error>  ): String {
        return "centi"
    }
    override fun getJavaCode(): String {
        var codigo = ""
        if (expresionAritmetica != null && expresionAritmetica2 != null) {
            codigo += expresionAritmetica!!.getJavaCode()
            codigo += operardorRel.getJavaCode()
            codigo += expresionAritmetica2!!.getJavaCode()
        }
        return codigo
    }
}