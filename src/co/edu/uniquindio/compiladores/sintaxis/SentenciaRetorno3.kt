package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class SentenciaRetorno3(var expresion: Expresion3):Sentencia3() {
    override fun toString(): String {
        return "SentenciaRetorno(expresion=$expresion)"
    }
    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Sentencia Retorno")

        raiz.children.add( expresion.getArbolVisual() )
        return raiz
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito) {
        if (expresion != null) {
            expresion.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        }
    }

}