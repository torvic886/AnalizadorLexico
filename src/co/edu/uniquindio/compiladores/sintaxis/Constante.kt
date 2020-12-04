package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Constante(var nombre: Token, var tipoDato: Token, var operadorAsignacion: Token?, var expresion: Expresion3?, var invFuncion: SentenciaInvocacionFuncion3?) : Sentencia3() {
    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Declaración Constante")
        raiz.children.add(TreeItem("Nombre: ${nombre.lexema}"))
        raiz.children.add(TreeItem("Tipo dato:  ${tipoDato.lexema}"))


        if (operadorAsignacion != null) {
            var raizOp = TreeItem("Operador:  ${operadorAsignacion!!.lexema}")
            if (expresion != null) {
                raizOp.children.add(expresion!!.getArbolVisual())
            }
            if (invFuncion != null) {
                raizOp.children.add(invFuncion!!.getArbolVisual())
            }
            raiz.children.add(raizOp)
        }

        return raiz
    }

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito) {
        tablaSimbolos.guardarSimboloValor(nombre.lexema, tipoDato.lexema, false, ambito, nombre.fila, nombre.columna)
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito) {
        if (expresion != null) {
            expresion!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
            val tipEx = expresion!!.obtenerTipo(tablaSimbolos, ambito, listaErrores)
            if (tipEx.isNotEmpty() && tipEx != tipoDato.lexema ) {
                listaErrores.add(Error("Tipo de dato del campo '${nombre.lexema}' que es '"+tipoDato.lexema+"' no coincide con el tipo de dato de la expresión (que es ${tipEx})", nombre.fila, nombre.columna))
            }
        } else if (invFuncion != null) {
            var listaTiposArgs = ArrayList<String>()
            for (a in invFuncion!!.argumentos) {
                a.analizarSemantica(tablaSimbolos, listaErrores, ambito)
                val tipo = a.obtenerTipo(tablaSimbolos, ambito, listaErrores)
                if (tipo.isNotEmpty()) {
                    listaTiposArgs.add(tipo)
                }
            }

            var s2 = tablaSimbolos.buscarSimboloFuncion(invFuncion!!.nombreFuncion.lexema, listaTiposArgs)
            if (s2 == null) {
                listaErrores.add(Error("No existe dentro del ambito '${ambito.nombre}' la función '"+invFuncion!!.nombreFuncion.lexema+" ${listaTiposArgs}", invFuncion!!.nombreFuncion.fila, invFuncion!!.nombreFuncion.columna))
            } else {
                val tipoFuncion = s2.tipo
                if (tipoFuncion != tipoDato.lexema) {
                    listaErrores.add(Error("Tipo de dato del campo ${nombre.lexema} que es ("+tipoDato.lexema+") no coincide con el tipo de dato de la función (que es ${tipoFuncion})", nombre.fila, nombre.columna))
                }
            }
        }


    }

    override fun getJavaCode(): String {
        var codigo =  "final " + tipoDato.getJavaCode() + " " + nombre.lexema + "="
        if (expresion != null) {
            codigo += expresion!!.getJavaCode() + ";"
        } else if (invFuncion != null) {
            codigo += invFuncion!!.getJavaCode()
        }
        return codigo
    }

}