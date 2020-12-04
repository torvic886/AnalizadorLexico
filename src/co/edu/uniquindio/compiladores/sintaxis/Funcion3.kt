package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Funcion3(var nombreFuncion: Token, var tipoRetorno:Token?, var listaParametros:ArrayList<Parametro3>, var bloqueSentencias:ArrayList<Sentencia3>) {
    override fun toString(): String {
        return "Funcion(nombreFuncion=$nombreFuncion, tipoRetorno=$tipoRetorno, listaParametros=$listaParametros, bloqueSentencias=$bloqueSentencias)"
    }

    fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Función")

        raiz.children.add(TreeItem("Nombre: ${nombreFuncion.lexema}"))
        if (tipoRetorno != null) {
            raiz.children.add(TreeItem("Tipo retorno: ${tipoRetorno!!.lexema}"))

        }
        // Para los parámetros
        var raizParametro = TreeItem("Parámetros")
        for (d in listaParametros) {
            raizParametro.children.add( d.getArbolVisual() )
        }
        raiz.children.add(raizParametro)

        // Para las sentencias
        var raizSentencias = TreeItem("Sentencias")
        for (f in bloqueSentencias) {
            raizSentencias.children.add( f.getArbolVisual() )
        }
        raiz.children.add(raizSentencias)
        return raiz
    }
    fun obtenerTiposParametros(): ArrayList<String> {
        var listaP: ArrayList<String> = ArrayList()
        for ( p in listaParametros ) {
            listaP.add(p.tipoDato.lexema)
        }
        return listaP
    }

    fun llenarTablaSimbolos( tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito ) {
        if (tipoRetorno != null) {
            tablaSimbolos.guardarSimboloFuncion(nombreFuncion.lexema, tipoRetorno!!.lexema, obtenerTiposParametros(), ambito, nombreFuncion.fila, nombreFuncion.columna )
        } else {
            tablaSimbolos.guardarSimboloFuncion(nombreFuncion.lexema, "", obtenerTiposParametros(), ambito, nombreFuncion.fila, nombreFuncion.columna )
        }
        for ( p in listaParametros ) {
            tablaSimbolos.guardarSimboloValor( p.nombreParametro.lexema, p.tipoDato.lexema, true, Ambito(nombreFuncion.lexema, listaParametros), p.nombreParametro.fila, p.nombreParametro.columna )
        }
        for ( s in bloqueSentencias ) {
            s.llenarTablaSimbolos( tablaSimbolos, listaErrores, Ambito(nombreFuncion.lexema, listaParametros) )
        }
    }
    fun analizarSemantica( tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error> ) {
        if (tipoRetorno != null) {
            var aux = 0
            var centi = true
            for ( s in bloqueSentencias ) {
                aux++
                if (s is SentenciaRetorno3) {
                    centi = false
                    aux = 1000
                    val tipoDR = s.expresion.obtenerTipo(tablaSimbolos, Ambito(nombreFuncion.lexema, listaParametros) , listaErrores )
                    if ( tipoDR.isNotEmpty() && tipoDR != tipoRetorno!!.lexema ) {
                        listaErrores.add(Error("EN FUNCION: Tipo de dato de la expresión de retorno ("+tipoDR+") no coincide con el tipo de dato de la función (que es ${tipoRetorno!!.lexema})", nombreFuncion.fila, nombreFuncion.columna))
                    }
                }
                s.analizarSemantica( tablaSimbolos, listaErrores, Ambito(nombreFuncion.lexema, listaParametros) )
            }
            if (centi) {
                listaErrores.add(Error("Dado que la función tiene un tipo de dato ${tipoRetorno!!.lexema}, es preciso que retorne una expresión de ese mismo tipo", nombreFuncion.fila, nombreFuncion.columna))
            }
            if (aux > 1000) {
                listaErrores.add(Error("Después de la sentencia de retorno no debe haber más sentencias", nombreFuncion.fila, nombreFuncion.columna))
            }
        } else {
            var centi = true
            for ( s in bloqueSentencias ) {
                if (s is SentenciaRetorno3) {
                    centi = false
                }
                s.analizarSemantica( tablaSimbolos, listaErrores, Ambito(nombreFuncion.lexema, listaParametros) )
            }
            if (!centi) {
                listaErrores.add(Error("Dado que la función no tiene un tipo de dato especificado, no debe contener una sentencia de retorno", nombreFuncion.fila, nombreFuncion.columna))
            }
        }

    }
    fun getJavaCode(): String {
        var codigo = ""
        //verifia si es el equivalente al main de java
        if (nombreFuncion.lexema == "raiz") {
            codigo += "public static void main(String [] args) {"
        } else {
            if (tipoRetorno != null) {
                codigo += "static " + tipoRetorno!!.getJavaCode() + nombreFuncion.getJavaCode() + "("
                var aux = 0
                for (p in listaParametros) {
                    if (aux == 0) {
                        codigo += p.getJavaCode()
                        aux++
                    } else {
                        codigo += "," + p.getJavaCode()
                    }
                }
                codigo += "){"
            } else {
                codigo += "static void " + nombreFuncion.lexema + "("
                var aux = 0
                for (p in listaParametros) {
                    if (aux == 0) {
                        codigo += p.getJavaCode()
                        aux++
                    } else {
                        codigo += "," + p.getJavaCode()
                    }
                }
                codigo += "){"
            }
        }
        for (s in bloqueSentencias) {
            codigo += s.getJavaCode()
        }
        codigo += "}"
        return codigo
    }
}