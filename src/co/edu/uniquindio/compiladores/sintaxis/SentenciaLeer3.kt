package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class SentenciaLeer3():Sentencia3() {
    var cad: Token? = null
    var identificador: Token? = null

    constructor(cad: Token, identificador: Token):this() {
        this.cad= cad
        this.identificador = identificador
    }
    constructor(identificador: Token):this() {
        this.identificador = identificador
    }

    override fun toString(): String {
        return "SentenciaLeer3(cad=$cad, identificador=$identificador)"
    }
    override  fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Sentencia Leer")
        if (cad != null) {
            raiz.children.add(TreeItem("Cadena: ${cad!!.lexema}"))
        }
        if (identificador != null) {
            raiz.children.add(TreeItem("Identificador: ${identificador!!.lexema}"))
        }

        return raiz
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito) {
        if (identificador != null ) {
            var s = tablaSimbolos.buscarSimboloValor(identificador!!.lexema, ambito, identificador!!.fila, identificador!!.columna)
            if (s == null) {
                listaErrores.add(Error("El campo ${identificador!!.lexema} aún no existe dentro del ambito ${ambito}", identificador!!.fila, identificador!!.columna))
            }
        }

    }

}