package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class SentenciaWhereas3(var condicion: Condicion3, var sentencias: ArrayList<Sentencia3>) : Sentencia3() {
    override fun toString(): String {
        return "SentenciaWhereas2(condicion=$condicion, sentencias=$sentencias)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Sentencia Whereas")

        raiz.children.add(condicion.getArbolVisual())

        if (sentencias.size >= 1) {
            for (s in sentencias) {
                raiz.children.add(s.getArbolVisual())
            }
        }
        return raiz
    }

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito) {
        for (s in sentencias) {
            s.llenarTablaSimbolos(tablaSimbolos, listaErrores, ambito)
        }
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito) {
        condicion.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        for (s in sentencias) {
            s.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        }
    }

    override fun getJavaCode(): String {
        var codigo = "while(" + condicion.getJavaCode() + "){ "
        for (s in sentencias) {
            codigo += s.getJavaCode()
        }
        codigo += "}"
        return codigo
    }
}