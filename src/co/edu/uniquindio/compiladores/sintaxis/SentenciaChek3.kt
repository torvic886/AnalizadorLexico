package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class SentenciaChek3(var condicion: Condicion3, var bloqueSentencias: ArrayList<Sentencia3>, var bloqueSentenciasOther: ArrayList<Sentencia3>?):Sentencia3() {
    override fun toString(): String {
        return "SentenciaChek(condicion=$condicion, bloqueSentencias=$bloqueSentencias, bloqueSentenciasOther=$bloqueSentenciasOther)"
    }
    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Sentencia Check")

        raiz.children.add( condicion.getArbolVisual() )

        var raizSentencias = TreeItem("Sentencias verdaderas")
        for (s in bloqueSentencias) {
            raizSentencias.children.add( s.getArbolVisual() )
        }
        raiz.children.add(raizSentencias)

        if ( bloqueSentenciasOther != null ) {
            var raizSentenciasOther = TreeItem("Sentencias other")
            for ( s in bloqueSentenciasOther!! ) {
                raizSentenciasOther.children.add( s.getArbolVisual() )
            }
            raiz.children.add(raizSentenciasOther)
        }
        return raiz
    }
    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String ) {
        for ( s in bloqueSentencias ) {
            s.llenarTablaSimbolos(tablaSimbolos, listaErrores, ambito)
        }
        if ( bloqueSentenciasOther != null ) {
            for ( s in bloqueSentenciasOther!! ) {
                s.llenarTablaSimbolos(tablaSimbolos, listaErrores, ambito)
            }
        }
    }
}