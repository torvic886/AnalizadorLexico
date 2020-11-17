package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token

class AnalizadorSintactico4(var listaTokens: ArrayList<Token>) {
    var posicionActual = 0
    var tokenActual = listaTokens[0]
    val listaErrores = ArrayList<Error>()

    /**
     * Método que permite volver a un punto determindado, para descartar una posibilidad, y volver a intentar desde
     * ese mismo punto para validar otra categoría sintáctica
     * @param: posicionInicial (posición a la cual se desea volver)
     */
    fun hacerBT(posicionInicial: Int) {
        posicionActual = posicionInicial
        tokenActual = listaTokens[posicionInicial]
    }

    /**
     * Método que obtiene, si es posible, el token siguiente en la lista principal
     */
    fun obtenerSiguienteToken() {
        posicionActual++
        if (posicionActual < listaTokens.size) {
            tokenActual = listaTokens[posicionActual]
        }
    }

    /**
     * Método que reporta un error de sintaxis
     * @param: mensaje cuyo objetivo es informar del error
     */
    fun reportarError(mensaje: String) {
        listaErrores.add(Error(mensaje, tokenActual.fila, tokenActual.columna))

    }

    /**
     * <UnidadDeCompilacion> ::= [<ListaDeclaraciónDeVariables>] <ListaFunciones>
     */
    fun esUnidadDeCompilacion4(): UnidadDeCompilacion2? {
        val listDecVarInm: ArrayList<SentenciaDeclaracionVariableInmutable2> = esListaDeclaracionVarInmutable()
        val listaFunciones: ArrayList<Funcion2> = esListaFunciones()

        if (listaFunciones.size > 0 || listDecVarInm.size > 0) {
            return UnidadDeCompilacion2(listDecVarInm, listaFunciones)
        }
        return null
    }

    /**
     * <ListaFunciones> ::= <Funcion> [<ListaFunciones>]
     */
    fun esListaFunciones(): ArrayList<Funcion2> {
        val listaFunciones = ArrayList<Funcion2>()
        var funcion = esFuncion()

        while (funcion != null) {
            listaFunciones.add(funcion)
            funcion = esFuncion()
        }
        return listaFunciones
    }

    fun esListaDeclaracionVarInmutable(): ArrayList<SentenciaDeclaracionVariableInmutable2> {
        val listaDecVar = ArrayList<SentenciaDeclaracionVariableInmutable2>()
        var decVar = esDeclaracionDeVariableInmutable()

        while (decVar != null) {
            listaDecVar.add(decVar)
            decVar = esDeclaracionDeVariableInmutable()
        }
        return listaDecVar
    }

