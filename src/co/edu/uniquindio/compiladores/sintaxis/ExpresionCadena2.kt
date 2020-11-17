package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token

class ExpresionCadena2(var cadena:Token, var expresion:Expresion2?):Expresion2() {
    override fun toString(): String {
        return "ExpresionCadena(cadena=$cadena, expresion=$expresion)"
    }
}