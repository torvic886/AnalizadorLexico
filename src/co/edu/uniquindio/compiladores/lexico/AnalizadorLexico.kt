package co.edu.uniquindio.compiladores.lexico

/**
 *
 */
class AnalizadorLexico ( var codigoFuente:String) {

    var caracterActual = codigoFuente[0]  //se captura el primer caracter de la cadena que entra por parámetro
    var listaTokens = ArrayList<Token>()  //se declara una lista para almacenar los tokens validados
    var posicionActual = 0  // se declara una variable que va a permitir conocer la posición actual en un momento determinado
    var finCodigo = 0.toChar()
    var filaActual = 0
    var columnaActual = 0

    /**
     * Método que permite volver a un punto determindado, para decartar una posibilidad, y volver a intentar desde
     * ese mismo punto para validar otra categoría
     * @param: posicionInicial (posición a la cual se desea volver), filaInicial (fila la cual se desea volver), columnaInicial (col a la cual se desea volver)
     */
    fun hacerBT(posicionInicial:Int, filaInicial:Int, columnaInicial:Int){
        filaActual = filaInicial
        columnaActual = columnaInicial
        posicionActual = posicionInicial
        caracterActual = codigoFuente[posicionActual]
    }

    /**
     * Método que permite almacenar un token previamente validado por alguna categoría, en la lista de los tokens validados
     * @param: lexema (token que se desea almacenar), categoria (categoría de ese token), fila (fila inicial), columna (col inicial)
     */
    fun almacenarToken(lexema:String, categoria: Categoria, fila:Int, columna:Int ) = listaTokens.add(Token(lexema, categoria, fila, columna))

    /**
     * Método que permite validar los token por categorías, una por una hasta hallar la indicada
     */
    fun analizar(){
        while ( caracterActual != finCodigo ){
            if ( caracterActual == ' ' ){
                obtenerSiguienteCaracter()
            }

            if ( esEntero() ) continue
            if ( esDecimal() ) continue
            if ( esIdentificador() ) continue
            if (esOperadorLogico()) continue
            if (esCadenaCaracteres()) continue
            if (esCaracter()) continue
            if (esOperadorRelacional()) continue
            if (esOperadorAsignacion()) continue
            if (esOperadorAditivo()) continue
            if (esOperadorMultiplicativo()) continue
            if (esComentarioBloque()) continue
            if (esComentarioLinea()) continue
            if (esComentarioLinea()) continue
            almacenarToken( lexema = ""+caracterActual, categoria = Categoria.NO_RECONOCIDO, fila = filaActual, columna = columnaActual )
            obtenerSiguienteCaracter()
        }

    }

