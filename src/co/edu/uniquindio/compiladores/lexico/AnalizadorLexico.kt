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

}