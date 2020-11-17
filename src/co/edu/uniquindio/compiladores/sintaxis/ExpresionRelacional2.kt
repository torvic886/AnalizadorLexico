package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token

class ExpresionRelacional2(var expresionAritmetica: ExpresionAritmetica2, var operardorRel:Token, var expresionAritmetica2: ExpresionAritmetica2):Expresion2() {
    override fun toString(): String {
        return "ExpresionRelacional(expresionAritmetica=$expresionAritmetica, operardorRel=$operardorRel, expresionAritmetica2=$expresionAritmetica2)"
    }
}