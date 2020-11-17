package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token

class SentenciaAsignacion2(var nombre:Token, var operadorAsignacion: Token, var expresion: Expresion2):Sentencia2()
{
    override fun toString(): String {
        return "SentenciaAsignacion(nombre=$nombre, operadorAsignacion=$operadorAsignacion, expresion=$expresion)"
    }
}