package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class SentenciaDecremento4(var exA: ExpresionAritmetica3): Sentencia3(){
    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito) {
        exA.analizarSemantica(tablaSimbolos, listaErrores, ambito)
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Sentencia de Decremento")
        raiz.children.add(exA.getArbolVisual())
        return raiz
    }

    override fun getJavaCode(): String {
        return exA.getJavaCode() + "--;"
    }
}