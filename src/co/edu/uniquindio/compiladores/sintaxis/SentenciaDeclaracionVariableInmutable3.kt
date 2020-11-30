package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class SentenciaDeclaracionVariableInmutable3(var tipoDato: Token, var nombreVar: Token) : Sentencia3() {
    override fun toString(): String {
        return "SentenciaDeclaracionVariableInmutable(tipoDato=$tipoDato, nombreVar=$nombreVar)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Declaración Variable")

        raiz.children.add(TreeItem("Tipo dato: ${tipoDato.lexema}"))
        raiz.children.add(TreeItem("Variable: ${nombreVar.lexema}"))
        return raiz
    }
    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String ) {
        tablaSimbolos.guardarSimboloValor(nombreVar.lexema, tipoDato.lexema, true, ambito, nombreVar.fila, nombreVar.columna )
    }
}