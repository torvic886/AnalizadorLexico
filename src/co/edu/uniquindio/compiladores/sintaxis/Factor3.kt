package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Factor3() {

    var identificador: Token? = null
    var valorNum: ValorNumerico3? = null
    var expresionAritme: ExpresionAritmetica3? = null

    constructor(identificador: Token) : this() {
        this.identificador = identificador
    }

    constructor(valorNum: ValorNumerico3) : this() {
        this.valorNum = valorNum
    }

    constructor(expresionAritme: ExpresionAritmetica3) : this() {
        this.expresionAritme = expresionAritme
    }

    fun getJavaCode(): String {
        return when {
            valorNum != null -> {
                valorNum!!.getJavaCode()
            }
            identificador != null -> {
                identificador!!.getJavaCode()
            }
            expresionAritme != null -> {
                "("+expresionAritme!!.getJavaCode()+")"
            }
            else -> ""
        }
    }

    fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito) {
        if (identificador != null) {
            var s = tablaSimbolos.buscarSimboloValor(identificador!!.lexema, ambito, identificador!!.fila, identificador!!.columna)
            if (s == null) {
                listaErrores.add(Error("El campo '${identificador!!.lexema}' aún no existe en el ambito '${ambito.nombre}'", identificador!!.fila, identificador!!.columna))
            } else {
                if (!(s.tipo == "dec" || s.tipo == "entero")) {
                    listaErrores.add(Error("El campo '${identificador!!.lexema}' no puede conformar una expresión aritmética puesto que es de tipo '${s.tipo}'", identificador!!.fila, identificador!!.columna))
                }
            }
        } else if (expresionAritme != null) {
            expresionAritme!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        }
    }

    fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito: Ambito, listaErrores: ArrayList<Error>): String {
        if (valorNum != null) {
            if (valorNum!!.valor.categoria == Categoria.ENTERO) return "entero"
            if (valorNum!!.valor.categoria == Categoria.DECIMAL) return "dec"
        } else if (identificador != null) {
            var simbolo = tablaSimbolos.buscarSimboloValor(identificador!!.lexema, ambito, identificador!!.fila, identificador!!.columna)
            if (simbolo != null) return simbolo.tipo
        } else if (expresionAritme != null) return expresionAritme!!.obtenerTipo(tablaSimbolos, ambito, listaErrores)

        return ""
    }

    fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Factor")

        if (identificador != null) {
            raiz.children.add(TreeItem("Identificador: ${identificador!!.lexema}"))
        }
        if (valorNum != null) {
            raiz.children.add(valorNum!!.getArbolVisual())
        }
        if (expresionAritme != null) {
            raiz.children.add(expresionAritme!!.getArbolVisual())
        }
        return raiz
    }

    override fun toString(): String {
        return "Factor3(identificador=$identificador, valorNum=$valorNum, expresionAritme=$expresionAritme)"
    }

}