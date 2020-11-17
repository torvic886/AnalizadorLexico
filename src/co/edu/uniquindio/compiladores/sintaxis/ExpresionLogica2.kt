package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token

class ExpresionLogica2(var expresionRelacional: ExpresionRelacional2, var operadorLogico:Token, var expresionRelacional2: ExpresionRelacional2 ):Expresion2()
{
    override fun toString(): String {
        return "ExpresionLogica(expresionRelacional=$expresionRelacional, operadorLogico=$operadorLogico, expresionRelacional2=$expresionRelacional2)"
    }
}