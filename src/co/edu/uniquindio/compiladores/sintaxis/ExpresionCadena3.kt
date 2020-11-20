package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class ExpresionCadena3():Expresion3() {
    var cadena:Token? = null
    var expresionA:ExpresionAritmetica3? = null
    var expresionCadena:ExpresionCadena3? = null
    constructor(cadena:Token):this() {
        this.cadena = cadena
    }
    constructor(expresionA:ExpresionAritmetica3):this() {
        this.expresionA = expresionA
    }
    constructor(cadena:Token, expresionA:ExpresionAritmetica3):this() {
        this.expresionA = expresionA
        this.cadena = cadena
    }
    constructor(expresionA:ExpresionAritmetica3, cadena:Token):this() {
        this.expresionA = expresionA
        this.cadena = cadena
    }

    constructor(cadena:Token, expresionCadena: ExpresionCadena3):this() {
        this.cadena = cadena
        this.expresionCadena = expresionCadena
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Expresión Cadena")

        return raiz
    }


    override fun toString(): String {
        return "ExpresionCadena3(cadena=$cadena, expresion=$expresionA)"
    }

}