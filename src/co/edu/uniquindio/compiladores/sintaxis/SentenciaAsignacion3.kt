package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class SentenciaAsignacion3(var nombre:Token, var operadorAsignacion: Token, var expresion: Expresion3?, var invFuncion: SentenciaInvocacionFuncion3?):Sentencia3()
{

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Sentencia Asignación")
        raiz.children.add( TreeItem("Nombre: ${nombre.lexema}") )
        var raizOp = TreeItem("Operador:  ${operadorAsignacion.lexema}")
        if ( expresion != null) {
            raizOp.children.add( expresion!!.getArbolVisual() )

        } else if (invFuncion != null) {
            raizOp.children.add( invFuncion!!.getArbolVisual() )
        }
        raiz.children.add( raizOp )
        return raiz
    }
    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito) {
        var s = tablaSimbolos.buscarSimboloValor(nombre.lexema, ambito, nombre.fila, nombre.columna)
        if (s == null) {
            listaErrores.add(Error("El campo '${nombre!!.lexema}' no existe dentro del ambito '${ambito}'", nombre.fila, nombre.columna))
        } else {
            val tipo = s.tipo
            if (expresion != null) {
                expresion!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
                val tipoEx = expresion!!.obtenerTipo(tablaSimbolos, ambito, listaErrores)
                if ( tipoEx.isNotEmpty() && tipo != tipoEx) {
                    listaErrores.add(Error("Tipo de dato del campo ${nombre.lexema} que es ("+tipo+") no coincide con el tipo de dato de la expresión (que es ${tipoEx})", nombre.fila, nombre.columna))

                }
            }
            if (invFuncion != null) {
                var listaTiposArgs = ArrayList<String>()
                for (a in invFuncion!!.argumentos) {
                    a.analizarSemantica(tablaSimbolos, listaErrores, ambito)
                    val tipo = a.obtenerTipo(tablaSimbolos, ambito, listaErrores)
                    if (tipo.isNotEmpty()){
                        listaTiposArgs.add(tipo)
                    }
                }

                var s2 = tablaSimbolos.buscarSimboloFuncion(invFuncion!!.nombreFuncion.lexema, listaTiposArgs)
                if (s2 == null) {
                    listaErrores.add(Error("No existe dentro del ambito '${ambito}' la función '"+invFuncion!!.nombreFuncion.lexema+" ${listaTiposArgs}", invFuncion!!.nombreFuncion.fila, invFuncion!!.nombreFuncion.columna))
                } else {
                    val tipoFuncion = s2.tipo
                    if (tipoFuncion != tipo) {
                        listaErrores.add(Error("Tipo de dato del campo ${nombre.lexema} que es ("+tipo+") no coincide con el tipo de dato de la función (que es ${tipoFuncion})", nombre.fila, nombre.columna))
                    }
                }
            }
        }

    }

    override fun toString(): String {
        return "SentenciaAsignacion3(nombre=$nombre, operadorAsignacion=$operadorAsignacion, expresion=$expresion, invFuncion=$invFuncion)"
    }
}