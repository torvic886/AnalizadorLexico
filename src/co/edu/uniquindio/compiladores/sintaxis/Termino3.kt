package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

open class Termino3(){
    var factor1: Factor3? = null
    var factor2: Factor3? = null
    var operador: Token? = null
    var termino: Termino3? = null
    constructor(factor1: Factor3, operador: Token, factor2: Factor3):this()
    {
        this.factor1 = factor1
        this.factor2 = factor2
        this.operador = operador
    }
    constructor(factor1: Factor3):this()
    {
        this.factor1 = factor1
    }
    constructor(termino: Termino3):this()
    {
       this.termino = termino
    }
    constructor(termino: Termino3, operador: Token, factor1: Factor3):this()
    {
        this.termino = termino
        this.operador = operador
        this.factor1 = factor1
    }

    fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Términos")

        return raiz
    }

    override fun toString(): String {
        return "Termino3(factor1=$factor1, factor2=$factor2, operador=$operador, termino=$termino)"
    }
}