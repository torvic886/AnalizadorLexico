package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem
import kotlin.math.exp

class Condicion3() {
    var condicion: Token? = null
    var expRel: ExpresionRelacional3? = null
    var expLo: ExpresionLogica3? = null
    var negacion: Token? = null

    constructor(expresionRel: ExpresionRelacional3, negacion: Token?) : this() {
        expRel = expresionRel
        this.negacion = negacion
    }

    constructor(expresionLog: ExpresionLogica3, negacion: Token?) : this() {
        expLo = expresionLog
        this.negacion = negacion
    }

    constructor(booleano: Token, negacion: Token?) : this() {
        condicion = booleano
        this.negacion = negacion
    }

    fun getJavaCode(): String {
        var codigo = ""
        if (negacion != null) {
            codigo += negacion!!.getJavaCode()
        }
        when {
            expLo != null -> {
                codigo += expLo!!.getJavaCode()
            }
            expRel != null -> {
                codigo += expRel!!.getJavaCode()
            }
            condicion != null -> {
                codigo += condicion!!.getJavaCode()
            }
        }
        return codigo
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

        /* if (negacion != null) {
             raiz.children.add(negacion!!.getArbolVisual())
         }*/

        return raiz
    }

    override fun toString(): String {
        return "Condicion2(condicion=$condicion, expRel=$expRel, expLo=$expLo, negacion=$negacion)"
    }


}