    /**
     * <Función> ::= mtd [is <TipoDato>] <Identificador> “[“<ListaParámetros>”]” “-“ ”-“ ”>” [<ListaSentencias>] “<” “-“ “-”
     */
    fun esFuncion(): Funcion2? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "mtd") {
            obtenerSiguienteToken()

            // Dado que es opcional definir el tipo de retorno, no se precisa satisfacer la siguiente validación
            if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "is") {
                obtenerSiguienteToken()

                // Si en la secuencia figura la palabra reservada "is", es necesario que siga un tipo de dato
                val tipoDato = esTipoDato()
                if (tipoDato != null) {
                    obtenerSiguienteToken()

                    // Verifica que en la secuencia continúe un identificador, puesto que se requiere
                    if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                        val nombreFuncion = tokenActual
                        obtenerSiguienteToken()

                        // Verifica si en la secuencia continúa  un "["
                        if (tokenActual.categoria == Categoria.PARENTESIS_ABRIR) {
                            obtenerSiguienteToken()

                            val listaParametros = esListaParametros()



                            if (tokenActual.categoria == Categoria.PARENTESIS_CERRAR) {
                                obtenerSiguienteToken()

                                if (tokenActual.categoria == Categoria.LLAVE_ABRIR) {
                                    obtenerSiguienteToken()

                                    val bloqueSentencias = esBloqueDeSentencia() //
                                    var centi = false
                                    for (i in bloqueSentencias) {
                                        if (i is SentenciaRetorno2) {
                                            centi = true
                                        }
                                    }
                                    if (!centi) {
                                        reportarError("La función debe retornar un " + tipoDato)
                                    } else {
                                        if (tokenActual.categoria == Categoria.LLAVE_CERRAR) {
                                            obtenerSiguienteToken()

                                            // Todo bien hasta acá
                                            return Funcion2(nombreFuncion, tipoDato, listaParametros, bloqueSentencias)
                                        } else {
                                            reportarError("Es preciso que se especifique al final la llave de cerrar")
                                        }
                                    }


                                } else {
                                    reportarError("Es preciso especificar la llave de abrir")
                                }

                            } else {
                                reportarError("Es preciso especificar el paréntesis de cerrar")
                            }


                        } else {
                            reportarError("Es preciso que se especifique el paréntesis de abrir")
                        }
                    }

                } else {
                    reportarError("Es preciso que se especifique el tipo de dato seguido de la palabra 'is'")
                }
            }


            // Verifica que en la secuencia continúe un identificador, puesto que se requiere
            if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                val nombreFuncion = tokenActual
                obtenerSiguienteToken()

                // Verifica si en la secuencia continúa  un "["
                if (tokenActual.categoria == Categoria.PARENTESIS_ABRIR) {
                    obtenerSiguienteToken()

                    val listaParametros = esListaParametros()



                    if (tokenActual.categoria == Categoria.PARENTESIS_CERRAR) {
                        obtenerSiguienteToken()

                        if (tokenActual.categoria == Categoria.LLAVE_ABRIR) {
                            obtenerSiguienteToken()

                            val bloqueSentencias = esBloqueDeSentencia() //


                            if (tokenActual.categoria == Categoria.LLAVE_CERRAR) {
                                obtenerSiguienteToken()

                                // Todo bien hasta acá
                                return Funcion2(nombreFuncion, Token("No especificado", Categoria.NO_RETORNO, 0, 0), listaParametros, bloqueSentencias)
                            } else {
                                reportarError("Es preciso que se especifique al final la llave de cerrar")
                            }

                        } else {
                            reportarError("Es preciso especificar la llave de abrir")
                        }

                    } else {
                        reportarError("Es preciso especificar el paréntesis de cerrar")
                    }


                } else {
                    reportarError("Es preciso que se especifique el paréntesis de abrir")
                }
            }


        }
        return null
    }


    /**
     * <TipoDato> ::= entero | dec | cad | atm | centi | conjunto | lista
     */
    fun esTipoDato(): Token? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA) {
            if (tokenActual.lexema == "entero" || tokenActual.lexema == "dec" || tokenActual.lexema == "cad"
                    || tokenActual.lexema == "atm" || tokenActual.lexema == "centi" || tokenActual.lexema == "lista"
                    || tokenActual.lexema == "conjunto") {
                return tokenActual
            }

        }
        return null
    }

    /**
     * <ListaParámetros> ::= <Parámetro> [";" <ListaParámetros>]
     */
    fun esListaParametros(): ArrayList<Parametro2> {
        var listaParametros = ArrayList<Parametro2>()
        var parametro = esParametro()

        if (parametro != null) {
            listaParametros.add(parametro)

            while (tokenActual.categoria == Categoria.SEPARADOR) {
                obtenerSiguienteToken()

                parametro = esParametro()
                if (parametro != null) {
                    listaParametros.add(parametro)
                } else {
                    reportarError("El separador únicamente puede estar en medio de dos parámetros")
                    break
                }
            }
        }
        return listaParametros
    }

    /**
     * <Parámetro> ::= <TipoDeDato> “:” <Identificador>
     */
    fun esParametro(): Parametro2? {
        val tipoDato = esTipoDato()
        if (tipoDato != null) {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.DOS_PUNTOS && tokenActual.lexema == ":") {
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                    val nombreParametro = tokenActual
                    obtenerSiguienteToken()
                    // Como todo está correcto:
                    return Parametro2(tipoDato, nombreParametro)
                } else {
                    reportarError("Después de los ':' debe seguir un identificador")
                }
            } else {
                reportarError("Después del tipo de dato se debe espcificar ':'")
            }
        }

        return null
    }



}
