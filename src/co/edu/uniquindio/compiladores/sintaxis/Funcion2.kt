package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token

class Funcion2(var nombreFuncion: Token, var tipoRetorno:Token, var listaParametros:ArrayList<Parametro2>, var bloqueSentencias:ArrayList<Sentencia2>) {
    override fun toString(): String {
        return "Funcion(nombreFuncion=$nombreFuncion, tipoRetorno=$tipoRetorno, listaParametros=$listaParametros, bloqueSentencias=$bloqueSentencias)"
    }

}