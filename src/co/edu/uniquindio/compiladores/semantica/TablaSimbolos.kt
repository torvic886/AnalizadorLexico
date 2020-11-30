package co.edu.uniquindio.compiladores.semantica

import co.edu.uniquindio.compiladores.lexico.Error

class TablaSimbolos(var listaErrores:ArrayList<Error>) {
    var listaSimbolos: ArrayList<Simbolo> = ArrayList()

    /**
     * Permite guardar un simbolo que representa una variable, una constante, un arreglo, un parámetro
     */
    fun guardarSimboloValor( nombre:String, tipo:String, modificable:Boolean, ambito:String, fila:Int, columna:Int ) {
        val s = buscarSimboloValor( nombre, ambito )
        if ( s == null ) {
            listaSimbolos.add( Simbolo(nombre, tipo, modificable, ambito, fila, columna) )
        } else {
            listaErrores.add( Error("El campo $nombre ya existe dentro del ámbito $ambito", fila, columna ))
        }
    }

    /**
     * Permite guardar un simbolo que representa una funcion
     */
    fun guardarSimboloFuncion( nombre:String, tipoRetorno:String, tiposParametros:ArrayList<String>, ambito:String, fila: Int, columna: Int ) {
        val s = buscarSimboloFuncion( nombre, tiposParametros )
        if ( s == null ) {
            listaSimbolos.add( Simbolo( nombre, tipoRetorno, tiposParametros, ambito) )
        } else {
            listaErrores.add( Error("La funcion $nombre ya existe dentro del ámbito $ambito", fila, columna ))
        }
    }

    /**
     * Permite buscar un valor dentro de la tabla simbolos
     */
    fun buscarSimboloValor( nombre:String, ambito:String ): Simbolo?{
        for ( s in listaSimbolos ) {
            if ( s.tiposParametros == null ) {
                if ( s.nombre == nombre && s.ambito == ambito) return s
            }
        }
        return null
    }

    /**
     * Permite buscar una funcion dentro de la tabla simbolos
     */
    fun buscarSimboloFuncion( nombre:String, tiposParametros: ArrayList<String> ): Simbolo?{
        for ( s in listaSimbolos ) {
            if ( s.tiposParametros != null ) {
                if ( s.nombre == nombre && s.tiposParametros == tiposParametros ) return s
            }
        }
        return null
    }

    override fun toString(): String {
        return "TablaSimbolos(listaSimbolos=$listaSimbolos)"
    }

}