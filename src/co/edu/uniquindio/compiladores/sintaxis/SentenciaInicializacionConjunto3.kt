package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token

class SentenciaInicializacionConjunto3(): Sentencia3() {
    var valoresNumericos: ArrayList<ValorNumerico3> = ArrayList<ValorNumerico3>()
    var identificador: Token? = null

    constructor(identificador: Token, valoresNumericos: ArrayList<ValorNumerico3>):this() {
        this.valoresNumericos = valoresNumericos
        this.identificador = identificador
    }
}