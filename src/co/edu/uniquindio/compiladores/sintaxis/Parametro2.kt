package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token


class Parametro2(var tipoDato: Token, var nombreParametro:Token) {
    override fun toString(): String {
        return "Parametro(tipoDato=$tipoDato, nombreParametro=$nombreParametro)"
    }

}