package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class SentenciaImprimir3():Sentencia3() {
    var identificador: Token? = null
    var expCad: ExpresionCadena3? = null
    constructor(identificador: Token):this() {
        this.identificador = identificador
    }
    constructor(expCad: ExpresionCadena3):this() {
        this.expCad = expCad
    }
    override  fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Sentencia Imprimir")
        if (expCad != null) {
            raiz.children.add(expCad!!.getArbolVisual())
        }
        if (identificador != null) {
            raiz.children.add(TreeItem("Identificador: ${identificador!!.lexema}"))
        }
        return raiz
    }
}