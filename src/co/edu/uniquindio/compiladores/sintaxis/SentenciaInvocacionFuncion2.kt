package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token


class SentenciaInvocacionFuncion2(var nombreFuncion: Token, var argumentos:ArrayList<Expresion2>):Sentencia2() {
    override fun toString(): String {
        return "SentenciaInvocacionFuncion(nombreFuncion=$nombreFuncion, argumentos=$argumentos)"
    }
}