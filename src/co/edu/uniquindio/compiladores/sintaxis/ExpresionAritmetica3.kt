package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

open class ExpresionAritmetica3():Expresion3()
{
    var expArit1: ExpresionAritmetica3? = null
    var termino: Termino3? = null
    var termino2: Termino3? = null
    var operador: Token? = null

    constructor(expArit1: ExpresionAritmetica3, operador: Token, termino: Termino3):this() {
        this.expArit1 = expArit1
        this.termino = termino
        this.operador = operador
    }
    constructor(termino: Termino3): this() {
        this.termino = termino
    }
    constructor(expArit1: ExpresionAritmetica3):this() {
        this.expArit1 = expArit1
    }
    constructor(termino1: Termino3, operador: Token, termino2: Termino3): this() {
        this.termino = termino1
        this.operador = operador
        this.termino2 = termino2
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito) {
        if (termino2 != null && termino != null) {
            termino!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
            termino2!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        } else if (expArit1 != null && termino != null) {
            termino!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
            expArit1!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        } else if (termino != null) {
            termino!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        } else if (expArit1 != null) {
            expArit1!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        }
    }

    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito: Ambito, listaErrores: ArrayList<Error>  ): String {
        if (termino != null && termino2 != null) {
            if (termino!!.obtenerTipo(tablaSimbolos, ambito, listaErrores) == "dec" || termino2!!.obtenerTipo(tablaSimbolos, ambito, listaErrores) == "dec" ) {
                return "dec"
            }
            return "entero"
        } else if (expArit1 != null && termino !=null ) {
            if (expArit1!!.obtenerTipo(tablaSimbolos, ambito, listaErrores) == "dec" || termino!!.obtenerTipo(tablaSimbolos, ambito, listaErrores) == "dec" ) {
                return "dec"
            }
            return "entero"
        } else if (termino != null) {
            return termino!!.obtenerTipo(tablaSimbolos, ambito, listaErrores)
        } else if (expArit1 != null) {
            return expArit1!!.obtenerTipo(tablaSimbolos, ambito, listaErrores)
        }
        return ""
    }
    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Expresión Aritmética")

        if (operador != null) {
            val raizOperador = TreeItem("Operador: ${operador!!.lexema}")
            if (termino != null && termino2 != null) {
                raizOperador.children.add(termino!!.getArbolVisual())
                raizOperador.children.add(termino2!!.getArbolVisual())
            }
            if (expArit1 != null && termino != null) {
                raizOperador.children.add(expArit1!!.getArbolVisual())
                raizOperador.children.add(termino!!.getArbolVisual())
            }
            raiz.children.add(raizOperador)
            return raiz
        }
        if (expArit1 != null) {
            raiz.children.add(expArit1!!.getArbolVisual())
            return raiz
        }
        if (termino != null) {
            raiz.children.add(termino!!.getArbolVisual())
            return raiz
        }
        if (termino2 != null) {
            raiz.children.add(termino2!!.getArbolVisual())
            return raiz
        }
        return raiz
    }
    override fun toString(): String {
        return "ExpresionAritmetica3(expArit1=$expArit1, termino=$termino, termino2=$termino2, operador=$operador)"
    }


}