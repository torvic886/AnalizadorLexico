package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Condicion3() {
    var condicion: Token? = null
    var expRel: ExpresionRelacional3? = null
    var expLo: ExpresionLogica3? = null
    var negacion: Negacion3? = null

    constructor(expresionRel: ExpresionRelacional3) : this() {
        expRel = expresionRel
    }

    constructor(expresionLog: ExpresionLogica3) : this() {
        expLo = expresionLog
    }

    constructor(booleano: Token) : this() {
        condicion = booleano
    }

    fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito) {
        if (expLo != null) {
            expLo!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        } else if (expRel != null) {
            expRel!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        }
    }

    fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Condición")

        if (condicion != null) {
            raiz.children.add(TreeItem("Booleano: ${condicion!!.lexema}"))
        }

        if (expRel != null) {
            raiz.children.add(expRel!!.getArbolVisual())
        }

        if (expLo != null) {
            raiz.children.add(expLo!!.getArbolVisual())
        }

        if (negacion != null) {
            raiz.children.add(negacion!!.getArbolVisual())
        }

        return raiz
    }

    override fun toString(): String {
        return "Condicion2(condicion=$condicion, expRel=$expRel, expLo=$expLo, negacion=$negacion)"
    }


}