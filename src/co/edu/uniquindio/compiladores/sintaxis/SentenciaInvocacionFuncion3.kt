package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem


class SentenciaInvocacionFuncion3(var nombreFuncion: Token, var argumentos: ArrayList<Expresion3>) : Sentencia3() {
    override fun toString(): String {
        return "SentenciaInvocacionFuncion(nombreFuncion=$nombreFuncion, argumentos=$argumentos)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Invocación Función")

        raiz.children.add(TreeItem("Nombre: ${nombreFuncion.lexema}"))

        var raizArg = TreeItem("Argumentos")
        for (a in argumentos) {
            raizArg.children.add(a.getArbolVisual())
        }
        raiz.children.add(raizArg)
        return raiz
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito) {
        var listaTiposArgs = ArrayList<String>()
        for (a in argumentos) {
            a.analizarSemantica(tablaSimbolos, listaErrores, ambito)
            val tipo = a.obtenerTipo(tablaSimbolos, ambito, listaErrores)
            if (tipo.isNotEmpty()) {
                listaTiposArgs.add(tipo)
            }
        }
        var s = tablaSimbolos.buscarSimboloFuncion(nombreFuncion.lexema, listaTiposArgs)
        if (s == null) {
            listaErrores.add(Error("No existe dentro del ambito '${ambito}' la función '"+nombreFuncion.lexema+"'  ${listaTiposArgs}", nombreFuncion.fila, nombreFuncion.columna))
        }
    }
}