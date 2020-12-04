package co.edu.uniquindio.compiladores.semantica

import co.edu.uniquindio.compiladores.sintaxis.Parametro3

class Ambito () {
    var nombre: String = ""
    var parametros: ArrayList<Parametro3>? = ArrayList()

    constructor(nombre: String): this() {
        this.nombre = nombre
    }
    constructor(nombre: String, parametros: ArrayList<Parametro3>): this() {
        this.nombre = nombre
        this.parametros = parametros
    }
}