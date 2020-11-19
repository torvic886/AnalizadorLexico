
package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token

class AnalizadorSintactico6(var listaTokens: ArrayList<Token>) {

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
        tokenActual = listaTokens[posicionActual]
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
    fun esUnidadDeCompilacion5(): UnidadDeCompilacion3? {
        val listDecVarInm: ArrayList<SentenciaDeclaracionVariableInmutable3> = esListaDeclaracionVarInmutable()
        val listaFunciones: ArrayList<Funcion3> = esListaFunciones()

        if (listaFunciones.size > 0 || listDecVarInm.size > 0) {
            return UnidadDeCompilacion3(listDecVarInm, listaFunciones)
        }
        return null
    }

    /**
     * <ListaFunciones> ::= <Funcion> [<ListaFunciones>]
     */
    fun esListaFunciones(): ArrayList<Funcion3> {
        val listaFunciones = ArrayList<Funcion3>()
        var funcion = esFuncion()

        while (funcion != null) {
            listaFunciones.add(funcion)
            funcion = esFuncion()
        }
        return listaFunciones
    }

    fun esListaDeclaracionVarInmutable(): ArrayList<SentenciaDeclaracionVariableInmutable3> {
        val listaDecVar = ArrayList<SentenciaDeclaracionVariableInmutable3>()
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
    fun esFuncion(): Funcion3? {
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
                                        print("--------------------------------------------- SENTENCIA- ----- - - -> ")
                                        print(i)
                                        if (i is SentenciaRetorno3) {
                                            centi = true

                                        }
                                    }
                                    if (centi == false) {
                                        reportarError("La función debe retornar un ${tipoDato.lexema}")
                                    } else {
                                        if (tokenActual.categoria == Categoria.LLAVE_CERRAR) {
                                            obtenerSiguienteToken()

                                            // Todo bien hasta acá
                                            return Funcion3(nombreFuncion, tipoDato, listaParametros, bloqueSentencias)
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
                                return Funcion3(nombreFuncion, Token("No especificado", Categoria.NO_RETORNO, 0, 0), listaParametros, bloqueSentencias)
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
                    || tokenActual.lexema == "atm" || tokenActual.lexema == "centi") {
                return tokenActual
            }

        }
        return null
    }

