package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class SentenciaArreglo(var nombre: Token, var tipoDato: Token, var expresiones: ArrayList<Expresion3>): Sentencia3() {

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Arreglo")
        raiz.children.add(TreeItem("${tipoDato.lexema} : ${nombre.lexema}"))
        var raizValores = TreeItem("Argumentos")
        if (expresiones.size >= 1) {
            for (v in expresiones) {
                raizValores.children.add( v.getArbolVisual() )
            }
        }
        raiz.children.add(raizValores)
        return raiz
    }

    override fun toString(): String {
        return "SentenciaArreglo(nombre=$nombre, tipoDato=$tipoDato, expresiones=$expresiones)"
    }
    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String ) {
        tablaSimbolos.guardarSimboloValor(nombre.lexema, tipoDato.lexema, true, ambito, nombre.fila, nombre.columna )
    }
    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String) {
        for ( e in expresiones ) {
            if ( e.obtenerTipo(tablaSimbolos, ambito) != tipoDato.lexema ) {
                listaErrores.add( Error("Tipo de dado de la expresión no coincide con el tipo de dato del arreglo", nombre.fila, nombre.columna))
            }
        }
    }

}