package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class UnidadDeCompilacion3(var listaDeclVar:ArrayList<Sentencia3>, var listaFunciones:ArrayList<Funcion3>) {
    override fun toString(): String {
        return "UnidadDeCompilacion(listaDeclVar=$listaDeclVar, listaFunciones=$listaFunciones)"
    }

    fun getArbolVisual():TreeItem<String> {
        var raiz = TreeItem("Unidad de Compilación")

        for (d in listaDeclVar) {
            raiz.children.add( d.getArbolVisual() )
        }

        for (f in listaFunciones) {
            raiz.children.add( f.getArbolVisual() )
        }
        return raiz
    }
    fun llenarTablaSimbolos( tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error> ) {
        for ( f in listaFunciones ) {
            f.llenarTablaSimbolos( tablaSimbolos, listaErrores, Ambito("unidadCompilacion"))
        }
        for ( x in listaDeclVar ) {
            x.llenarTablaSimbolos(tablaSimbolos, listaErrores, Ambito("unidadCompilacion"))
        }
    }
    fun analizarSemantica( tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error> ) {
        var centi = false
        var aux = 0
        for ( f in listaFunciones ) {
            if (f.nombreFuncion.lexema == "raiz") {
                if (f.listaParametros.isNotEmpty()) {
                    listaErrores.add( Error("Es preciso que la función principal no reciba parámetros para realizar traducción del código a Java", 0, 0) )
                } else if (f.tipoRetorno != null) {
                    listaErrores.add( Error("Es preciso que la función principal no retorne nada, para realizar traducción del código a Java", 0, 0) )
                }
                centi = true
                aux++
            }
            f.analizarSemantica( tablaSimbolos, listaErrores )
        }
        if (!centi) {
            listaErrores.add( Error("Es preciso que se defina una función principal para realizar traducción del código a Java", 0, 0) )
        }
        if (aux >=2) {
            listaErrores.add( Error("No debe existir más de una función principal, para realizar traducción del código a Java", 0, 0) )
        }
    }
    fun getJavaCode():String {
        var codigo = "import javax.swing.*; public class Principal{"
        for (f in listaFunciones) {
            codigo += f.getJavaCode()
        }
        codigo += "}"
        return codigo
    }
}