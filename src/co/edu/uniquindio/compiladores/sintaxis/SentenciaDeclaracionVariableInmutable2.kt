package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token

class SentenciaDeclaracionVariableInmutable2(var tipoDato: Token, var nombreVar:Token):Sentencia2(){
    override fun toString(): String {
        return "SentenciaDeclaracionVariableInmutable(tipoDato=$tipoDato, nombreVar=$nombreVar)"
    }
}