    /**
     * <Arreglo> ::= Conjunto | Lista
     */
    fun esArreglo(): Token? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA) {
            if (tokenActual.lexema == "Conjunto" || tokenActual.lexema == "Lista") {
                return tokenActual
            }

        }
        return null
    }

    /**
     * <ListaParámetros> ::= <Parámetro> [";" <ListaParámetros>]
     */
    fun esListaParametros(): ArrayList<Parametro3> {
        var listaParametros = ArrayList<Parametro3>()
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
    fun esParametro(): Parametro3? {
        val tipoDato = esTipoDato()
        if (tipoDato != null) {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.DOS_PUNTOS && tokenActual.lexema == ":") {
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                    val nombreParametro = tokenActual
                    obtenerSiguienteToken()
                    // Como todo está correcto:
                    return Parametro3(tipoDato, nombreParametro)
                } else {
                    reportarError("Después de los ':' debe seguir un identificador")
                }
            } else {
                reportarError("Después del tipo de dato se debe espcificar ':'")
            }
        }

        return null
    }

    /**
     * <ListaSentencias> ::= <Sentencia><ListaSentencias> | <Sentencia>
     */
    fun esBloqueDeSentencia(): ArrayList<Sentencia3> {
        val listaSentencias = ArrayList<Sentencia3>()
        var sentencia = esSentencia()

        if (sentencia != null) {
            listaSentencias.add(sentencia)
            sentencia = esSentencia()
            while (sentencia != null) {
                listaSentencias.add(sentencia)
                sentencia = esSentencia()
            }
        }
        return listaSentencias
    }

    /**
     *  <Sentencia> ::= < InvocaciónDeFunción> | < SentenciaAsignación> |
    < SentenciaChek> |< SentenciaWhereas> |
    <SentenciaDeclaraciónDeVariableInmutable> |
    <SentenciaDeclaraciónDeVariableMutable> |
    <SentenciaDeRetorno> | <SentenciaDeIncremento> |
    <SentenciaDeDecremento> | <SentenciaImprimir>
    <SentenciaLeer>
     */
    fun esSentencia(): Sentencia3? {

        val asignacion = esAsignacion()
        if (asignacion != null) {
            return asignacion
        }

        // Verifica que se trate de la invocación de una función
        val invocacionFuncion = esInvocacionDeFuncion()
        if (invocacionFuncion != null) {
            return invocacionFuncion
        }


        val chek = esChek()
        if (chek != null) {
            return chek
        }


        val whereas = esWhereas()
        if (whereas != null) {
            return whereas
        }

        val declaracionVariableInmutable = esDeclaracionDeVariableInmutable()
        // Verifica si se trata de la declaración de una variable inmutable
        if (declaracionVariableInmutable != null) {
            return declaracionVariableInmutable
        }

        /*
        val decConjunto = esDeclaracionConjunto()
        if (decConjunto != null) {
            return decConjunto
        }*/

        val retorno = esRetorno()
        if (retorno != null) {
            return retorno
        }
        /*
        val incremento = esIncremento()
        if (incremento != null) {
            return incremento
        }

        val decremento = esDecremento()
        if (decremento != null) {
            return decremento
        }
        val imprimir = esImprimir()
        if (imprimir != null) {
            return imprimir
        }
        val leer = esLeer()
        if (leer != null) {
            return null
        }*/
        return null
    }

    fun esLeer(): SentenciaLeer3? {
        return null
    }

    /**
     * <SentenciaDeIncremento> ::= <ExpresiónAritmética> "+" "_"
     */
    fun esIncremento(): SentenciaIncremento3? {
        /*val expresionAritmetica = esExpresionAritmetica()
        if (expresionAritmetica != null) {
            if (tokenActual.categoria == Categoria.OPERADOR_INCREMENTO) {
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.FIN_SENTENCIA) {
                    obtenerSiguienteToken()
                    return SentenciaIncremento(expresionAritmetica)
                } else {
                    reportarError("Es preciso que especifique el final de la sentencia incremento")
                }
            }
        }*/
        return null
    }

    /**
     * <SentenciaDeDecremento> ::= <ExpresiónAritmética> "-" "_"
     */
    fun esDecremento(): SentenciaDecremento3? {
        /*val expresionAritmetica = esExpresionAritmetica()
        if (expresionAritmetica != null) {
            if (tokenActual.categoria == Categoria.OPERADOR_DECREMENTO) {
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.FIN_SENTENCIA) {
                    obtenerSiguienteToken()
                    return SentenciaDecremento(expresionAritmetica)
                } else {
                    reportarError("Es preciso que especifique el final de la sentencia incremento")
                }
            }
        }*/
        return null
    }

    fun esImprimir(): SentenciaImprimir3? {
        return null
    }

    /**
     * <Condición> ::= no <Condición> | <ExpresiónRelacional> | <ExpresiónLógica> | yes | not
     */
    fun esCondicion(): Condicion3? {
        val posInicial = posicionActual
        if (tokenActual.categoria == Categoria.PARENTESIS_ABRIR) {
            obtenerSiguienteToken()
            if (tokenActual.lexema == "no") {
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && (tokenActual.lexema == "yes" || tokenActual.lexema == "not")) {
                    val cent = tokenActual
                    obtenerSiguienteToken()
                    if (tokenActual.categoria == Categoria.PARENTESIS_CERRAR) {
                        obtenerSiguienteToken()
                        return Condicion3(null, null, null, Negacion3(cent))
                    } else {
                        reportarError("Falta paréntesis de cerrar en la condición")
                    }
                }
                val expRel = esExpresionRelacional()
                if (expRel != null) {
                    if (tokenActual.categoria == Categoria.PARENTESIS_CERRAR) {
                        obtenerSiguienteToken()
                        return Condicion3(null, null, null, Negacion3(expRel))
                    } else {
                        reportarError("Falta paréntesis de cerrar en la condición")
                    }
                }
                val expLog = esExpresionLogica()
                if (expLog != null) {
                    if (tokenActual.categoria == Categoria.PARENTESIS_CERRAR) {
                        obtenerSiguienteToken()
                        return Condicion3(null, null, null, Negacion3(expLog))
                    } else {

                        reportarError("Falta paréntesis de cerrar en la condición")
                    }
                }
                reportarError("Después de la palabra 'no' debe continuar una condición")
            }
            if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && (tokenActual.lexema == "yes" || tokenActual.lexema == "not")) {
                val cent = tokenActual
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.PARENTESIS_CERRAR) {
                    obtenerSiguienteToken()
                    return Condicion3(cent, null, null, null)
                } else {
                    reportarError("Falta paréntesis de cerrar en la condición")
                }
            }
            val expRel = esExpresionRelacional()
            if (expRel != null) {
                if (tokenActual.categoria == Categoria.PARENTESIS_CERRAR) {
                    obtenerSiguienteToken()
                    return Condicion3(null, expRel, null, null)
                } else {
                    reportarError("Falta paréntesis de cerrar en la condición")
                }
            }
            val expLog = esExpresionLogica()
            if (expLog != null) {
                if (tokenActual.categoria == Categoria.PARENTESIS_CERRAR) {
                    obtenerSiguienteToken()
                    return Condicion3(null, null, expLog, null)
                } else {
                    reportarError("Falta paréntesis de cerrar en la condición")
                }
            }
        } else {
            if (tokenActual.lexema == "no") {
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.PALABRA_RESERVADA || tokenActual.lexema == "yes" || tokenActual.lexema == "not") {
                    val cent = tokenActual
                    obtenerSiguienteToken()
                    return Condicion3(null, null, null, Negacion3(cent))
                }

                val expRel = esExpresionRelacional()
                if (expRel != null) {
                    return Condicion3(null, null, null, Negacion3(expRel))
                }

                val expLog = esExpresionLogica()
                if (expLog != null) {
                    return Condicion3(null, null, null, Negacion3(expLog))
                }
                reportarError("Después de la palabra 'no' debe continuar una condición")
            }
            if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && (tokenActual.lexema == "yes" || tokenActual.lexema == "not")) {
                val cent = tokenActual
                obtenerSiguienteToken()
                return Condicion3(cent, null, null, null)
            }

            val expRel = esExpresionRelacional()
            if (expRel != null) {
                return Condicion3(null, expRel, null, null)
            }

            val expLog = esExpresionLogica()
            if (expLog != null) {
                return Condicion3(null, null, expLog, null)
            }
        }
        hacerBT(posInicial)
        return null
    }

    /**
     *  <SentenciaChek> ::= chek <Condición> “-“ “-“ “>” [<ListaSentencias>] “<” “-” “-” [other “-“ “-“ “>” [<ListaSentencias>] “<” “-“ “-“]

     */
    fun esChek(): SentenciaChek3? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "check") {
            obtenerSiguienteToken()
            val condicion = esCondicion()
            if (condicion != null) {
                if (tokenActual.categoria == Categoria.LLAVE_ABRIR) {
                    obtenerSiguienteToken()
                    var bloqueSentencias = esBloqueDeSentencia()

                    if (tokenActual.categoria == Categoria.LLAVE_CERRAR) {
                        obtenerSiguienteToken()
                        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "other") {
                            obtenerSiguienteToken()

                            if (tokenActual.categoria == Categoria.LLAVE_ABRIR) {
                                obtenerSiguienteToken()

                                val bloqueSentenciasOther = esBloqueDeSentencia()

                                if (tokenActual.categoria == Categoria.LLAVE_CERRAR) {
                                    obtenerSiguienteToken()

                                    // Como todo bien, entonces:
                                    return SentenciaChek3(condicion, bloqueSentencias, Other3(bloqueSentenciasOther))
                                } else {
                                    reportarError("Hace falta llave cerrar del 'other'")
                                }
                            } else {
                                reportarError("Hace falta llave abrir del 'other'")
                            }


                        }
                        print("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!CASI RETORNA EL CHECK!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
                        // Como todo bien, y no es obligatorio el other, entonces:
                        return SentenciaChek3(condicion, bloqueSentencias, null)
                    } else {
                        reportarError("Hace falta llave cerrar en el chek")
                    }

                } else {
                    reportarError("Después de una condición debe seguir una llave de abrir")
                }
            } else {
                reportarError("Después de la pal 'chek' debe seguir una condición")
            }
        }
        return null
    }

    /**
     * <SentenciaMientras> ::= whereas “[“<Condición> “]” do “-“ “-“ “>” [<ListaSentencias>]  “<” “-” “-”
     */
    fun esWhereas(): SentenciaWhereas3? {

        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "whereas") {
            obtenerSiguienteToken()
            val condicion = esCondicion()
            if (condicion != null) {
                if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "do") {
                    obtenerSiguienteToken()
                    if (tokenActual.categoria == Categoria.LLAVE_ABRIR) {
                        obtenerSiguienteToken()
                        val bloqueSentencias = esBloqueDeSentencia()
                        if (tokenActual.categoria == Categoria.LLAVE_CERRAR) {
                            obtenerSiguienteToken()

                            // Como todo bien, entonces:
                            return SentenciaWhereas3(condicion, bloqueSentencias)
                        } else {
                            reportarError("Hace falta llave cerrar del 'ciclo'")
                        }

                    } else {
                        reportarError("Hace falta llave abrir en ciclo")
                    }
                } else {
                    reportarError("Después de la condición sigue la pal 'do'")
                }

            } else {
                reportarError("Despueés de la pal 'whereas' sigue una condición")
            }

        }
        return null
    }

    /**
     * <SentenciaDeclaraciónDeVariableInmutable> ::=  <TipoDatoInmutable> “:” <Identificador> "_"
     */
    fun esDeclaracionDeVariableInmutable(): SentenciaDeclaracionVariableInmutable3? {
        val tipoDato = esTipoDato()

        if (tipoDato != null) {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.DOS_PUNTOS) {
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                    val nombreVar = tokenActual
                    obtenerSiguienteToken()
                    if (tokenActual.categoria == Categoria.FIN_SENTENCIA) {
                        obtenerSiguienteToken()
                        return SentenciaDeclaracionVariableInmutable3(tipoDato, nombreVar)
                    } else {
                        reportarError("Es preciso especificar el '_' al terminar de declarar la var inmutable")
                    }
                }
            } else {
                reportarError("Es preciso especificar ':' después de definir el tipo de dato jajaja")
            }
        }

        return null
    }


    /**
     * <DeclaraciónConjunto> ::=  Conjunto de tipoDato “:” <Identificador> "_"
     */
    fun esDeclaracionConjunto(): SentenciaDeclaracionArreglo3? {
        val posInicial = posicionActual
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "Conjunto") {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "is") {
                obtenerSiguienteToken()
                val tipoDato = esTipoDato()
                if (tipoDato != null) {
                    obtenerSiguienteToken()
                    if (tokenActual.categoria == Categoria.DOS_PUNTOS) {
                        obtenerSiguienteToken()
                        if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                            val nombreVar = tokenActual
                            obtenerSiguienteToken()
                            if (tokenActual.categoria == Categoria.FIN_SENTENCIA) {
                                obtenerSiguienteToken()
                                // Como todo bien, entonces
                                return SentenciaDeclaracionArreglo3(tipoDato, nombreVar)
                            } else {
                                reportarError("Es preciso especificar el '_' al terminar de declarar la var..")
                            }
                        } else {
                            reportarError("Debe especificar el nombre de la variable")
                        }
                    } else {
                        reportarError("Es preciso especificar ':' después de definir el tipo de dato")
                    }
                } else {
                    reportarError("Hace falta especificar tipo de dato")
                    hacerBT(posInicial)
                }
            } else {
                reportarError("Después de Conjunto debe seguir la pal.. 'de'")
            }
        }

        return null
    }



    /**
     * <SentenciaDeRetorno> ::= dev <Expresión> "_"
     */
    fun esRetorno(): SentenciaRetorno3? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "dev") {
            obtenerSiguienteToken()
            val expresion = esExpresion()
            if (expresion != null) {
                if (tokenActual.categoria == Categoria.FIN_SENTENCIA) {
                    obtenerSiguienteToken()
                    return SentenciaRetorno3(expresion)
                } else {
                    reportarError("Es preciso especificar el final de la sentencia")
                }
            } else {
                reportarError("Después de la palabra 'dev' debe seguir una expresión")
            }
        }
        return null
    }

    /**
     * <SentenciaAsignación> ::= Identificador opAsignación <Expresión> finSentencia
     */
    fun esAsignacion(): SentenciaAsignacion3? {
        val posIni = posicionActual
        if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
            val nombreVariable = tokenActual
            print("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$44444444444444444444Entra asignacion4444444444444444$$$$$$$$$$$$44444444")
            if (posicionActual < listaTokens.size + 1) {
                if (listaTokens[posicionActual + 1].categoria != Categoria.OPERADOR_DE_ASIGNACION) {
                    return null
                }
            }
            obtenerSiguienteToken()
            val operadorAsignacion = esOpAsignacion()
            if (operadorAsignacion != null) {
                val expresion = esExpresion()
                if (expresion != null) {
                    if (tokenActual.categoria == Categoria.FIN_SENTENCIA) {
                        obtenerSiguienteToken()
                        // Como todo va bien hasta acá
                        return SentenciaAsignacion3(nombreVariable, operadorAsignacion, expresion)
                    } else {
                        reportarError("Es preciso especificar el fin de la sentencia al asignar")
                    }
                }
            } else {
                reportarError("Después del nombre de la variable va un opAsignación")
                print("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% HACE BT EN ASIGNA %%%%%%%%%%%%%%%%%%%%%%%%%%%55%%%%%%%%5555")
                hacerBT(posIni)
            }
        }
        return null
    }

    /**
     * <InvocaciónDeFunción> ::= Identificador “[“[<ListaArgumentos>]”]” "_"
     */
    fun esInvocacionDeFuncion(): SentenciaInvocacionFuncion3? {
        if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
            val nombreFuncion = tokenActual
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.PARENTESIS_ABRIR) {
                obtenerSiguienteToken()
                val argumentos: ArrayList<Expresion3> = esListaArgumentos()
                print("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!EXPRESION CORRECTA!!!!!!!!!!!!!!!1111!!!!!!!!!!!!!!!!!!!!!!!!!!!")
                if (tokenActual.categoria == Categoria.PARENTESIS_CERRAR) {
                    obtenerSiguienteToken()
                    if (tokenActual.categoria == Categoria.FIN_SENTENCIA) {
                        obtenerSiguienteToken()
                        return SentenciaInvocacionFuncion3(nombreFuncion, argumentos)
                    } else {
                        reportarError("Falta fin de sentencia")
                    }
                } else {
                    reportarError("Falta paréntesis derecho")
                }
            } else {
                if (tokenActual.categoria != Categoria.OPERADOR_DE_ASIGNACION) {
                    reportarError("Falta paréntesis izquierdo")
                }

            }
        }
        return null

    }

    /**
     * <ListaArgumentos> ::= <Expresión> | <Expresión> ";" <ListaArgumentos>
     */
    fun esListaArgumentos(): ArrayList<Expresion3> {
        val listaExpresiones = ArrayList<Expresion3>()
        var expresion = esExpresion()

        if (expresion != null) {
            listaExpresiones.add(expresion)

            while (tokenActual.lexema == ";" || tokenActual.categoria == Categoria.SEPARADOR) {
                obtenerSiguienteToken()

                expresion = esExpresion()
                if (expresion != null) {
                    listaExpresiones.add(expresion)
                } else {
                    reportarError("El separador únicamente puede estar en medio de dos argumentos")
                    break
                }
            }
        }
        return listaExpresiones
    }

    /**
     * <Expresión> ::= <ExpresiónAritmética> | <ExpresiónCadena> | <ExpresiónRelacional> | <ExpresiónLógica>
     */
    fun esExpresion(): Expresion3? {
        val expresionLogica = esExpresionLogica()
        if (expresionLogica != null) {
            return expresionLogica
        }

        val expresionRelacional = esExpresionRelacional()
        if (expresionRelacional != null) {
            return expresionRelacional
        }

        val expresionArit = esExpresionAritmetica()
        if (expresionArit != null) {
            return expresionArit
        }
        /*
        val expresionCad = esExpresionCadena()
        if (expresionCad != null) {
            return expresionCad
        }*/


        return null
    }

    /**
     * <ExpresiónRelacional> ::= <ExpresiónAritmética> opRelacional <ExpresiónAritmética>
     */
    fun esExpresionRelacional(): ExpresionRelacional3? {
        val posInicial = posicionActual
        if (tokenActual.categoria == Categoria.PARENTESIS_ABRIR || tokenActual.lexema == "[") {
            obtenerSiguienteToken()
            val expresionAritmetica = esExpresionAritmetica()
            if (expresionAritmetica != null) {
                print("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEXPRESION ARITM ESTA BIENEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE")
                if (tokenActual.categoria == Categoria.OPERADOR_RELACIONAL) {
                    val operadorRel = tokenActual
                    obtenerSiguienteToken()
                    val expresionAritmetica2 = esExpresionAritmetica()
                    if (expresionAritmetica2 != null) {
                        print("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEXPRESION ARITM ESTA BIENEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE")
                        if (tokenActual.categoria != Categoria.CONCATENADOR || tokenActual.lexema != ",") {
                            if (tokenActual.categoria == Categoria.PARENTESIS_CERRAR || tokenActual.lexema == "]") {
                                obtenerSiguienteToken()
                                print("PPPPPPPPPPPPPPPPPPPPPPPPPPPPPARENTESIS DE CERRRAR TODO BIEN EXP ARITMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM")
                                return ExpresionRelacional3(expresionAritmetica, operadorRel, expresionAritmetica2)

                            } else {
                                reportarError("Hace falta paréntesis de cerrar exp rel")
                            }

                        } else {
                            hacerBT(posInicial)
                            return null
                        }
                    } else {
                        reportarError("Exresión relacional incompleta")
                    }
                } else {
                    // La ruta actual no es la correcta, por tanto, es pertinente volver al punto de partida
                    print("Ruta incorrecta, a lo mejor es una exp aritmé..")
                    hacerBT(posInicial)
                    return null
                }
            }
        } else {
            if (tokenActual.categoria == Categoria.CADENA_CARACTERES) {
                return null
            }
            val expresionAritmetica = esExpresionAritmetica()
            if (expresionAritmetica != null) {
                if (tokenActual.categoria == Categoria.OPERADOR_RELACIONAL) {
                    val operadorRel = tokenActual
                    obtenerSiguienteToken()
                    val expresionAritmetica2 = esExpresionAritmetica()
                    if (expresionAritmetica2 != null) {
                        if (tokenActual.categoria != Categoria.CONCATENADOR || tokenActual.lexema != ",") {
                            return ExpresionRelacional3(expresionAritmetica, operadorRel, expresionAritmetica2)
                        } else {
                            hacerBT(posInicial)
                            return null
                        }
                    } else {
                        reportarError("Exresión relacional incompleta")
                    }
                } else {
                    // La ruta actual no es la correcta, por tanto, es pertinente volver al punto de partida
                    print("Ruta incorrecta, a lo mejor es una exp aritmé..")
                    hacerBT(posInicial)
                    return null
                }
            }
        }
        return null
    }

    /**
     * <ExpresiónLógica> ::= ["["]<ExpresiónRelacional> opLógico <ExpresiónRelacional> ["]"]
     */
    fun esExpresionLogica(): ExpresionLogica3? {
        val posInicial = posicionActual
        if (tokenActual.categoria == Categoria.PARENTESIS_ABRIR) {
            obtenerSiguienteToken()
            val expresionRel1 = esExpresionRelacional()
            if (expresionRel1 != null) {
                print("-----------------------------------------ENTRA EXP REL 1 --------------------------- ")
                if (tokenActual.categoria == Categoria.OPERADOR_LOGICO) {
                    val operadorLo = tokenActual
                    obtenerSiguienteToken()
                    val expresionRel2 = esExpresionRelacional()
                    if (expresionRel2 != null) {
                        if (tokenActual.categoria != Categoria.CONCATENADOR || tokenActual.lexema != ",") {

                            if (tokenActual.categoria == Categoria.PARENTESIS_CERRAR) {
                                obtenerSiguienteToken()
                                return ExpresionLogica3(expresionRel1, operadorLo, expresionRel2)
                            } else {
                                reportarError("Hace falta paréntesis de cerrar en exp lóg")
                            }
                        } else {
                            hacerBT(posInicial)
                            return null
                        }
                    } else {
                        reportarError("Exresión lógica incompleta")
                    }
                } else {
                    // La ruta actual no es la correcta, por tanto, es pertinente volver al punto de partida
                    print("----------------------------------------NO ES EXP LOGICA --------------------------- ")
                    hacerBT(posInicial)
                    return null
                }
            } else {
                print("-----------------------------------------EPAAAAAAAAAAAAAAAAAAAAAAAAAAAA-------------------------------------- ")
                hacerBT(posInicial)
                return null
            }

        } else {
            if (tokenActual.categoria == Categoria.CADENA_CARACTERES) {
                return null
            }
            val expresionRel1 = esExpresionRelacional()
            if (expresionRel1 != null) {
                print("-----------------------------------------ENTRA EXP REL 1 DEL ELSE --------------------------- ")
                if (tokenActual.categoria == Categoria.OPERADOR_LOGICO) {
                    val operadorLo = tokenActual
                    obtenerSiguienteToken()
                    val expresionRel2 = esExpresionRelacional()
                    if (expresionRel2 != null) {
                        print("wwwwwwwwwwwwwwwwwwwwwwwwwWWWWWWWWW RECONOCE AMBAS EXP REL aaaaaaaaaaaAAAAAAAAAAAAAAAA")
                        if (tokenActual.categoria != Categoria.CONCATENADOR || tokenActual.lexema != ",") {

                            return ExpresionLogica3(expresionRel1, operadorLo, expresionRel2)
                        } else {
                            hacerBT(posInicial)
                            return null
                        }
                    } else {
                        reportarError("Exresión lógica incompleta")
                    }
                } else {
                    // La ruta actual no es la correcta, por tanto, es pertinente volver al punto de partida
                    print("----------------------------------------EPAPAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA --------------------------- ")
                    hacerBT(posInicial)
                    return null
                }
            } else {
                print("-----------------------------------------Mal camino EXP LÓG-------------------------------------- ")
                hacerBT(posInicial)
                return null
            }
        }


        return null
    }

    /**
     * <ExpresiónCadena> ::= cadenaDeCaracteres [","<Expresión> ] | <Expresión> “,” cadenaDeCaracteres
     */
    fun esExpresionCadena(): ExpresionCadena3? {
        val posicionI = posicionActual
        if (tokenActual.categoria == Categoria.CADENA_CARACTERES) {
            val cad = tokenActual
            obtenerSiguienteToken()
            print("############################ ENTRA CADENA ########################33")
            if (tokenActual.categoria == Categoria.CONCATENADOR || tokenActual.lexema == ",") {
                obtenerSiguienteToken()
                val expresion = esExpresion()
                if (expresion != null) {
                    print("----------------------------------RECONOCE EXP CAD----------------------------------------------------")
                    return ExpresionCadena3(cad, expresion)
                } else {
                    reportarError("Después de un concatenador sigue una expresión, en este caso")
                }

            }
            print("############################ CASI RETORNA ########################33")
            return ExpresionCadena3(cad, null)
        }
        val expresion = esExpresion()
        if (expresion != null) {
            if (tokenActual.categoria == Categoria.CONCATENADOR || tokenActual.lexema == ",") {
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.CADENA_CARACTERES) {
                    obtenerSiguienteToken()
                    val cad = tokenActual
                    return ExpresionCadena3(cad, expresion)
                } else {
                    reportarError("Después de un concatenador sigue una cadena, en este caso")
                }
            } else {
                reportarError("Falta la cadena de caract..")
                hacerBT(posicionI)

            }

        }
        return null
    }

    /**
     * <ExpresiónAritmética> ::= <ExpresiónAritmética> opAritmético <Término> | <Término>
     */
    fun esExpresionAritmetica(): ExpresionAritmetica3? {
        val posInicial = posicionActual
        val terminos: ArrayList<Termino3> = ArrayList<Termino3>()
        if (tokenActual.categoria == Categoria.PARENTESIS_ABRIR) {
            obtenerSiguienteToken()
            var termino = esTermino()
            if (termino != null) {
                terminos.add(termino)
                while (tokenActual.categoria == Categoria.OPERADOR_ADITIVO || tokenActual.categoria == Categoria.OPERADOR_ARITMETICO) {
                    obtenerSiguienteToken()
                    termino = esTermino()
                    if (termino != null) {
                        terminos.add(termino)
                    } else {
                        reportarError("Los op. aritméticos únicamente pueden estar en medio de dos términos..")
                        return null
                    }
                }
                if (tokenActual.categoria == Categoria.PARENTESIS_CERRAR) {
                    if (tokenActual.categoria != Categoria.CONCATENADOR || tokenActual.lexema != ",") {
                        obtenerSiguienteToken()
                        return ExpresionAritmetica3(terminos)
                    } else {
                        return null
                    }
                } else {
                    reportarError("Falta paréntesis de cerrar de expresión aritmética")
                }
            }
            reportarError("Termino fue nulo")
            hacerBT(posInicial)
        } else {
            if (tokenActual.categoria == Categoria.CADENA_CARACTERES) {
                return null
            }
            var termino = esTermino()
            if (termino != null) {
                terminos.add(termino)
                while (tokenActual.categoria == Categoria.OPERADOR_ADITIVO || tokenActual.categoria == Categoria.OPERADOR_ARITMETICO) {
                    obtenerSiguienteToken()
                    termino = esTermino()
                    if (termino != null) {
                        terminos.add(termino)
                    } else {
                        reportarError("Los op. aritméticos únicamente pueden estar en medio de dos términos..")
                        break
                    }
                }
                if (tokenActual.categoria != Categoria.CONCATENADOR || tokenActual.lexema != ",") {
                    return ExpresionAritmetica3(terminos)
                } else {
                    hacerBT(posInicial)
                    return null
                }


            }
            print("Termino fue nulo")
            hacerBT(posInicial)

        }

        return null

    }

    /**
     * <Término> ::= <Termino> ”<” “*” ”>” <Factor> | <Factor>
     */
    fun esTermino(): Termino3? {
        val posicionInicial = posicionActual
        val factores: ArrayList<Factor3> = ArrayList<Factor3>()
        if (tokenActual.categoria == Categoria.PARENTESIS_ABRIR) {
            obtenerSiguienteToken()
            var factor = esFactor()
            if (factor != null) {
                factores.add(factor)
                while (tokenActual.categoria == Categoria.OPERADOR_MULTIPLICATIVO) {
                    obtenerSiguienteToken()
                    factor = esFactor()
                    if (factor != null) {
                        factores.add(factor)
                    } else {
                        reportarError("Estando en Termino: El <*> únicamente puede estar en medio de dos factores o expresiones aritmé..")
                        return null
                    }
                }
                if (tokenActual.categoria == Categoria.PARENTESIS_CERRAR) {
                    obtenerSiguienteToken()
                    return Termino3(factores)
                } else {
                    reportarError("Hace falta el paréntess de cerrar")
                }

            } else {
                hacerBT(posicionInicial)
                return null
            }
        } else {
            if (tokenActual.categoria == Categoria.CADENA_CARACTERES) {
                return null
            }
            var factor = esFactor()
            if (factor != null) {
                factores.add(factor)
                while (tokenActual.categoria == Categoria.OPERADOR_MULTIPLICATIVO) {
                    obtenerSiguienteToken()
                    factor = esFactor()
                    if (factor != null) {
                        factores.add(factor)
                    } else {
                        reportarError("Estando en Termino: El <*> únicamente puede estar en medio de dos factores o expresiones aritmé..")
                        return null
                    }
                }
                return Termino3(factores)
            } else {
                hacerBT(posicionInicial)
            }
        }

        return null
    }

    /**
     *  <Factor> ::= “[“<ExpresiónAritmética>”]” | Identificador | <ValorNumérico>
     */
    fun esFactor(): Factor3? {
        val posicionInicial = posicionActual
        if (tokenActual.categoria == Categoria.PARENTESIS_ABRIR) {
            obtenerSiguienteToken()
            // Verficia si se trata de un identificador
            if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                val identificadr = tokenActual
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.PARENTESIS_CERRAR) {
                    obtenerSiguienteToken()
                    return Factor3(identificadr, null, null)
                } else {
                    reportarError("Hace falta espe.. paréntesis de cerrar fac")
                    return null
                }
            }
            // Verifica si se trata de un número
            val valorNum = esValorNumerico()
            if (valorNum != null) {
                if (tokenActual.categoria == Categoria.PARENTESIS_CERRAR) {
                    obtenerSiguienteToken()
                    return Factor3(null, valorNum, null)
                } else {
                    reportarError("Hace falta espe.. paréntesis de cerrar fac")
                }
            }
            val expresionArit = esExpresionAritmetica()
            if (expresionArit != null) {
                if (tokenActual.categoria == Categoria.PARENTESIS_CERRAR) {
                    obtenerSiguienteToken()
                    return Factor3(null, null, expresionArit)
                } else {
                    reportarError("Hace falta espe.. paréntesis de cerrar fac")
                }
            }
        } else {
            if (tokenActual.categoria == Categoria.CADENA_CARACTERES) {
                return null
            }
            // Verficia si se trata de un identificador
            if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                val identificadr = tokenActual
                obtenerSiguienteToken()
                return Factor3(identificadr, null, null)
            }
            // Verifica si se trata de un número
            val valorNum = esValorNumerico()
            if (valorNum != null) {
                return Factor3(null, valorNum, null)
            }
            val expresionArit = esExpresionAritmetica()
            if (expresionArit != null) {
                return Factor3(null, null, expresionArit)
            }
        }
        return null
    }


    /**
     * <ValorNumérico> ::= <Entero> | <Decimal> | <EnteroCorto> | <EnteroLargo> | <DecimalCorto> | <DecimalLargo>
     */
    fun esValorNumerico(): ValorNumerico3? {
        if (tokenActual.categoria == Categoria.ENTERO || tokenActual.categoria == Categoria.DECIMAL
                || tokenActual.categoria == Categoria.ENTERO_CORTO || tokenActual.categoria == Categoria.ENTERO_LARGO
                || tokenActual.categoria == Categoria.DECIMAL_LARGO || tokenActual.categoria == Categoria.DECIMAL_CORTO) {
            val valorN = tokenActual
            obtenerSiguienteToken()
            return ValorNumerico3(valorN)
        }
        return null
    }

    /**
     *
     */
    /**
     * <ValorNumérico> ::= <Entero> | <Decimal> | <EnteroCorto> | <EnteroLargo> | <DecimalCorto> | <DecimalLargo>
     */
    fun esOpAsignacion(): Token? {
        if (tokenActual.categoria == Categoria.OPERADOR_DE_ASIGNACION) {
            val valorN = tokenActual
            obtenerSiguienteToken()
            return valorN
        }
        return null
    }

}