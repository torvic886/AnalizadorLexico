package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class ExpresionCadena3() : Expresion3() {
    var cadena: Token? = null
    var expresionA: ExpresionAritmetica3? = null
    var expresionCadena: ExpresionCadena3? = null

    constructor(cadena: Token) : this() {
        this.cadena = cadena
    }

    constructor(expresionA: ExpresionAritmetica3) : this() {
        this.expresionA = expresionA
    }

    constructor(cadena: Token, expresionA: ExpresionAritmetica3) : this() {
        this.expresionA = expresionA
        this.cadena = cadena
    }

    constructor(expresionA: ExpresionAritmetica3, cadena: Token) : this() {
        this.expresionA = expresionA
        this.cadena = cadena
    }

    constructor(expresionA: ExpresionAritmetica3, expresionCadena: ExpresionCadena3) : this() {
        this.expresionA = expresionA
        this.expresionCadena = expresionCadena
    }

    constructor(cadena: Token, expresionCadena: ExpresionCadena3) : this() {
        this.cadena = cadena
        this.expresionCadena = expresionCadena
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito) {
        if (expresionA != null && expresionCadena != null) {
            expresionA!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
            expresionCadena!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        } else if (expresionA != null) {
            expresionA!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        } else if (expresionCadena != null) {
            expresionCadena!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        }
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Expresión Cadena")
        if (cadena != null) {
            raiz.children.add(TreeItem("Cadena: ${cadena!!.lexema}"))
        }
        if (expresionCadena != null) {
            raiz.children.add(expresionCadena!!.getArbolVisual())
        }
        if (expresionA != null) {
            raiz.children.add(expresionCadena!!.getArbolVisual())
        }
        return raiz
    }

    override fun toString(): String {
        return "ExpresionCadena3(cadena=$cadena, expresionA=$expresionA, expresionCadena=$expresionCadena)"
    }

    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito: Ambito, listaErrores: ArrayList<Error>): String {
        return "cad"
    }


}