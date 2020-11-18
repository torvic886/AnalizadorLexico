package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token

class AnalizadorSintactico4(var listaTokens: ArrayList<Token>)
{
    var posicionActual = 0
    var tokenActual = listaTokens[0]
    val listaErrores = ArrayList<Error>()

    /**
     * Método que permite volver a un punto determindado, para descartar una posibilidad, y volver a intentar desde
     * ese mismo punto para validar otra categoría sintáctica
     * @param: posicionInicial (posición a la cual se desea volver)
     */
    fun hacerBT(posicionInicial: Int)
    {
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

    /**
     * Aca ingrese esto < VICTOR>
     */

    /**
     * <ListaSentencias> ::= <Sentencia><ListaSentencias> | <Sentencia>
     */
    fun esBloqueDeSentencia(): ArrayList<Sentencia2>
    {
        val listaSentencias = ArrayList<Sentencia2>()
        var sentencia = esSentencia()

        if (sentencia != null)
        {
            listaSentencias.add(sentencia)
            sentencia = esSentencia()

            while (sentencia != null)
            {
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
    fun esSentencia(): Sentencia2?
    {

        val asignacion = esAsignacion()
        if (asignacion != null)
        {
            return asignacion
        }

        // Verifica que se trate de la invocación de una función
        val invocacionFuncion = esInvocacionDeFuncion()

        if (invocacionFuncion != null)
        {
            return invocacionFuncion
        }


        val chek = esChek()
        if (chek != null)
        {
            return chek
        }



        val whereas = esWhereas()
        if (whereas != null) {
            return whereas
        }

        val declaracionVariableInmutable = esDeclaracionDeVariableInmutable()
        // Verifica si se trata de la declaración de una variable inmutable
        if (declaracionVariableInmutable != null)
        {
            return declaracionVariableInmutable
        }

        val retorno = esRetorno()
        if (retorno != null)
        {
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

    fun esLeer(): SentenciaLeer2?
    {
        return null
    }

    /**
     * <SentenciaDeIncremento> ::= <ExpresiónAritmética> "+" "_"
     */
    fun esIncremento(): SentenciaIncremento2?
    {
        /*val expresionAritmetica = esExpresionAritmetica()
        if (expresionAritmetica != null)
        {
            if (tokenActual.categoria == Categoria.OPERADOR_INCREMENTO)
             {
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.FIN_SENTENCIA)
                 {
                    obtenerSiguienteToken()
                    return SentenciaIncremento(expresionAritmetica)
                }
                 else
                 {
                    reportarError("Es preciso que especifique el final de la sentencia incremento")
                }
            }
        }*/
        return null
    }

    /**
     * <SentenciaDeDecremento> ::= <ExpresiónAritmética> "-" "_"
     */
    fun esDecremento(): SentenciaDecremento2?
    {
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

    fun esImprimir(): SentenciaImprimir2?
    {
        return null
    }

    /**
     * <Condición> ::= ["~"] <Condición> | <ExpresiónRelacional> | <ExpresiónLógica> | yes | not
     */
    fun esCondicion(): Condicion2?
    {
        if (tokenActual.categoria == Categoria.NEGACION && tokenActual.lexema == "no")
        {
            obtenerSiguienteToken()

            if (tokenActual.categoria == Categoria.PALABRA_RESERVADA || tokenActual.lexema == "yes" || tokenActual.lexema == "not")
            {
                val cent = tokenActual
                obtenerSiguienteToken()
                return Condicion2(Negacion2(cent))
            }

            val expRel = esExpresionRelacional()

            if (expRel != null)
            {
                return Condicion2(Negacion2(expRel))
            }

            val expLog = esExpresionLogica()

            if (expLog != null)
            {
                return Condicion2(Negacion2(expLog))
            }
            reportarError("Después de la palabra 'no' debe continuar una condición")
        }
        //no exRe
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && (tokenActual.lexema == "yes" || tokenActual.lexema == "not"))
        {
            val cent = tokenActual
            obtenerSiguienteToken()
            return Condicion2(cent)
        }

        val expRel = esExpresionRelacional()

        if (expRel != null)
        {
            return Condicion2(expRel)
        }

        val expLog = esExpresionLogica()

        if (expLog != null)
        {
            return Condicion2(expLog)
        }
        return null
    }

    /**
     *  <SentenciaChek> ::= chek <Condición> “-“ “-“ “>” [<ListaSentencias>] “<” “-” “-” [other “-“ “-“ “>” [<ListaSentencias>] “<” “-“ “-“]

     */
    fun esChek(): SentenciaChek2?
    {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "check")
        {
            obtenerSiguienteToken()
            print("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ENTRA PAL CHECK!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
            val condicion = esCondicion()
            if (condicion != null)
            {
                print("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ENTRA CONDICION!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
                if (tokenActual.categoria == Categoria.LLAVE_ABRIR)
                {
                    obtenerSiguienteToken()
                    print("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ENTRA LLAVE ABRIR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
                    var bloqueSentencias = esBloqueDeSentencia()

                    if (tokenActual.categoria == Categoria.LLAVE_CERRAR)
                    {
                        obtenerSiguienteToken()
                        print("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ENTRA LLAVE CERRAR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
                        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "other")
                        {
                            obtenerSiguienteToken()

                            if (tokenActual.categoria == Categoria.LLAVE_ABRIR)
                            {
                                obtenerSiguienteToken()

                                val bloqueSentenciasOther = esBloqueDeSentencia()

                                if (tokenActual.categoria == Categoria.LLAVE_CERRAR)
                                {
                                    obtenerSiguienteToken()

                                    // Como todo bien, entonces:
                                    return SentenciaChek2(condicion, bloqueSentencias, Other2(bloqueSentenciasOther))
                                }
                                else
                                {
                                    reportarError("Hace falta llave cerrar del 'other'")
                                }
                            } else
                            {
                                reportarError("Hace falta llave abrir del 'other'")
                            }


                        }
                        print("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!CASI RETORNA EL CHECK!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
                        // Como todo bien, y no es obligatorio el other, entonces:
                        return SentenciaChek2(condicion, bloqueSentencias, null)
                    }
                    else
                    {
                        reportarError("Hace falta llave cerrar en el chek")
                    }

                } else
                {
                    reportarError("Después de una condición debe seguir una llave de abrir")
                }
            } else
            {
                reportarError("Después de la pal 'chek' debe seguir una condición")
            }
        }
        return null
    }

    fun esWhereas(): SentenciaWhereas2?
    {
        return null
    }

    /**
     * <SentenciaDeclaraciónDeVariableInmutable> ::=  <TipoDatoInmutable> “:” <Identificador> "_"
     */
    fun esDeclaracionDeVariableInmutable(): SentenciaDeclaracionVariableInmutable2?
    {
        val tipoDato = esTipoDato()
        if (tipoDato != null)
        {
            if (tipoDato.lexema != "conjunto" && tipoDato.lexema != "lista")
            {
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.DOS_PUNTOS)
                {
                    obtenerSiguienteToken()
                    if (tokenActual.categoria == Categoria.IDENTIFICADOR)
                    {
                        val nombreVar = tokenActual
                        obtenerSiguienteToken()
                        if (tokenActual.categoria == Categoria.FIN_SENTENCIA)
                        {
                            obtenerSiguienteToken()
                            return SentenciaDeclaracionVariableInmutable2(tipoDato, nombreVar)
                        }
                        else
                        {
                            reportarError("Es preciso especificar el '_' al terminar de declarar la var inmutable")
                        }
                    }

                }
                else
                {

                    reportarError("Es preciso especificar ':' después de definir el tipo de dato jajaja")
                }

            }
        }
        return null
    }

    /**
     * <SentenciaDeRetorno> ::= dev <Expresión> "_"
     */
    fun esRetorno(): SentenciaRetorno2?
    {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "dev")
        {
            obtenerSiguienteToken()
            val expresion = esExpresion()

            if (expresion != null)
            {
                if (tokenActual.categoria == Categoria.FIN_SENTENCIA)
                {
                    obtenerSiguienteToken()
                    return SentenciaRetorno2(expresion)
                }
                else
                {
                    reportarError("Es preciso especificar el final de la sentencia")
                }
            }
            else
            {
                reportarError("Después de la palabra 'dev' debe seguir una expresión")
            }
        }
        return null
    }

    /**
     * <SentenciaAsignación> ::= Identificador opAsignación <Expresión> finSentencia
     */
    fun esAsignacion(): SentenciaAsignacion2?
    {
        if (tokenActual.categoria == Categoria.IDENTIFICADOR)
        {
            val nombreVariable = tokenActual
            print("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$44444444444444444444Entra asignacion4444444444444444$$$$$$$$$$$$44444444")
            if (posicionActual < listaTokens.size + 1)
            {
                if (listaTokens[posicionActual + 1].categoria != Categoria.OPERADOR_DE_ASIGNACION)
                {
                    return null
                }
            }
            obtenerSiguienteToken()

            val operadorAsignacion = esOpAsignacion()
            if (operadorAsignacion != null)
            {

                val expresion = esExpresion()
                if (expresion != null)
                {

                    if (tokenActual.categoria == Categoria.FIN_SENTENCIA)
                    {
                        obtenerSiguienteToken()
                        // Como todo va bien hasta acá
                        return SentenciaAsignacion2(nombreVariable, operadorAsignacion, expresion)
                    }
                    else
                    {
                        reportarError("Es preciso especificar el fin de la sentencia al asignar")
                    }
                }
            }
            else
            {
                reportarError("Después del nombre de la variable va un opAsignación")
            }


        }
        return null
    }

    /**
     * <InvocaciónDeFunción> ::= Identificador “[“[<ListaArgumentos>]”]” "_"
     */
    fun esInvocacionDeFuncion(): SentenciaInvocacionFuncion2?
    {
        if (tokenActual.categoria == Categoria.IDENTIFICADOR)
        {
            val nombreFuncion = tokenActual
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.PARENTESIS_ABRIR)
            {
                obtenerSiguienteToken()
                val argumentos: ArrayList<Expresion2> = esListaArgumentos()
                print("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!EXPRESION CORRECTA!!!!!!!!!!!!!!!1111!!!!!!!!!!!!!!!!!!!!!!!!!!!")
                if (tokenActual.categoria == Categoria.PARENTESIS_CERRAR)
                {
                    obtenerSiguienteToken()
                    if (tokenActual.categoria == Categoria.FIN_SENTENCIA)
                    {
                        obtenerSiguienteToken()
                        return SentenciaInvocacionFuncion2(nombreFuncion, argumentos)
                    }
                    else
                    {
                        reportarError("Falta fin de sentencia")
                    }
                }
                else
                {
                    reportarError("Falta paréntesis derecho")
                }
            }
            else
            {
                if (tokenActual.categoria != Categoria.OPERADOR_DE_ASIGNACION)
                {
                    reportarError("Falta paréntesis izquierdo")
                }

            }
        }
        return null

    }

    /**
     * <ListaArgumentos> ::= <Expresión> | <Expresión> ";" <ListaArgumentos>
     */
    fun esListaArgumentos(): ArrayList<Expresion2>
    {
        val listaExpresiones = ArrayList<Expresion2>()
        var expresion = esExpresion()

        if (expresion != null)
        {
            listaExpresiones.add(expresion)

            while (tokenActual.lexema == ";" || tokenActual.categoria == Categoria.SEPARADOR)
            {
                obtenerSiguienteToken()

                expresion = esExpresion()
                if (expresion != null)
                {
                    listaExpresiones.add(expresion)
                }
                else
                {
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
    fun esExpresion(): Expresion2?
    {

        val expresionLogica = esExpresionLogica()
        if (expresionLogica != null)
        {
            //print("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!EN LA PUERTA!!!!!!!!!!!!!!!!!!!!!!!!!111")
            return expresionLogica
        }

        val expresionRelacional = esExpresionRelacional()
        if (expresionRelacional != null)
        {
            return expresionRelacional
        }

        val expresionArit = esExpresionAritmetica()
        if (expresionArit != null)
        {
            return expresionArit
        }
        val expresionCad = esExpresionCadena()
        if (expresionCad != null)
        {
            return expresionCad
        }

        return null
    }

}
