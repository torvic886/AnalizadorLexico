package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
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

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito) {
        if (identificador != null) {
            var s = tablaSimbolos.buscarSimboloValor(identificador!!.lexema, ambito, identificador!!.fila, identificador!!.columna)
            if (s == null) {
                listaErrores.add(Error("El campo ${identificador!!.lexema} aún no existe en el ambito ${ambito}", identificador!!.fila, identificador!!.columna))
            }
        } else if (expCad != null) {
            expCad!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        }
    }

    override fun getJavaCode(): String {
        var codigo = "JOptionPane.showMessageDialog(null,"
        if (identificador != null) {
            codigo += identificador!!.getJavaCode()
        } else if (expCad != null) {
            codigo += expCad!!.getJavaCode()
        }
        codigo += ");"
        return codigo
    }
}