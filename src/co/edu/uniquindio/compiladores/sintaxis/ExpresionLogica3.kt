package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class ExpresionLogica3():Expresion3()
{
    var expresionRelacional: ExpresionRelacional3? = null
    var operadorLogico:Token? = null
    var expresionRelacional2: ExpresionRelacional3? = null
    var centi: Token? = null

    override fun toString(): String {
        return "ExpresionLogica(expresionRelacional=$expresionRelacional, operadorLogico=$operadorLogico, expresionRelacional2=$expresionRelacional2)"
    }
    constructor( expresionRelacional: ExpresionRelacional3, operadorLogico:Token, expresionRelacional2: ExpresionRelacional3 ): this() {
        this.expresionRelacional = expresionRelacional
        this.operadorLogico = operadorLogico
        this.expresionRelacional2 = expresionRelacional2
    }
    constructor(centi: Token): this() {
        this.centi = centi
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito) {
        if (expresionRelacional2 != null && expresionRelacional != null) {
            expresionRelacional2!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
            expresionRelacional!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        }
    }
    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Expresión Lógica")

        if (operadorLogico != null) {
            var raizOp = TreeItem("Op. Lógico: ${operadorLogico!!.lexema}")
            if (expresionRelacional != null) {
                raizOp.children.add( expresionRelacional!!.getArbolVisual() )
            }
            if (expresionRelacional2 != null) {
                raizOp.children.add( expresionRelacional2!!.getArbolVisual() )
            }
            raiz.children.add( raizOp )
        }
        if (centi != null) {
            raiz.children.add(TreeItem("Centi: ${centi!!.lexema}"))
        }

        return raiz
    }

    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito: Ambito, listaErrores: ArrayList<Error>  ): String {
        return "centi"
    }
}