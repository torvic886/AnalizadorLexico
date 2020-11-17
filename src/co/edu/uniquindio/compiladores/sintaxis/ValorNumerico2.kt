package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token

class ValorNumerico2(var valor:Token) {
    override fun toString(): String {
        return "ValorNumerico(valor=$valor)"
    }
}