    /**
     * Método que valida un número entero en el lenguaje Helix
     */
    fun esEntero():Boolean{
        //En Helix, los decimales se definen con un '$' al principio
        if ( caracterActual == '$'){
            var lexema = ""
            var filaInicial = filaActual  // se guarda la fila desde la cual inicia
            var columnaInicial = columnaActual  // se guarda la columna desde la cual inició
            var posicionInicial = posicionActual // se guarda la posición en la cual inició
            lexema += caracterActual  // se almacena el caracter que satisface los requisitos
            obtenerSiguienteCaracter()
            //verifica si el siguiente caracter es un dígito
            if (caracterActual.isDigit()){

                // verifica si sigue un dígito o una secuencia de dígitos
                while ( caracterActual.isDigit() ){
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }
                // en caso de que el caracter siguiente sea un '.', se debe deshacer el proceso, puesto que probablemente se trate de un decimal
                if ( caracterActual == ',')
                {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)  // se busca descartar la posibilidad de que sea un entero, para ello se deben partir desde el punto inicial, para validar otra cat
                    return false
                }
                almacenarToken( lexema, Categoria.ENTERO, filaInicial, columnaInicial )
                return true
            }
        }
        //Si el primer caracter no es un '$', entonces no es un entero
        return false
    }

    /**
     * Método que valida un número decimal en Helix
     */
    fun esDecimal():Boolean{
        //En Helix, los decimales se definen con un '$' al principio
        if ( caracterActual == '$'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual.isDigit()){

                while ( caracterActual.isDigit() ){
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }
                //En Helix, los decimales en vez de puntos llevan una ','
                if ( caracterActual == ','){
                    lexema += caracterActual
                    obtenerSiguienteCaracter()

                    //Verifica si después de la coma sigue un dígito
                    if( caracterActual.isDigit() ) {
                        while ( caracterActual.isDigit() ){
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                        }
                    } else{
                        //Si lo que sigue no es un dígito, por defecto agregará un 0, esto es, para que en vez de '12,' quede '12,0'
                        lexema += '0'
                    }

                } else{
                    hacerBT( posicionInicial, filaInicial, columnaInicial )
                    return false
                }
                almacenarToken( lexema, Categoria.DECIMAL, filaInicial, columnaInicial )
                return true
            }
        }
        //Si el primer caracter no es un '$', entonces no es un decimal
        return false
    }

    /**
     * Método que valida un identificador en Helix
     */
    fun esIdentificador():Boolean{

        if (caracterActual.isLetter() || caracterActual == '#' ){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            while ( caracterActual.isLetter() || caracterActual == '#' || caracterActual.isDigit() ){
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }
            almacenarToken( lexema, Categoria.IDENTIFICADOR, filaInicial, columnaInicial )
            return true
        }
        return false
    }

    /**
     * Método que consulta, si es posible, el caracter siguiente en la cadena principal
     */
    fun obtenerSiguienteCaracter(){
        // verifica si se trata del último caracter
        if ( posicionActual == codigoFuente.length-1 ){
            caracterActual = finCodigo
        } else{
            // verifica si se trata de un salto de línea
            if ( caracterActual == '?' || codigoFuente[posicionActual+1] == 'S'){
                filaActual++
                columnaActual = 0
            } else{
                columnaActual++
            }
            posicionActual++
            caracterActual = codigoFuente[posicionActual]
        }


    }

    /**
     * Método que valida una cadena de caracteres en Helix
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
                                almacenarToken(lexema, Categoria.ERROR, filaInicial, columnaInicial)
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
     * Método que valida un caractere en Helix
     */
    fun esCaracter(): Boolean {
        if (caracterActual == '.') {

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

            if (caracterActual == '.') {
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
     * Método que valida un operador relacional en Helix
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
     * Método que valida un operador de asignación en Helix
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
     * Método que valida un operador aditivo en Helix
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
                    almacenarToken(lexema, Categoria.OPERADOR_ADITIVO, filaInicial, columnaInicial)
                    return true
                }
            }

            hacerBT(posicionInicial, filaInicial, columnaInicial)
        }
        return false
    }

    /**
     * Método que valida un operador multiplicativo en Helix
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
                    almacenarToken(lexema, Categoria.OPERADOR_MULTIPLICATIVO, filaInicial, columnaInicial)
                    return true
                }
            }

            hacerBT(posicionInicial, filaInicial, columnaInicial)
        }
        return false
    }
    /** Método que valida operador incremento en Helix
     *
     */
    fun esOperadorIncremento():Boolean{
        if ( caracterActual == '+'){
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
    /** Método que valida operador decremento en Helix
     *
     */
    fun esOperadorDecremento():Boolean{
        if ( caracterActual == '-'){
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
    /**
     * Método que valida un operador lógico en Helix
     */
    fun esOperadorLogico(): Boolean {
        if ( caracterActual == 'A' ) {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if ( caracterActual == 'N' ) {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if ( caracterActual == 'D' ) {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.OPERADOR_LOGICO, filaInicial, columnaInicial)
                    return true
                }
            }
            hacerBT(posicionInicial, filaInicial, columnaInicial)
            return false

        }
        if ( caracterActual == 'O' ) {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if ( caracterActual == 'R' ) {
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
     * Método que valida un comentario bloque en Helix
     */
    fun esComentarioBloque(): Boolean {
        if (caracterActual == '~') {

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            while ( caracterActual != finCodigo ) {

                if ( caracterActual == '~'){
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.COMENRARIO_BLOQUE, filaInicial, columnaInicial)
                    return true
                }
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }
            almacenarToken(lexema, Categoria.ERROR, filaInicial, columnaInicial)
            return false

        }
        return false
    }


    /**
     * Método que valida un comentario línea en Helix
     */
    fun esComentarioLinea(): Boolean {
        if (caracterActual == '¬') {

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            while ( caracterActual != finCodigo && filaInicial == filaActual) {

                lexema += caracterActual
                obtenerSiguienteCaracter()
            }
            if ( caracterActual != finCodigo ){
                lexema = lexema.substring(0, lexema.length-1)
            }
            almacenarToken(lexema, Categoria.COMENTARIO_LINEA, filaInicial, columnaInicial)
            return true
        }
        return false
    }


}