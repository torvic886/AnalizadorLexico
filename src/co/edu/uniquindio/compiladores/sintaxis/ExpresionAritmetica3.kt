package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

open class ExpresionAritmetica3():Expresion3()
{
    var expArit1: ExpresionAritmetica3? = null
    var termino: Termino3? = null
    var termino2: Termino3? = null
    var operador: Token? = null

    constructor(expArit1: ExpresionAritmetica3, operador: Token, termino: Termino3):this() {
        this.expArit1 = expArit1
        this.termino = termino
        this.operador = operador
    }
    constructor(termino: Termino3): this() {
        this.termino = termino
    }
    constructor(expArit1: ExpresionAritmetica3):this() {
        this.expArit1 = expArit1
    }
    constructor(termino1: Termino3, operador: Token, termino2: Termino3): this() {
        this.termino = termino1
        this.operador = operador
        this.termino2 = termino2
    }
    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Expresión Aritmética")



        return raiz
    }

    override fun toString(): String {
        return "ExpresionAritmetica3(expArit1=$expArit1, termino=$termino, termino2=$termino2, operador=$operador)"
    }


}