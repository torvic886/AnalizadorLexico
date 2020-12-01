package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class ExpresionLogica3(var expresionRelacional: ExpresionRelacional3, var operadorLogico:Token, var expresionRelacional2: ExpresionRelacional3 ):Expresion3()
{
    override fun toString(): String {
        return "ExpresionLogica(expresionRelacional=$expresionRelacional, operadorLogico=$operadorLogico, expresionRelacional2=$expresionRelacional2)"
    }
    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Expresión Lógica")

        var raizOp = TreeItem("Op. Lógico: ${operadorLogico.lexema}")
        raizOp.children.add( expresionRelacional.getArbolVisual() )
        raizOp.children.add( expresionRelacional2.getArbolVisual() )
        raiz.children.add( raizOp )
        return raiz
    }

    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito: String ): String {
        return "centi"
    }
}