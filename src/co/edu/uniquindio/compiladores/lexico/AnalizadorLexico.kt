package co.edu.uniquindio.compiladores.lexico

/**
 * Lenguaje de programaci�n Helix
 * Realizado por: V�ctor Guti�rrez V�squez, Mauricio Tiburcio Tabares Mar�n, William Casta�eda L�pez
 * Fecha de entrega: 16-10-2020
 * Versi�n en desarrollo: 1.0
 * Profesor: Carlos Andres Flores V.
 */
class AnalizadorLexico(var codigoFuente: String)
{

    var caracterActual = codigoFuente[0]  //se captura el primer caracter de la cadena que entra por par�metro
    var listaTokens = ArrayList<Token>()  //se declara una lista para almacenar los tokens v�lidos
    var posicionActual = 0  // se declara una variable que va a permitir conocer la posici�n actual en un momento determinado
    var listaErrores = ArrayList<Token>()  //se declara una lista para almacenar los errores identificados
    var finCodigo = 0.toChar()
    var filaActual = 0
    var columnaActual = 0

    /**
     * M�todo que permite volver a un punto determindado, para decartar una posibilidad, y volver a intentar desde
     * ese mismo punto para validar otra categor�a
     * @param: posicionInicial (posici�n a la cual se desea volver), filaInicial (fila la cual se desea volver), columnaInicial (col a la cual se desea volver)
     */
    fun hacerBT(posicionInicial: Int, filaInicial: Int, columnaInicial: Int)
    {
        filaActual = filaInicial
        columnaActual = columnaInicial
        posicionActual = posicionInicial
        caracterActual = codigoFuente[posicionActual]
    }

    /**
     * M�todo que permite almacenar un token previamente validado por alguna categor�a, en la lista de los tokens validados
     * @param: lexema (token que se desea almacenar), categoria (categor�a de ese token), fila (fila inicial), columna (col inicial)
     */
    fun almacenarTokenErroneo(lexema: String, categoria: Categoria, fila: Int, columna: Int) = listaErrores.add(Token(lexema, categoria, fila, columna))

    /**
     * M�todo que permite almacenar un token previamente validado por alguna categor�a, en la lista de los tokens validados
     * @param: lexema (token que se desea almacenar), categoria (categor�a de ese token), fila (fila inicial), columna (col inicial)
     */
    fun almacenarToken(lexema: String, categoria: Categoria, fila: Int, columna: Int)
    {
        if (categoria != Categoria.NO_RECONOCIDO)
        {
            listaTokens.add(Token(lexema, categoria, fila, columna))
        }
        else
        {
            almacenarTokenErroneo(lexema, categoria, fila, columna)
        }

    }


    /**
     * M�todo que permite validar los token por categor�as, una por una hasta hallar la indicada
     */
    fun analizar()
    {
        while (caracterActual != finCodigo)
        {
            while (caracterActual == ' ')
            {
                obtenerSiguienteCaracter()
            }


            if (esEntero()) continue
            if (esEnteroCorto()) continue
            if (esEnteroLargo()) continue
            if (esDecimal()) continue
            if (esDecimalCorto()) continue
            if (esDecimalLargo()) continue
            if (esIdentificador()) continue
            if (esPalabraReservada()) continue
            if (esOperadorLogico()) continue
            if (esCadenaCaracteres()) continue
            if (esCaracter()) continue
            if (esOperadorRelacional()) continue
            if (esOperadorAsignacion()) continue
            if (esOperadorAditivo()) continue
            if (esOperadorMultiplicativo()) continue
            if (esOperadorIncremento()) continue
            if (esOperadorDecremento()) continue
            if (esComentarioBloque()) continue
            if (esComentarioLinea()) continue
            //if (esLlaveAbrir()) continue
            //if (esLlaveCerrar()) continue
            if (esOperadorInicial()) continue
            if (esOperadorTerminal()) continue
            if (esComentarioLinea()) continue
            if (esSeparador()) continue
            if (esParentesisAbrir()) continue
            if (esParentesisCerrar()) continue
            if (esFinSentencia()) continue
            if (esCorcheteAbrir()) continue
            if (esCorcheteCerrar()) continue
            if (esDosPuntos()) continue
            if (esConcatenador()) continue

            if (caracterActual == ' ') {
                obtenerSiguienteCaracter()
                while (caracterActual == ' ') {
                    obtenerSiguienteCaracter()
                }
            } else {
                almacenarToken(lexema = "" + caracterActual, categoria = Categoria.NO_RECONOCIDO, fila = filaActual, columna = columnaActual)
                obtenerSiguienteCaracter()
            }
        }

    }


    /**
     * M�todo que valida un n�mero entero en el lenguaje Helix
     */
    fun esEntero(): Boolean {
        //En Helix, los decimales se definen con un '$' al principio
        if (caracterActual == '$') {
            var lexema = ""
            var filaInicial = filaActual  // se guarda la fila desde la cual inicia
            var columnaInicial = columnaActual  // se guarda la columna desde la cual inici�
            var posicionInicial = posicionActual // se guarda la posici�n en la cual inici�
            lexema += caracterActual  // se almacena el caracter que satisface los requisitos
            obtenerSiguienteCaracter()
            //verifica si el siguiente caracter es un d�gito
            if (caracterActual.isDigit()) {

                // verifica si sigue un d�gito o una secuencia de d�gitos
                while (caracterActual.isDigit()) {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }
                // en caso de que el caracter siguiente sea un ',', se debe deshacer el proceso, puesto que probablemente se trate de un decimal
                if (caracterActual == ',' || caracterActual == '_') {
                    if (caracterActual == ',') {

                        if (posicionActual < codigoFuente.length - 1) {
                            if (!codigoFuente[posicionActual + 1].isDigit()) {
                                //posicionActual = posicionActual-1
                                //columnaActual = columnaActual-1
                                //lexema = lexema.substring( 0, lexema.length- )
                                almacenarToken(lexema, Categoria.ENTERO, filaInicial, columnaInicial)
                                return true
                            }
                        }
                    } else {
                        if (posicionActual < codigoFuente.length - 1) {
                            if (codigoFuente[posicionActual + 1] != 'c' || codigoFuente[posicionActual + 1] != 'l') {
                                //posicionActual = posicionActual-1
                                //columnaActual = columnaActual-1
                                //lexema = lexema.substring( 0, lexema.length- )
                                almacenarToken(lexema, Categoria.ENTERO, filaInicial, columnaInicial)
                                return true
                            }
                        }
                    }
                    hacerBT(posicionInicial, filaInicial, columnaInicial)  // se busca descartar la posibilidad de que sea un entero, para ello se deben partir desde el punto inicial, para validar otra cat
                    return false
                }
                almacenarToken(lexema, Categoria.ENTERO, filaInicial, columnaInicial)
                return true
            }
        }
        //Si el primer caracter no es un '$', entonces no es un entero
        return false
    }

