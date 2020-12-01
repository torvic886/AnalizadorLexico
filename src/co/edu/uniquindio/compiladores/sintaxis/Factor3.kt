package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Factor3(){

    var identificador:Token? = null
    var valorNum: ValorNumerico3? = null
    var expresionAritme: ExpresionAritmetica3? = null
    constructor(identificador: Token):this() {
        this.identificador = identificador
    }
    constructor(valorNum: ValorNumerico3):this() {
        this.valorNum = valorNum
    }
    constructor(expresionAritme: ExpresionAritmetica3):this() {
        this.expresionAritme = expresionAritme
    }
    fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito: String ): String {
        if (valorNum != null) {
            if (valorNum!!.valor.categoria == Categoria.ENTERO) return "entero"
            if (valorNum!!.valor.categoria == Categoria.DECIMAL) return "dec"
        } else if (identificador != null ) {
            var simbolo = tablaSimbolos.buscarSimboloValor(identificador!!.lexema, ambito )
            if ( simbolo != null ) return simbolo.tipo
        } else if (expresionAritme != null ) return expresionAritme!!.obtenerTipo(tablaSimbolos, ambito)

        return ""
    }
    fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Factor")

        if (identificador != null) {
            raiz.children.add(TreeItem("Identificador: ${identificador!!.lexema}"))
        }
        if (valorNum != null) {
            raiz.children.add( valorNum!!.getArbolVisual() )
        }
        if (expresionAritme != null) {
            raiz.children.add( expresionAritme!!.getArbolVisual() )
        }
        return raiz
    }

    override fun toString(): String {
        return "Factor3(identificador=$identificador, valorNum=$valorNum, expresionAritme=$expresionAritme)"
    }

}