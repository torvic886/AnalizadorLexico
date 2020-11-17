package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token

class OperadorAsignacion2(var operadorAsignacion: Token)
{
    override fun toString(): String {
        return "OperadorAsignacion(operadorAsignacion=$operadorAsignacion)"
    }
}