    /**
     * M�todo que valida un n�mero entero en el lenguaje Helix
     */
    fun esEnteroCorto(): Boolean {
        //En Helix, los decimales se definen con un '$' al principio
        if (caracterActual == '$') {
            var lexema = ""
            var filaInicial = filaActual  // se guarda la fila desde la cual inicia
            var columnaInicial = columnaActual  // se guarda la columna desde la cual inici�
            var posicionInicial = posicionActual // se guarda la posici�n en la cual inici�
            lexema += caracterActual  // se almacena el caracter que satisface los requisitos
            obtenerSiguienteCaracter()
            //verifica si el siguiente caracter es un d�gito
            if (caracterActual.isDigit()) {

                // verifica si sigue un d�gito o una secuencia de d�gitos
                while (caracterActual.isDigit()) {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }
                // en caso de que el caracter siguiente sea un '.', se debe deshacer el proceso, puesto que probablemente se trate de un decimal
                if (caracterActual == ',') {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)  // se busca descartar la posibilidad de que sea un entero, para ello se deben partir desde el punto inicial, para validar otra cat
                    return false
                }
                if (caracterActual == '_') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'c') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        almacenarToken(lexema, Categoria.ENTERO_CORTO, filaInicial, columnaInicial)
                        return true
                    } else {

                        hacerBT(posicionInicial, filaInicial, columnaInicial)  // se busca descartar la posibilidad de que sea un entero, para ello se deben partir desde el punto inicial, para validar otra cat
                        return false
                    }

                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)  // se busca descartar la posibilidad de que sea un entero, para ello se deben partir desde el punto inicial, para validar otra cat
                    return false
                }

            }
        }
        //Si el primer caracter no es un '$', entonces no es un entero
        return false
    }

    /**
     * M�todo que valida un entero corto
     */
    fun esEnteroLargo(): Boolean {
        //En Helix, los decimales se definen con un '$' al principio
        if (caracterActual == '$') {
            var lexema = ""
            var filaInicial = filaActual  // se guarda la fila desde la cual inicia
            var columnaInicial = columnaActual  // se guarda la columna desde la cual inici�
            var posicionInicial = posicionActual // se guarda la posici�n en la cual inici�
            lexema += caracterActual  // se almacena el caracter que satisface los requisitos
            obtenerSiguienteCaracter()
            //verifica si el siguiente caracter es un d�gito
            if (caracterActual.isDigit()) {

                // verifica si sigue un d�gito o una secuencia de d�gitos
                while (caracterActual.isDigit()) {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }
                // en caso de que el caracter siguiente sea un '.', se debe deshacer el proceso, puesto que probablemente se trate de un decimal
                if (caracterActual == ',') {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)  // se busca descartar la posibilidad de que sea un entero, para ello se deben partir desde el punto inicial, para validar otra cat
                    return false
                }
                if (caracterActual == '_') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'l') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        almacenarToken(lexema, Categoria.ENTERO_LARGO, filaInicial, columnaInicial)
                        return true
                    } else {

                        hacerBT(posicionInicial, filaInicial, columnaInicial)  // se busca descartar la posibilidad de que sea un entero, para ello se deben partir desde el punto inicial, para validar otra cat
                        return false
                    }

                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)  // se busca descartar la posibilidad de que sea un entero, para ello se deben partir desde el punto inicial, para validar otra cat
                    return false
                }

            }
        }
        //Si el primer caracter no es un '$', entonces no es un entero
        return false
    }

    /**
     * M�todo que valida un n�mero decimal en Helix
     */
    fun esDecimal(): Boolean {
        //En Helix, los decimales se definen con un '$' al principio
        if (caracterActual == '$') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual.isDigit()) {

                while (caracterActual.isDigit()) {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }
                //En Helix, los decimales en vez de puntos llevan una ','
                if (caracterActual == ',') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()

                    //Verifica si despu�s de la coma sigue un d�gito
                    if (caracterActual.isDigit()) {
                        while (caracterActual.isDigit()) {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                        }
                        if (caracterActual != '_') {
                            almacenarToken(lexema, Categoria.DECIMAL, filaInicial, columnaInicial)
                            return true
                        }
                    }
                }
            }
            hacerBT(posicionInicial, filaInicial, columnaInicial)
            return false
        }
        //Si el primer caracter no es un '$', entonces no es un decimal
        return false
    }

    /**
     * M�todo que valida un n�mero decimal corto en Helix
     */
    fun esDecimalCorto(): Boolean {
        //En Helix, los decimales se definen con un '$' al principio
        if (caracterActual == '$') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual.isDigit()) {

                while (caracterActual.isDigit()) {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }
                //En Helix, los decimales en vez de puntos llevan una ','
                if (caracterActual == ',') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()

                    //Verifica si despu�s de la coma sigue un d�gito
                    if (caracterActual.isDigit()) {
                        while (caracterActual.isDigit()) {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                        }
                        if (caracterActual == '_') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            if (caracterActual == 'c') {
                                lexema += caracterActual
                                obtenerSiguienteCaracter()
                                almacenarToken(lexema, Categoria.DECIMAL_CORTO, filaInicial, columnaInicial)
                                return true
                            }
                        }
                    }
                }
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        //Si el primer caracter no es un '$', entonces no es un decimal
        return false
    }

    /**
     * M�todo que valida un n�mero decimal largo en Helix
     */
    fun esDecimalLargo(): Boolean {
        //En Helix, los decimales se definen con un '$' al principio
        if (caracterActual == '$') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual.isDigit()) {

                while (caracterActual.isDigit()) {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }
                //En Helix, los decimales en vez de puntos llevan una ','
                if (caracterActual == ',') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()

                    //Verifica si despu�s de la coma sigue un d�gito
                    if (caracterActual.isDigit()) {
                        while (caracterActual.isDigit()) {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                        }
                        if (caracterActual == '_') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            if (caracterActual == 'l') {
                                lexema += caracterActual
                                obtenerSiguienteCaracter()
                                almacenarToken(lexema, Categoria.DECIMAL_LARGO, filaInicial, columnaInicial)
                                return true
                            }
                        }
                    }
                }
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        //Si el primer caracter no es un '$', entonces no es un decimal
        return false
    }


    /**
     * M�todo que valida un identificador en Helix, como m�ximo debe tener 10 caracteres
     */
    fun esIdentificador(): Boolean {
        var lexema = ""
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual

        if (caracterActual == 'A') {

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == 'N') {

                lexema += caracterActual
                obtenerSiguienteCaracter()

                if (caracterActual == 'D') {

                    lexema += caracterActual
                    obtenerSiguienteCaracter()

                    if (caracterActual != finCodigo) {

                        if (caracterActual == '#' || caracterActual.isLetter() || caracterActual.isDigit()) {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                }
            }
        }
        if (caracterActual == 'O') {

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == 'R') {

                lexema += caracterActual
                obtenerSiguienteCaracter()

                if (caracterActual != finCodigo) {

                    if (caracterActual == '#' || caracterActual.isLetter() || caracterActual.isDigit()) {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
            }
        }

        lexema = ""

        // Descarta la posibilidad de confundir una palabra reservada con un identificador
        if (esPalabraReservada()) {
            hacerBT(posicionInicial, filaInicial, columnaInicial)
            listaTokens.removeAt(listaTokens.size - 1)
            return false
        }


        // En Helix, cada identificador debe empezar con una letra o un '#'
        if (caracterActual.isLetter() || caracterActual == '#') {
            lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()

            // Dado que solo se permite que tenga hasta 10 caracteres, se especifica la condici�n lexema.length <= 10
            while (lexema.length <= 10 && (caracterActual.isLetter() || caracterActual == '#' || caracterActual.isDigit())) {
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }
            almacenarToken(lexema, Categoria.IDENTIFICADOR, filaInicial, columnaInicial)
            return true

        }
        return false
    }

    /**
     * M�todo que valida una cadena de caracteres en Helix
     */
    fun esCadenaCaracteres(): Boolean {

        if (caracterActual == '|') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            while (caracterActual != finCodigo) {

                if (caracterActual == '&') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'T' || caracterActual == 'D' || caracterActual == 'S') {
                        //lexema += caracterActual
                    } else {
                        while (caracterActual != finCodigo) {
                            if (caracterActual == '|') {
                                lexema += caracterActual
                                obtenerSiguienteCaracter()
                                almacenarTokenErroneo(lexema, Categoria.ERROR, filaInicial, columnaInicial)
                                return true
                            }
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                        }
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false

                    }

                }

                lexema += caracterActual
                obtenerSiguienteCaracter()

                if (caracterActual == '|') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.CADENA_CARACTERES, filaInicial, columnaInicial)
                    return true
                }
            }

            hacerBT(posicionInicial, filaInicial, columnaInicial)
            return false
        }
        return false
    }


    /**
     * M�todo que valida un XXXXXXXXXXXXXXXXX en Helix
     */
    fun esDosPuntos(): Boolean {
        if (caracterActual == ':') {

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            almacenarToken(lexema, Categoria.DOS_PUNTOS, filaInicial, columnaInicial)
            return true

        }
        return false
    }


    /**
     * M�todo que valida un caractere en Helix
     */
    fun esCaracter(): Boolean {
        if (caracterActual == '\"') {

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == '&') {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == '\"') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema, Categoria.CARACTER, filaInicial, columnaInicial)
                return true
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
            }
        }
        return false
    }

    /**
     * M�todo que valida un operador relacional en Helix
     */
    fun esOperadorRelacional(): Boolean {
        if (caracterActual == '<' || caracterActual == '>' || caracterActual == '?' || caracterActual == '!') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual

            if (caracterActual == '!') {
                obtenerSiguienteCaracter()
                if (caracterActual == '!') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.OPERADOR_RELACIONAL, filaInicial, columnaInicial)
                    return true
                }
            }

            obtenerSiguienteCaracter()

            if (caracterActual == '?') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == '?') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.OPERADOR_RELACIONAL, filaInicial, columnaInicial)
                    return true
                }
                almacenarToken(lexema, Categoria.OPERADOR_RELACIONAL, filaInicial, columnaInicial)
                return true
            }
            hacerBT(posicionInicial, filaInicial, columnaInicial)
        }
        return false
    }

    /**
     * M�todo que valida un operador de asignaci�n en Helix
     */
    fun esOperadorAsignacion(): Boolean {
        if (caracterActual == '@') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == '+' || caracterActual == '-' || caracterActual == '*' || caracterActual == '/' || caracterActual == '%') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema, Categoria.OPERADOR_DE_ASIGNACION, filaInicial, columnaInicial)
                return true
            }
            almacenarToken(lexema, Categoria.OPERADOR_DE_ASIGNACION, filaInicial, columnaInicial)
            return true
        }
        return false
    }

    /**
     * M�todo que valida un operador aditivo en Helix
     */
    fun esOperadorAditivo(): Boolean {
        if (caracterActual == '<') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == '+' || caracterActual == '-') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == '>') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.OPERADOR_ADITIVO, filaInicial, columnaInicial)
                    return true
                }
            }

            hacerBT(posicionInicial, filaInicial, columnaInicial)
        }
        return false
    }

    /**
     * M�todo que valida un operador multiplicativo en Helix
     */
    fun esOperadorMultiplicativo(): Boolean {
        if (caracterActual == '<') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == '*' || caracterActual == '/' || caracterActual == '%') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == '>') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.OPERADOR_MULTIPLICATIVO, filaInicial, columnaInicial)
                    return true
                }
            }

            hacerBT(posicionInicial, filaInicial, columnaInicial)
        }
        return false
    }

    /** M�todo que valida operador incremento en Helix
     *
     */
    fun esOperadorIncremento(): Boolean {
        if (caracterActual == '+') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual


            lexema += caracterActual
            obtenerSiguienteCaracter()

            almacenarToken(lexema, Categoria.OPERADOR_INCREMENTO, filaInicial, columnaInicial)
            return true
        }
        return false
    }

    /** M�todo que valida operador decremento en Helix
     *
     */
    fun esOperadorDecremento(): Boolean {
        if (caracterActual == '-') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual


            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == '-') {
                obtenerSiguienteCaracter()
                if (caracterActual == '>') {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            }


            almacenarToken(lexema, Categoria.OPERADOR_DECREMENTO, filaInicial, columnaInicial)
            return true
        }
        return false
    }

    /**
     * M�todo que valida un operador l�gico en Helix
     */
    fun esOperadorLogico(): Boolean {
        if (caracterActual == 'A') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'N') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'D') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.OPERADOR_LOGICO, filaInicial, columnaInicial)
                    return true
                }
            }
            hacerBT(posicionInicial, filaInicial, columnaInicial)
            return false

        }
        if (caracterActual == 'O') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == 'R') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema, Categoria.OPERADOR_LOGICO, filaInicial, columnaInicial)
                return true
            }

            hacerBT(posicionInicial, filaInicial, columnaInicial)
            return false

        }
        return false
    }

    /**
     * M�todo que valida un comentario bloque en Helix
     */
    fun esComentarioBloque(): Boolean {
        if (caracterActual == '~') {

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            while (caracterActual != finCodigo) {

                if (caracterActual == '~') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.COMENRARIO_BLOQUE, filaInicial, columnaInicial)
                    return true
                }
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }
            almacenarTokenErroneo(lexema, Categoria.ERROR, filaInicial, columnaInicial)
            return true

        }
        return false
    }

    /**
     * M�todo que valida un comentario l�nea en Helix
     */
    fun esComentarioLinea(): Boolean {
        if (caracterActual == '�') {

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            while (caracterActual != finCodigo && filaInicial == filaActual) {

                lexema += caracterActual
                obtenerSiguienteCaracter()
            }
            if (caracterActual != finCodigo) {
                lexema = lexema.substring(0, lexema.length - 1)
            }
            almacenarToken(lexema, Categoria.COMENTARIO_LINEA, filaInicial, columnaInicial)
            return true
        }
        return false
    }

    /**
     * M�todo que valida llave abrir
     */
    fun esOperadorInicial(): Boolean {

        if (caracterActual == '-') {

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual


            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == '-') {
                lexema += caracterActual
                obtenerSiguienteCaracter()

                if (caracterActual == '>') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.LLAVE_ABRIR, filaInicial, columnaInicial)
                    return true
                }
            }
            hacerBT(posicionInicial, filaInicial, columnaInicial)
        }

        return false

    }

    /**
     * M�todo que valida llave cerrar
     */
    fun esOperadorTerminal(): Boolean {
        if (caracterActual == '<') {

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual


            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == '-') {
                lexema += caracterActual
                obtenerSiguienteCaracter()

                if (caracterActual == '-') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.LLAVE_CERRAR, filaInicial, columnaInicial)
                    return true
                }
            }
            hacerBT(posicionInicial, filaInicial, columnaInicial)
        }

        return false


    }

    /** M�todo que valida corchete abrir
     *
     */
    fun esCorcheteAbrir(): Boolean {
        if (caracterActual == '{') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual


            lexema += caracterActual
            obtenerSiguienteCaracter()

            almacenarToken(lexema, Categoria.CORCHETE_ABRIR, filaInicial, columnaInicial)
            return true
        }
        return false
    }

    /** M�todo que valida un concatenador ","
     *
     */
    fun esConcatenador(): Boolean {
        if (caracterActual == ',') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual


            lexema += caracterActual
            obtenerSiguienteCaracter()

            almacenarToken(lexema, Categoria.CONCATENADOR, filaInicial, columnaInicial)
            return true
        }
        return false
    }

    /** M�todo que valida corchete cerrar
     *
     */
    fun esCorcheteCerrar(): Boolean {
        if (caracterActual == '}') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual


            lexema += caracterActual
            obtenerSiguienteCaracter()

            almacenarToken(lexema, Categoria.CORCHETE_CERRAR, filaInicial, columnaInicial)
            return true
        }
        return false
    }

    /** M�todo que valida par�ntesis de abrir
     *
     */
    fun esParentesisAbrir(): Boolean {
        if (caracterActual == '[') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual


            lexema += caracterActual
            obtenerSiguienteCaracter()

            almacenarToken(lexema, Categoria.PARENTESIS_ABRIR, filaInicial, columnaInicial)
            return true
        }
        return false
    }

    /** M�todo que valida par�ntesis de cerrar
     *
     */
    fun esParentesisCerrar(): Boolean {
        if (caracterActual == ']') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual


            lexema += caracterActual
            obtenerSiguienteCaracter()

            almacenarToken(lexema, Categoria.PARENTESIS_CERRAR, filaInicial, columnaInicial)
            return true
        }
        return false
    }

    /** M�todo que valida separador en Helix
     *
     */
    fun esSeparador(): Boolean {
        if (caracterActual == ';') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual


            lexema += caracterActual
            obtenerSiguienteCaracter()

            almacenarToken(lexema, Categoria.SEPARADOR, filaInicial, columnaInicial)
            return true
        }
        return false
    }

    /** M�todo que valida fin de sentencia en Helix
     *
     */
    fun esFinSentencia(): Boolean {
        if (caracterActual == '_') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual


            lexema += caracterActual
            obtenerSiguienteCaracter()

            almacenarToken(lexema, Categoria.FIN_SENTENCIA, filaInicial, columnaInicial)
            return true
        }
        return false
    }

    /**
     * M�todo que valida una palabra reservada en Helix
     * @Return true: si es una pal reservada, false: si no lo es
     */
    fun esPalabraReservada(): Boolean {
        var lexema = ""  // variable que va a almacenar la palabra reconocida
        var filaInicial = filaActual  // se captura la fila desde la cual inicia
        var columnaInicial = columnaActual  // se captura la columna desde la cual inicia
        var posicionInicial = posicionActual  // se captura la posici�n desde la cual inicia

        // Verifica pal reservada 'rup' <--> 'break'
        if (caracterActual == 'n') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'o') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 't') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()


                    if (caracterActual != '#' && !caracterActual.isDigit() && !caracterActual.isLetter()) {
                        almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                        return true
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                }
            }
        }
        // se inician las variables desde el punto de inicio, para continual validando
        hacerBT(posicionInicial, filaInicial, columnaInicial)

        lexema = ""
        filaInicial = filaActual
        columnaInicial = columnaActual
        posicionInicial = posicionActual







        // Pal reservada 'extensive' <--> 'public'
        if (caracterActual == 'e') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'x') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 't') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'e') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 'n') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            if (caracterActual == 's') {
                                lexema += caracterActual
                                obtenerSiguienteCaracter()
                                if (caracterActual == 'i') {
                                    lexema += caracterActual
                                    obtenerSiguienteCaracter()
                                    if (caracterActual == 'v') {
                                        lexema += caracterActual
                                        obtenerSiguienteCaracter()
                                        if (caracterActual == 'e') {
                                            lexema += caracterActual
                                            obtenerSiguienteCaracter()

                                            if (caracterActual != '#' && !caracterActual.isDigit() && !caracterActual.isLetter()) {
                                                almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                                                return true
                                            } else {
                                                hacerBT(posicionInicial, filaInicial, columnaInicial)
                                                return false
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        // se inician las variables desde el punto de inicio, para continual validando
        hacerBT(posicionInicial, filaInicial, columnaInicial)
        lexema = ""
        filaInicial = filaActual
        columnaInicial = columnaActual
        posicionInicial = posicionActual
        // Verifica pal reservada 'limited' <--> 'private'
        if (caracterActual == 'l') {
            print("Entro")
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'i') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'm') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'i') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 't') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            if (caracterActual == 'e') {
                                lexema += caracterActual
                                obtenerSiguienteCaracter()
                                if (caracterActual == 'd') {
                                    lexema += caracterActual
                                    obtenerSiguienteCaracter()


                                    if (caracterActual != '#' && !caracterActual.isDigit() && !caracterActual.isLetter()) {
                                        almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                                        return true
                                    } else {
                                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                                        return false
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
        // se inician las variables desde el punto de inicio, para continual validando
        hacerBT(posicionInicial, filaInicial, columnaInicial)
        lexema = ""
        filaInicial = filaActual
        columnaInicial = columnaActual
        posicionInicial = posicionActual

        // Pal reservada 'privileged' <--> 'protected'
        if (caracterActual == 'p') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'r') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'i') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'v') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 'i') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            if (caracterActual == 'l') {
                                lexema += caracterActual
                                obtenerSiguienteCaracter()
                                if (caracterActual == 'e') {
                                    lexema += caracterActual
                                    obtenerSiguienteCaracter()
                                    if (caracterActual == 'g') {
                                        lexema += caracterActual
                                        obtenerSiguienteCaracter()
                                        if (caracterActual == 'e') {
                                            lexema += caracterActual
                                            obtenerSiguienteCaracter()
                                            if (caracterActual == 'd') {
                                                lexema += caracterActual
                                                obtenerSiguienteCaracter()


                                                if (caracterActual != '#' && !caracterActual.isDigit() && !caracterActual.isLetter()) {
                                                    almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                                                    return true
                                                } else {
                                                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                                                    return false
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // se inician las variables desde el punto de inicio, para continual validando
        hacerBT(posicionInicial, filaInicial, columnaInicial)
        lexema = ""
        filaInicial = filaActual
        columnaInicial = columnaActual
        posicionInicial = posicionActual

        // Verifica pal reservada 'no' <--> '!'
        if (caracterActual == 'n') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'o') {
                lexema += caracterActual
                obtenerSiguienteCaracter()


                if (caracterActual != '#' && !caracterActual.isDigit() && !caracterActual.isLetter()) {
                    almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                    return true
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            }
        }


        // se inician las variables desde el punto de inicio, para continual validando
        hacerBT(posicionInicial, filaInicial, columnaInicial)
        lexema = ""
        filaInicial = filaActual
        columnaInicial = columnaActual
        posicionInicial = posicionActual

        // Verifica pal reservada 'to' <--> 'for'
        if (caracterActual == 't') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'o') {
                lexema += caracterActual
                obtenerSiguienteCaracter()


                if (caracterActual != '#' && !caracterActual.isDigit() && !caracterActual.isLetter()) {
                    almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                    return true
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            }
        }


        // se inician las variables desde el punto de inicio, para continual validando
        hacerBT(posicionInicial, filaInicial, columnaInicial)
        lexema = ""
        filaInicial = filaActual
        columnaInicial = columnaActual
        posicionInicial = posicionActual

        // Verifica pal reservada 'to' <--> 'for'
        if (caracterActual == 'n') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'o') {
                lexema += caracterActual
                obtenerSiguienteCaracter()


                if (caracterActual != '#' && !caracterActual.isDigit() && !caracterActual.isLetter()) {
                    almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                    return true
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            }
        }

        hacerBT(posicionInicial, filaInicial, columnaInicial)
        lexema = ""
        filaInicial = filaActual
        columnaInicial = columnaActual
        posicionInicial = posicionActual

        // Verifica pal reservada 'do' <--> 'hacer'
        if (caracterActual == 'd') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'o') {
                lexema += caracterActual
                obtenerSiguienteCaracter()


                if (caracterActual != '#' && !caracterActual.isDigit() && !caracterActual.isLetter()) {
                    almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                    return true
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            }
        }


        hacerBT(posicionInicial, filaInicial, columnaInicial)
        lexema = ""
        filaInicial = filaActual
        columnaInicial = columnaActual
        posicionInicial = posicionActual

        // Verifica pal reservada 'conjunto' <--> 'lista'
        if (caracterActual == 'C') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'o') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'n') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'j') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 'u') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            if (caracterActual == 'n') {
                                lexema += caracterActual
                                obtenerSiguienteCaracter()
                                if (caracterActual == 't') {
                                    lexema += caracterActual
                                    obtenerSiguienteCaracter()
                                    if (caracterActual == 'o') {
                                        lexema += caracterActual
                                        obtenerSiguienteCaracter()

                                        if (caracterActual != '#' && !caracterActual.isDigit() && !caracterActual.isLetter()) {
                                            almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                                            return true
                                        } else {
                                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                                            return false
                                        }
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }


        // se inician las variables desde el punto de inicio, para continual validando
        hacerBT(posicionInicial, filaInicial, columnaInicial)
        lexema = ""
        filaInicial = filaActual
        columnaInicial = columnaActual
        posicionInicial = posicionActual

        // Verifica pal reservada 'whereas' <--> 'while'
        if (caracterActual == 'w') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'h') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'e') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'r') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 'e') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            if (caracterActual == 'a') {
                                lexema += caracterActual
                                obtenerSiguienteCaracter()
                                if (caracterActual == 's') {
                                    lexema += caracterActual
                                    obtenerSiguienteCaracter()

                                    if (caracterActual != '#' && !caracterActual.isDigit() && !caracterActual.isLetter()) {
                                        almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                                        return true
                                    } else {
                                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                                        return false
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
        // se inician las variables desde el punto de inicio, para continual validando
        hacerBT(posicionInicial, filaInicial, columnaInicial)
        lexema = ""
        filaInicial = filaActual
        columnaInicial = columnaActual
        posicionInicial = posicionActual

        // Verifica pal reservada 'change' <--> 'switch'
        if (caracterActual == 'c') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'h') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'a') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'n') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 'g') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            if (caracterActual == 'e') {

                                lexema += caracterActual
                                obtenerSiguienteCaracter()

                                if (caracterActual != '#' && !caracterActual.isDigit() && !caracterActual.isLetter()) {
                                    almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                                    return true
                                } else {
                                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                                    return false
                                }
                            }
                        }
                    }
                }
            }
        }
        // se inician las variables desde el punto de inicio, para continual validando
        hacerBT(posicionInicial, filaInicial, columnaInicial)
        lexema = ""
        filaInicial = filaActual
        columnaInicial = columnaActual
        posicionInicial = posicionActual

        // Verifica pal reservada 'Entero' <--> 'int'
        if (caracterActual == 'e') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'n') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 't') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'e') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 'r') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            if (caracterActual == 'o') {

                                lexema += caracterActual
                                obtenerSiguienteCaracter()

                                if (caracterActual != '#' && !caracterActual.isDigit() && !caracterActual.isLetter()) {
                                    almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                                    return true
                                } else {
                                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                                    return false
                                }
                            }
                        }
                    }
                }
            }
        }
        // se inician las variables desde el punto de inicio, para continual validando
        hacerBT(posicionInicial, filaInicial, columnaInicial)
        lexema = ""
        filaInicial = filaActual
        columnaInicial = columnaActual
        posicionInicial = posicionActual

        // Verifica pal reservada 'cad' <--> 'String'
        if (caracterActual == 'c') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'a') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'd') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()


                    if (caracterActual != '#' && !caracterActual.isDigit() && !caracterActual.isLetter()) {
                        almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                        return true
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                }
            }
        }

        // se inician las variables desde el punto de inicio, para continual validando
        hacerBT(posicionInicial, filaInicial, columnaInicial)
        lexema = ""
        filaInicial = filaActual
        columnaInicial = columnaActual
        posicionInicial = posicionActual


        if (caracterActual == 'i') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 's') {
                lexema += caracterActual
                obtenerSiguienteCaracter()

                if (caracterActual != '#' && !caracterActual.isDigit() && !caracterActual.isLetter()) {
                    almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                    return true
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            }
        }


        // se inician las variables desde el punto de inicio, para continual validando

        // se inician las variables desde el punto de inicio, para continual validando
        hacerBT(posicionInicial, filaInicial, columnaInicial)
        lexema = ""
        filaInicial = filaActual
        columnaInicial = columnaActual
        posicionInicial = posicionActual

        // Verifica pal reservada 'yes' <--> 'true' (en Kotlin)
        if (caracterActual == 'y') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'e') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 's') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()

                    if (caracterActual != '#' && !caracterActual.isDigit() && !caracterActual.isLetter()) {
                        almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                        return true
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                }
            }
        }


        // se inician las variables desde el punto de inicio, para continual validando
        hacerBT(posicionInicial, filaInicial, columnaInicial)
        lexema = ""
        filaInicial = filaActual
        columnaInicial = columnaActual
        posicionInicial = posicionActual

        // Verifica pal reservada 'mtd' <--> 'fun' (en Kotlin)
        if (caracterActual == 'm') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 't') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'd') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()

                    if (caracterActual != '#' && !caracterActual.isDigit() && !caracterActual.isLetter()) {
                        almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                        return true
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                }
            }
        }


        // se inician las variables desde el punto de inicio, para continual validando
        hacerBT(posicionInicial, filaInicial, columnaInicial)
        lexema = ""
        filaInicial = filaActual
        columnaInicial = columnaActual
        posicionInicial = posicionActual


        // Verifica pal reservada 'dec' <--> 'decimal'
        if (caracterActual == 'd') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'e') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'c') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()

                    if (caracterActual != '#' && !caracterActual.isDigit() && !caracterActual.isLetter()) {
                        almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                        return true
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                }
            }
        }
        // se inician las variables desde el punto de inicio, para continual validando
        hacerBT(posicionInicial, filaInicial, columnaInicial)
        lexema = ""
        filaInicial = filaActual
        columnaInicial = columnaActual
        posicionInicial = posicionActual

        // Verifica pal reservada 'atm' <--> 'char'
        if (caracterActual == 'a') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 't') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'm') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()


                    if (caracterActual != '#' && !caracterActual.isDigit() && !caracterActual.isLetter()) {
                        almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                        return true
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                }
            }
        }


        // se inician las variables desde el punto de inicio, para continual validando
        hacerBT(posicionInicial, filaInicial, columnaInicial)
        lexema = ""
        filaInicial = filaActual
        columnaInicial = columnaActual
        posicionInicial = posicionActual

        // Verifica pal reservada 'centi' <--> 'boolean'
        if (caracterActual == 'c') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'e') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'n') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'e') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()


                        if (caracterActual != '#' && !caracterActual.isDigit() && !caracterActual.isLetter()) {
                            almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                            return true
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    }

                }
            }
        }
        // se inician las variables desde el punto de inicio, para continual validando
        hacerBT(posicionInicial, filaInicial, columnaInicial)

        lexema = ""
        filaInicial = filaActual
        columnaInicial = columnaActual
        posicionInicial = posicionActual

        // Verifica pal reservada 'lista' <--> 'list'
        if (caracterActual == 'L') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'i') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 's') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 't') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 'a') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()


                            if (caracterActual != '#' && !caracterActual.isDigit() && !caracterActual.isLetter()) {
                                almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                                return true
                            } else {
                                hacerBT(posicionInicial, filaInicial, columnaInicial)
                                return false
                            }
                        }
                    }

                }
            }
        }
        // se inician las variables desde el punto de inicio, para continual validando
        hacerBT(posicionInicial, filaInicial, columnaInicial)


        lexema = ""
        filaInicial = filaActual
        columnaInicial = columnaActual
        posicionInicial = posicionActual

        // Verifica pal reservada 'check' <--> 'if'
        if (caracterActual == 'c') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'h') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'e') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'c') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 'k') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()


                            if (caracterActual != '#' && !caracterActual.isDigit() && !caracterActual.isLetter()) {
                                almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                                return true
                            } else {
                                hacerBT(posicionInicial, filaInicial, columnaInicial)
                                return false
                            }
                        }
                    }

                }
            }
        }



        // se inician las variables desde el punto de inicio, para continual validando
        hacerBT(posicionInicial, filaInicial, columnaInicial)


        lexema = ""
        filaInicial = filaActual
        columnaInicial = columnaActual
        posicionInicial = posicionActual

        // Verifica pal reservada 'until' <--> 'hasta'
        if (caracterActual == 'u') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'n') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 't') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'i') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 'l') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()


                            if (caracterActual != '#' && !caracterActual.isDigit() && !caracterActual.isLetter()) {
                                almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                                return true
                            } else {
                                hacerBT(posicionInicial, filaInicial, columnaInicial)
                                return false
                            }
                        }
                    }

                }
            }
        }

        // se inician las variables desde el punto de inicio, para continual validando
        hacerBT(posicionInicial, filaInicial, columnaInicial)


        lexema = ""
        filaInicial = filaActual
        columnaInicial = columnaActual
        posicionInicial = posicionActual

        // Verifica pal reservada 'other' <--> 'else'
        if (caracterActual == 'o') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 't') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'h') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'e') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 'r') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()


                            if (caracterActual != '#' && !caracterActual.isDigit() && !caracterActual.isLetter()) {
                                almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                                return true
                            } else {
                                hacerBT(posicionInicial, filaInicial, columnaInicial)
                                return false
                            }
                        }
                    }

                }
            }
        }
        // se inician las variables desde el punto de inicio, para continual validando
        hacerBT(posicionInicial, filaInicial, columnaInicial)

        lexema = ""
        filaInicial = filaActual
        columnaInicial = columnaActual
        posicionInicial = posicionActual

        // Verifica pal reservada 'insi' <--> 'else'
        if (caracterActual == 'i') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'n') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 's') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'i') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()


                        if (caracterActual != '#' && !caracterActual.isDigit() && !caracterActual.isLetter()) {
                            almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                            return true
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    }

                }
            }
        }
        // se inician las variables desde el punto de inicio, para continual validando
        hacerBT(posicionInicial, filaInicial, columnaInicial)


        lexema = ""
        filaInicial = filaActual
        columnaInicial = columnaActual
        posicionInicial = posicionActual

        // Verifica pal reservada 'msg' (message) <--> 'print'
        if (caracterActual == 'm') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 's') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'g') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()


                    if (caracterActual != '#' && !caracterActual.isDigit() && !caracterActual.isLetter()) {
                        almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                        return true
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                }
            }
        }
        // se inician las variables desde el punto de inicio, para continual validando
        hacerBT(posicionInicial, filaInicial, columnaInicial)



        lexema = ""
        filaInicial = filaActual
        columnaInicial = columnaActual
        posicionInicial = posicionActual

        // Verifica pal reservada 'dev' <--> 'return'
        if (caracterActual == 'd') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'e') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'v') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()


                    if (caracterActual != '#' && !caracterActual.isDigit() && !caracterActual.isLetter()) {
                        almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                        return true
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                }
            }
        }
        // se inician las variables desde el punto de inicio, para continual validando
        hacerBT(posicionInicial, filaInicial, columnaInicial)


        lexema = ""
        filaInicial = filaActual
        columnaInicial = columnaActual
        posicionInicial = posicionActual

        // Verifica pal reservada 'make' <--> 'do'
        if (caracterActual == 'm') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'a') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'k') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'e') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()


                        if (caracterActual != '#' && !caracterActual.isDigit() && !caracterActual.isLetter()) {
                            almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                            return true
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    }

                }
            }
        }
        // se inician las variables desde el punto de inicio, para continual validando
        hacerBT(posicionInicial, filaInicial, columnaInicial)

        lexema = ""
        filaInicial = filaActual
        columnaInicial = columnaActual
        posicionInicial = posicionActual

        // Verifica pal reservada 'rup' <--> 'break'
        if (caracterActual == 'r') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'u') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'p') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()


                    if (caracterActual != '#' && !caracterActual.isDigit() && !caracterActual.isLetter()) {
                        almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                        return true
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                }
            }
        }
        // se inician las variables desde el punto de inicio, para continual validando
        hacerBT(posicionInicial, filaInicial, columnaInicial)

        lexema = ""
        filaInicial = filaActual
        columnaInicial = columnaActual
        posicionInicial = posicionActual

        // Verifica pal reservada 'opt' <--> 'case'
        if (caracterActual == 'o') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'p') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 't') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()


                    if (caracterActual != '#' && !caracterActual.isDigit() && !caracterActual.isLetter()) {
                        almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                        return true
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                }
            }
        }
        // se inician las variables desde el punto de inicio, para continual validando
        hacerBT(posicionInicial, filaInicial, columnaInicial)

        lexema = ""
        filaInicial = filaActual
        columnaInicial = columnaActual
        posicionInicial = posicionActual

        // Verifica pal reservada 'enumtn' <--> 'enumeration'
        if (caracterActual == 'e') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'n') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'u') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'm') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 't') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            if (caracterActual == 'n') {
                                lexema += caracterActual
                                obtenerSiguienteCaracter()


                                if (caracterActual != '#' && !caracterActual.isDigit() && !caracterActual.isLetter()) {
                                    almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                                    return true
                                } else {
                                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                                    return false
                                }
                            }
                        }
                    }
                }
            }
        }
        // se inician las variables desde el punto de inicio, para continual validando
        hacerBT(posicionInicial, filaInicial, columnaInicial)

        lexema = ""
        filaInicial = filaActual
        columnaInicial = columnaActual
        posicionInicial = posicionActual

        // Verifica pal reservada 'treat' <--> 'try'
        if (caracterActual == 't') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'r') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'e') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'a') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 't') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()


                            if (caracterActual != '#' && !caracterActual.isDigit() && !caracterActual.isLetter()) {
                                almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                                return true
                            } else {
                                hacerBT(posicionInicial, filaInicial, columnaInicial)
                                return false
                            }

                        }
                    }
                }
            }
        }
        // se inician las variables desde el punto de inicio, para continual validando
        hacerBT(posicionInicial, filaInicial, columnaInicial)

        lexema = ""
        filaInicial = filaActual
        columnaInicial = columnaActual
        posicionInicial = posicionActual

        // Verifica pal reservada 'cap' <--> 'catch'
        if (caracterActual == 'c') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'a') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'p') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()


                    if (caracterActual != '#' && !caracterActual.isDigit() && !caracterActual.isLetter()) {
                        almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                        return true
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                }
            }
        }
        // se inician las variables desde el punto de inicio, para continual validando
        hacerBT(posicionInicial, filaInicial, columnaInicial)

        lexema = ""
        filaInicial = filaActual
        columnaInicial = columnaActual
        posicionInicial = posicionActual

        // Verifica pal reservada 'impt' <--> 'import'
        if (caracterActual == 'i') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'm') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'p') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 't') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()


                        if (caracterActual != '#' && !caracterActual.isDigit() && !caracterActual.isLetter()) {
                            almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                            return true
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    }
                }
            }
        }
        // se inician las variables desde el punto de inicio, para continual validando
        hacerBT(posicionInicial, filaInicial, columnaInicial)

        lexema = ""
        filaInicial = filaActual
        columnaInicial = columnaActual
        posicionInicial = posicionActual

        // Verifica pal reservada 'metd' <--> definici�n de un m�todo/funci�n
        if (caracterActual == 'm') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'e') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 't') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'd') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()


                        if (caracterActual != '#' && !caracterActual.isDigit() && !caracterActual.isLetter()) {
                            almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                            return true
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    }
                }
            }
        }
        // dado que no se hall� palabras reservadas, se reinician las variables globales, para validar otra categor�a; y se retorna false
        hacerBT(posicionInicial, filaInicial, columnaInicial)

        return false
    }


    /**
     * M�todo que consulta, si es posible, el caracter siguiente en la cadena principal
     */
    fun obtenerSiguienteCaracter() {
        // verifica si se trata del �ltimo caracter

        if (posicionActual == codigoFuente.length - 1) {
            caracterActual = finCodigo
        } else {
            // verifica si se trata de un salto de l�nea
            if (caracterActual == '&') {
                if (codigoFuente[posicionActual + 1] == 'S') {
                    posicionActual = posicionActual + 1
                    filaActual++
                    columnaActual = 0
                    listaTokens.removeAt((listaTokens.size - 1))
                    //print("------------------------------ENTROOOOOOOO----------------------")
                }

            } else {
                columnaActual++
            }
            posicionActual++
            caracterActual = codigoFuente[posicionActual]
        }
    }

//mauricio
}