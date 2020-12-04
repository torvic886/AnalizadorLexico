package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class ExpresionCadena3() : Expresion3() {
    var cadena: Token? = null
    var expresion: Expresion3? = null
    var identificador: Token? = null

    constructor(identificador: Token) : this() {
        this.identificador = identificador
    }
    constructor(cadena: Token, expresion: Expresion3?) : this() {
        this.expresion = expresion
        this.cadena = cadena
    }

    override fun getJavaCode(): String {
        var codigo = ""
        if (cadena != null) {
            codigo += cadena!!.getJavaCode()
            if (expresion != null) {
                codigo += "+" + expresion!!.getJavaCode()
            }
        } else {
            codigo += identificador!!.getJavaCode()
        }
        return codigo
    }
    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito) {
        if (expresion != null && cadena != null) {
            expresion!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        } else if (identificador != null) {
            var s = tablaSimbolos.buscarSimboloValor(identificador!!.lexema, ambito, identificador!!.fila, identificador!!.columna)
            if (s == null) {
                listaErrores.add(Error("El campo '${identificador!!.lexema}' aún no existe en el ambito '${ambito.nombre}'", identificador!!.fila, identificador!!.columna))
            }
        }
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Expresión Cadena")
        if (cadena != null) {
            raiz.children.add(TreeItem("Cadena: ${cadena!!.lexema}"))
        }
        if (identificador != null) {
            raiz.children.add(TreeItem("Identificador: " + identificador!!.lexema))
        }
        if (expresion != null) {
            raiz.children.add(expresion!!.getArbolVisual())
        }
        return raiz
    }

    override fun toString(): String {
        return "ExpresionCadena3(cadena=$cadena, expresionA=$expresion, expresionCadena=$identificador)"
    }

    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito: Ambito, listaErrores: ArrayList<Error>): String {
        return "cad"
    }


}