package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

open class Termino3() {
    var factor1: Factor3? = null
    var factor2: Factor3? = null
    var operador: Token? = null
    var termino: Termino3? = null

    constructor(factor1: Factor3, operador: Token, factor2: Factor3) : this() {
        this.factor1 = factor1
        this.factor2 = factor2
        this.operador = operador
    }

    constructor(factor1: Factor3) : this() {
        this.factor1 = factor1
    }

    constructor(termino: Termino3) : this() {
        this.termino = termino
    }

    constructor(termino: Termino3, operador: Token, factor1: Factor3) : this() {
        this.termino = termino
        this.operador = operador
        this.factor1 = factor1
    }

    fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito) {
        if (factor1 != null && factor2 != null) {
            factor1!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
            factor2!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        } else if (factor1 != null && termino != null) {
            factor1!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
            termino!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        } else if (factor1 != null) {
            factor1!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        } else if (termino != null) {
            termino!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        }
    }
    fun getJavaCode(): String {
        var codigo = ""
        when {
            factor1 != null && factor2 != null -> {
                codigo += "(" + factor1!!.getJavaCode()
                codigo += operador!!.getJavaCode()
                codigo += factor2!!.getJavaCode() + ")"
            }
            factor1 != null && termino != null -> {
                codigo += factor1!!.getJavaCode()
                codigo += operador!!.getJavaCode()
                codigo += "(" + termino!!.getJavaCode() + ")"
            }
            factor1 != null -> {
                codigo += factor1!!.getJavaCode()
            }
            termino != null -> {
                codigo += termino!!.getJavaCode()
            }
        }
        return codigo
    }

    fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito: Ambito, listaErrores: ArrayList<Error>): String {
        if (factor1 != null && factor2 != null) {
            if (factor1!!.obtenerTipo(tablaSimbolos, ambito, listaErrores) == "dec" || factor2!!.obtenerTipo(tablaSimbolos, ambito, listaErrores) == "dec") {
                return "dec"
            }
            return "entero"
        } else if (factor1 != null && termino != null) {
            if (factor1!!.obtenerTipo(tablaSimbolos, ambito, listaErrores) == "dec" || termino!!.obtenerTipo(tablaSimbolos, ambito, listaErrores) == "dec") {
                return "dec"
            }
            return "entero"
        } else if (factor1 != null) {
            return factor1!!.obtenerTipo(tablaSimbolos, ambito, listaErrores)
        } else if (termino != null) {
            return termino!!.obtenerTipo(tablaSimbolos, ambito, listaErrores)
        }
        return ""
    }

    fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Términos")
        if (operador != null) {
            var raizOp = TreeItem("Operador: ${operador!!.lexema}")
            if (factor1 != null && factor2 != null) {
                raizOp.children.add(factor1!!.getArbolVisual())
                raizOp.children.add(factor2!!.getArbolVisual())
            }
            if (termino != null && factor1 != null) {
                raizOp.children.add(termino!!.getArbolVisual())
                raizOp.children.add(factor1!!.getArbolVisual())
            }
            raiz.children.add(raizOp)
            return raiz
        }
        if (termino != null) {
            raiz.children.add(termino!!.getArbolVisual())
        }
        if (factor1 != null) {
            raiz.children.add(factor1!!.getArbolVisual())
        }
        return raiz
    }

    override fun toString(): String {
        return "Termino3(factor1=$factor1, factor2=$factor2, operador=$operador, termino=$termino)"
    }

}