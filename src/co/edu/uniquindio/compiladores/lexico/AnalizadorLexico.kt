package co.edu.uniquindio.compiladores.lexico

//  ANALIZADOR LEXICO
//  Clase AnalizadorLexico
//  Codigo del analizador lexico, su funcion, aceptacion y almacenamiento
//  Aqui entra por parametro el codigo fuente como variable String
//  Hecho por Juan David Moreno, Miguel Angel Medina y Jorge Enrique Giraldo

class AnalizadorLexico(var codigoFuente: String) {

	var posicionActual = 0   // Variable de la posicion actual del caracter
	var caracterActual = codigoFuente[0] // Variable del caracter actual del codigo de fuente
	var listaTokens = ArrayList<Token>() // ArrayList de los tokens 
	var finCodigo = 0.toChar() // Variable del fin de codigo
	var filaActual = 0 // Variable de la fila actual del caracter
	var columnaActual = 0 // Variable de la columna actual del caracter
	var listaErrores = ArrayList<Error>()


	// Fuuncion del almacenamiento del lexema, la categor la fila actual y la columna actual
	// almacena en la lista de los tokens
	fun almacenarToken(lexema: String, categoria: Categoria, fila: Int, columna: Int) =
		listaTokens.add(Token(lexema, categoria, fila, columna))

	// Funcion para realizar Backtracking
	fun hacerBT(posicionInicial: Int, filaInicial: Int, columnaInicial: Int) {
		posicionActual = posicionInicial
		filaActual = filaInicial
		columnaActual = columnaInicial
		caracterActual = codigoFuente[posicionActual]
	}

	// Funcion de reportar errores por mensaje mandando fila y columna
	fun reportarErrorLexico(mensaje: String, fila: Int, columna: Int) {

		listaErrores.add(Error(mensaje, fila, columna))
	}

	// Funcion para analizar todo el codigo de fuente
	// realiza diferentes llamados de metodos en orden de acuerdo a lo que se expone
	// en el codigo de fuente, tambien aqui saca los carcateres NO RECONOCIDOS
	fun analizar() {

		while (caracterActual != finCodigo) {
			if (caracterActual == ' ' || caracterActual == '\t' || caracterActual == '\n') {
				obtenerSiguienteCaracter()
				continue
			}
			if (esEntero()) continue
			if (esDecimal()) continue
			if (esIdentificador()) continue
			if (esIdentificadorDeClase()) continue
			if (esIdentificadorDeMetodo()) continue
			if (esIdentificadorDeConstantes()) continue
			if (esPalabraReservadaSi()) continue
			if (esPalabraReservadaNo()) continue
			if (esPalabraReservadaMientras()) continue
			if (esPalabraReservadaLapso()) continue
			if (esPalabraReservadaRetorno()) continue
			if (esPalabraReservadaImprime()) continue
			if (esPalabraReservadaVerdad()) continue
			if (esPalabraReservadaFalso()) continue
			if (esPalabraReservadaLeer()) continue
			if (esPalabraReservadaLista()) continue
			if (esPalabraReservadaNulo()) continue
			if (esPalabraReservadaVacio()) continue
			if (esInt()) continue
			if (esDouble()) continue
			if (esChar()) continue
			if (esString()) continue
			if (esBoolean()) continue
			if (esOperadorAritmeticoSuma()) continue
			if (esOperadorAritmeticoResta()) continue
			if (esOperadorAritmeticoMultiplicacion()) continue
			if (esOperadorAritmeticoDivision()) continue
			if (esOperadorAsignacion()) continue
			if (esOperadorIncremento()) continue
			if (esOperadorDecremento()) continue
			if (esOperadorRelacionalMenorQue()) continue
			if (esOperadorRelacionalMayorQue()) continue
			if (esOperadorRelacionalMenorOIgualQue()) continue
			if (esOperadorRelacionalMayorOIgualQue()) continue
			if (esOperadorRelacionalIgual()) continue
			if (esOperadorRelacionalDiferente()) continue
			if (esOperadorRelacionalIgualIgual()) continue
			if (esOperadorLogicoY()) continue
			if (esOperadorLogicoO()) continue
			if (esOperadorLogicoNegacion()) continue
			if (esLlaveDerecho()) continue
			if (esLlaveIzquierdo()) continue
			if (esParentesisDerecho()) continue
			if (esParentesisIzquierdo()) continue
			if (esCorcheteDerecho()) continue
			if (esCorcheteIzquierdo()) continue
			if (esFinDeSentencia()) continue
			if (esSeparador()) continue
			if (esPunto()) continue
			if (esDosPuntos()) continue
			if (esComentarioDeLinea()) continue
			if (esComentarioDeBloque()) continue
			if (esCadenaDeCaracter()) continue
			if (esCaracter()) continue


			almacenarToken("" + caracterActual, Categoria.NO_RECONOCIDO, filaActual, columnaActual)
			obtenerSiguienteCaracter()
		}
	}

	// Funcion para detectar un numero entero
	fun esEntero(): Boolean {

		if (caracterActual.isDigit()) {
			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			val posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()

			if (caracterActual.isLetter()) {
				reportarErrorLexico("No es un entero", filaActual, columnaActual)
			}

			while (caracterActual.isDigit()) {
				if (caracterActual.isDigit()) {
					lexema += caracterActual
					obtenerSiguienteCaracter()
				}
				if (caracterActual.isLetter()) {
					reportarErrorLexico("No es un entero", filaActual, columnaActual)
				}
			}

			if (caracterActual == '.') {
				hacerBT(posicionInicial, filaInicial, columnaInicial)
				return false
			}

			almacenarToken(lexema, Categoria.ENTERO, filaInicial, columnaInicial)
			return true
		}
		return false
	}

	// Funcion para detectar un numero decimal
	fun esDecimal(): Boolean {
		if (caracterActual == '.' || caracterActual.isDigit()) {
			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			if (caracterActual == '.') {
				lexema += caracterActual
				obtenerSiguienteCaracter()
				if (caracterActual.isDigit()) {
					lexema += caracterActual
					obtenerSiguienteCaracter()
					while (caracterActual.isDigit()) {
						if (caracterActual.isDigit()) {
							lexema += caracterActual
							obtenerSiguienteCaracter()
						}
						if (caracterActual.isLetter()) {
							reportarErrorLexico("No es un Decimal", filaActual, columnaActual)
						}
					}
					almacenarToken(lexema, Categoria.DECIMAL, filaInicial, columnaInicial)
					return true
				}
				if (caracterActual.isLetter()) {
					reportarErrorLexico("No es un Decimal", filaActual, columnaActual)
				}
			} else {
				lexema += caracterActual
				obtenerSiguienteCaracter()
				while (caracterActual.isDigit()) {
					if (caracterActual.isDigit()) {
						lexema += caracterActual
						obtenerSiguienteCaracter()
					}
					if (caracterActual.isLetter()) {
						reportarErrorLexico("No es un Decimal", filaActual, columnaActual)
					}
				}
				if (caracterActual == '.') {
					lexema += caracterActual
					obtenerSiguienteCaracter()
					while (caracterActual.isDigit()) {
						if (caracterActual.isDigit()) {
							lexema += caracterActual
							obtenerSiguienteCaracter()
						}
						if (caracterActual.isLetter()) {
							reportarErrorLexico("No es un Decimal", filaActual, columnaActual)
						}
					}
					almacenarToken(lexema, Categoria.DECIMAL, filaInicial, columnaInicial)
					return true
				}
			}
		}
		return false
	}

	// Funcion para detectar un identificador
	fun esIdentificador(): Boolean {
		var lexema = ""
		val filaInicial = filaActual
		val columnaInicial = columnaActual
		var posicionInicial = posicionActual
		if (caracterActual.isLetter()) {
			lexema += caracterActual
			obtenerSiguienteCaracter()

			while (caracterActual.isDigit() || caracterActual.isLetter()) {
				lexema += caracterActual
				obtenerSiguienteCaracter()
			}

			almacenarToken(lexema, Categoria.IDENTIFICADOR, filaInicial, columnaInicial)
			return true
		}
		return false
	}

	// Funcion para detectar un identificador de clase
	fun esIdentificadorDeClase(): Boolean {
		if (caracterActual == '_') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			val posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()

			if (caracterActual == 'C') {
				lexema += caracterActual
				obtenerSiguienteCaracter()

				if (!(caracterActual.isDigit() || caracterActual.isLetter())) {
					reportarErrorLexico("No es un Identificador de clase", filaActual, columnaActual)
				}

				while (caracterActual.isDigit() || caracterActual.isLetter()) {
					lexema += caracterActual
					obtenerSiguienteCaracter()
				}
				almacenarToken(lexema, Categoria.IDENTIFICADOR_DE_CLASE, filaInicial, columnaInicial)
				return true
			} else {
				hacerBT(posicionInicial, filaInicial, columnaInicial)
				return false
			}
		}
		return false
	}

	// Funcion para detectar un identificador de metodo
	fun esIdentificadorDeMetodo(): Boolean {
		if (caracterActual == '_') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			val posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()

			if (caracterActual == 'M') {
				lexema += caracterActual
				obtenerSiguienteCaracter()


				if (!(caracterActual.isDigit() || caracterActual.isLetter())) {
					reportarErrorLexico("No es un Identificador de metodo", filaActual, columnaActual)
				}

				while (caracterActual.isDigit() || caracterActual.isLetter()) {
					lexema += caracterActual
					obtenerSiguienteCaracter()

				}
				almacenarToken(lexema, Categoria.IDENTIFICADOR_DE_METODO, filaInicial, columnaInicial)
				return true
			} else {
				hacerBT(posicionInicial, filaInicial, columnaInicial)
				return false
			}
		}
		return false
	}

	// Funcion para detectar un identificador de constantes
	fun esIdentificadorDeConstantes(): Boolean {
		var lexema = ""
		val filaInicial = filaActual
		val columnaInicial = columnaActual
		val posicionInicial = posicionActual
		if (caracterActual == '_') {
			lexema += caracterActual
			obtenerSiguienteCaracter()

			if (caracterActual == 'L') {
				lexema += caracterActual
				obtenerSiguienteCaracter()

				if (!(caracterActual.isDigit() || caracterActual.isLetter())) {
					reportarErrorLexico("No es un Identificador de constantes", filaActual, columnaActual)
				}

				while (caracterActual.isDigit() || caracterActual.isLetter()) {
					lexema += caracterActual
					obtenerSiguienteCaracter()

				}
				almacenarToken(lexema, Categoria.IDENTIFICADOR_DE_CONSTANTES, filaInicial, columnaInicial)
				return true
			} else {
				hacerBT(posicionInicial, filaInicial, columnaInicial)
				return false
			}
		}
		return false
	}

	//PALABRAS RESERVADAS
	// Funcion para detectar la palabra reservada MIENTRAS
	fun esPalabraReservadaMientras(): Boolean {
		if (caracterActual == '$') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			val posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()

			if (caracterActual != 'M') {
				hacerBT(posicionInicial, filaInicial, columnaInicial)
				return false
			}

			val palabraSuma = "MIENTRAS"
			for (i in palabraSuma) {
				if (caracterActual == i) {
					lexema += caracterActual
					obtenerSiguienteCaracter()
				} else {
					reportarErrorLexico("No es una palabra reservada", filaActual, columnaActual)
				}
			}

			almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
			return true
		}
		return false
	}

	// Funcion para detectar la palabra reservada SI
	fun esPalabraReservadaSi(): Boolean {
		if (caracterActual == '$') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			val posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()

			val palabraSuma = "SI"
			for (i in palabraSuma) {
				if (caracterActual == i) {
					lexema += caracterActual
					obtenerSiguienteCaracter()
				} else {
					hacerBT(posicionInicial, filaInicial, columnaInicial)
					return false
				}
			}

			if (caracterActual == 'I') {
				reportarErrorLexico("No es una palabra reservada", filaActual, columnaActual)
			}

			almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
			return true
		}
		return false
	}

	// Funcion para detectar la palabra reservada NO
	fun esPalabraReservadaNo(): Boolean {
		if (caracterActual == '$') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			val posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()

			val palabraSuma = "NO"
			for (i in palabraSuma) {
				if (caracterActual == i) {
					lexema += caracterActual
					obtenerSiguienteCaracter()
				} else {
					hacerBT(posicionInicial, filaInicial, columnaInicial)
					return false
				}
			}

			almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
			return true
		}
		return false
	}

	// Funcion para detectar la palabra reservada LAPSO
	fun esPalabraReservadaLapso(): Boolean {
		if (caracterActual == '$') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			val posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()

			val palabraSuma = "LAPSO"
			for (i in palabraSuma) {
				if (caracterActual == i) {
					lexema += caracterActual
					obtenerSiguienteCaracter()
				} else {
					hacerBT(posicionInicial, filaInicial, columnaInicial)
					return false
				}
			}

			almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
			return true
		}
		return false
	}

	// Funcion para detectar la palabra reservada RETORNO
	fun esPalabraReservadaRetorno(): Boolean {
		if (caracterActual == '$' || columnaActual == 0) {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			val posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()

			if (caracterActual != 'R') {
				hacerBT(posicionInicial, filaInicial, columnaInicial)
				return false
			}

			val palabraSuma = "RETORNO"
			for (i in palabraSuma) {
				if (caracterActual == i) {
					lexema += caracterActual
					obtenerSiguienteCaracter()
				} else {
					reportarErrorLexico("No es una palabra reservada", filaActual, columnaActual)
				}
			}

			almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
			return true
		}
		return false
	}

	// Funcion para detectar la palabra reservada IMPRIME
	fun esPalabraReservadaImprime(): Boolean {
		if (caracterActual == '$' || columnaActual == 0) {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			val posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()

			val palabraSuma = "IMPRIME"
			for (i in palabraSuma) {
				if (caracterActual == i) {
					lexema += caracterActual
					obtenerSiguienteCaracter()
				} else {
					hacerBT(posicionInicial, filaInicial, columnaInicial)
					return false
				}
			}

			almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
			return true
		}
		return false
	}

	// Funcion para detectar la palabra reservada VERDAD
	fun esPalabraReservadaVerdad(): Boolean {
		if (caracterActual == '$') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			val posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()

			val palabraSuma = "VERDAD"
			for (i in palabraSuma) {
				if (caracterActual == i) {
					lexema += caracterActual
					obtenerSiguienteCaracter()
				} else {
					hacerBT(posicionInicial, filaInicial, columnaInicial)
					return false
				}
			}

			almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
			return true
		}
		return false
	}

	// Funcion para detectar la palabra reservada FALSO
	fun esPalabraReservadaFalso(): Boolean {
		if (caracterActual == '$') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			val posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()

			if (caracterActual != 'F') {
				hacerBT(posicionInicial, filaInicial, columnaInicial)
				return false
			}

			val palabraSuma = "FALSO"
			for (i in palabraSuma) {
				if (caracterActual == i) {
					lexema += caracterActual
					obtenerSiguienteCaracter()
				} else {
					reportarErrorLexico("No es una palabra reservada", filaActual, columnaActual)
				}
			}

			almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
			return true
		}
		return false
	}

	// Funcion para detectar la palabra reservada LEER
	private fun esPalabraReservadaLeer(): Boolean {
		if (caracterActual == '$') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			val posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()

			val palabraSuma = "LEER"
			for (i in palabraSuma) {
				if (caracterActual == i) {
					lexema += caracterActual
					obtenerSiguienteCaracter()
				} else {
					hacerBT(posicionInicial, filaInicial, columnaInicial)
					return false
				}
			}

			almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
			return true
		}
		return false
	}

	// Funcion para detectar la palabra reservada LISTA
	fun esPalabraReservadaLista(): Boolean {
		if (caracterActual == '$') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			val posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()

			val palabraSuma = "ARREGLO"
			for (i in palabraSuma) {
				if (caracterActual == i) {
					lexema += caracterActual
					obtenerSiguienteCaracter()
				} else {
					hacerBT(posicionInicial, filaInicial, columnaInicial)
					return false
				}
			}

			almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
			return true
		}
		return false
	}

	// Funcion para detectar la palabra reservada NULO
	fun esPalabraReservadaNulo(): Boolean {
		if (caracterActual == '$') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			val posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()

			val palabraSuma = "NULO"
			for (i in palabraSuma) {
				if (caracterActual == i) {
					lexema += caracterActual
					obtenerSiguienteCaracter()
				} else {
					hacerBT(posicionInicial, filaInicial, columnaInicial)
					return false
				}
			}

			almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
			return true
		}
		return false
	}

	// Funcion para detectar la palabra reservada VACIO
	fun esPalabraReservadaVacio(): Boolean {
		if (caracterActual == '$') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			val posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()


			val palabraSuma = "VACIO"
			for (i in palabraSuma) {
				if (caracterActual == i) {
					lexema += caracterActual
					obtenerSiguienteCaracter()
				} else {
					hacerBT(posicionInicial, filaInicial, columnaInicial)
					return false
				}
			}

			almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
			return true
		}
		return false
	}

	//TIPO DE DATOS
	// Funcion para detectar el tipo de dato DOUBLE
	fun esDouble(): Boolean {
		if (caracterActual == '$') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			val posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()

			if (caracterActual != 'D') {
				hacerBT(posicionInicial, filaInicial, columnaInicial)
				return false
			}

			val palabraSuma = "DOUBLE"
			for (i in palabraSuma) {
				if (caracterActual == i) {
					lexema += caracterActual
					obtenerSiguienteCaracter()
				} else {
					reportarErrorLexico("No es una palabra reservada", filaActual, columnaActual)
				}
			}

			almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
			return true
		}
		return false
	}

	// Funcion para detectar el tipo de dato INT
	fun esInt(): Boolean {
		if (caracterActual == '$') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			val posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()


			val palabraSuma = "INT"
			for (i in palabraSuma) {
				if (caracterActual == i) {
					lexema += caracterActual
					obtenerSiguienteCaracter()
				} else {
					hacerBT(posicionInicial, filaInicial, columnaInicial)
					return false
				}
			}

			almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
			return true
		}
		return false
	}

	// Funcion para detectar el tipo de dato CHAR
	fun esChar(): Boolean {
		if (caracterActual == '$') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			val posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()

			if (caracterActual != 'C') {
				hacerBT(posicionInicial, filaInicial, columnaInicial)
				return false
			}


			val palabraSuma = "CHAR"
			for (i in palabraSuma) {
				if (caracterActual == i) {
					lexema += caracterActual
					obtenerSiguienteCaracter()
				} else {
					reportarErrorLexico("No es una palabra reservada", filaActual, columnaActual)
				}
			}

			almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
			return true
		}
		return false
	}

	// Funcion para detectar el tipo de dato STRING
	fun esString(): Boolean {
		if (caracterActual == '$') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			val posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()


			val palabraSuma = "STRING"
			for (i in palabraSuma) {
				if (caracterActual == i) {
					lexema += caracterActual
					obtenerSiguienteCaracter()
				} else {
					hacerBT(posicionInicial, filaInicial, columnaInicial)
					return false
				}
			}

			almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
			return true
		}
		return false
	}

	// Funcion para detectar el tipo de dato BOOLEAN
	fun esBoolean(): Boolean {
		if (caracterActual == '$') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			val posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()

			if (caracterActual != 'B') {
				hacerBT(posicionInicial, filaInicial, columnaInicial)
				return false
			}

			val palabraSuma = "BOOLEAN"
			for (i in palabraSuma) {
				if (caracterActual == i) {
					lexema += caracterActual
					obtenerSiguienteCaracter()
				} else {
					reportarErrorLexico("No es una palabra reservada", filaActual, columnaActual)
				}
			}

			almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
			return true
		}
		return false
	}

	//OPERADOR ARITMETICO
	// Funcion para detectar un operador aritmetico de SUMA
	fun esOperadorAritmeticoSuma(): Boolean {
		if (caracterActual == '_') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			val posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()

			if (caracterActual != 'p') {
				hacerBT(posicionInicial, filaInicial, columnaInicial)
				return false
			}


			val palabraSuma = "plus"
			for (i in palabraSuma) {
				if (caracterActual == i) {
					lexema += caracterActual
					obtenerSiguienteCaracter()
				} else {
					reportarErrorLexico("No es un operador aritmetico", filaActual, columnaActual)
				}
			}

			if (caracterActual == '_') {
				hacerBT(posicionInicial, filaInicial, columnaInicial)
				return false
			}

			almacenarToken(lexema, Categoria.OPERADOR_ARITMETICO, filaInicial, columnaInicial)
			return true
		}
		return false
	}

	// Funcion para detectar un operador aritmetico de RESTA
	fun esOperadorAritmeticoResta(): Boolean {
		if (caracterActual == '_') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			val posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()


			val palabraSuma = "minus"
			for (i in palabraSuma) {
				if (caracterActual == i) {
					lexema += caracterActual
					obtenerSiguienteCaracter()
				} else {
					hacerBT(posicionInicial, filaInicial, columnaInicial)
					return false
				}
			}

			if (caracterActual == '_') {
				hacerBT(posicionInicial, filaInicial, columnaInicial)
				return false
			}

			almacenarToken(lexema, Categoria.OPERADOR_ARITMETICO, filaInicial, columnaInicial)
			return true
		}
		return false
	}

	// Funcion para detectar un operador aritmetico de MULTIPLICACION
	fun esOperadorAritmeticoMultiplicacion(): Boolean {
		if (caracterActual == '_') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			val posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()


			val palabraSuma = "multi"
			for (i in palabraSuma) {
				if (caracterActual == i) {
					lexema += caracterActual
					obtenerSiguienteCaracter()
				} else {
					hacerBT(posicionInicial, filaInicial, columnaInicial)
					return false
				}
			}

			if (caracterActual == '_') {
				hacerBT(posicionInicial, filaInicial, columnaInicial)
				return false
			}

			almacenarToken(lexema, Categoria.OPERADOR_ARITMETICO, filaInicial, columnaInicial)
			return true
		}
		return false
	}

	// Funcion para detectar un operador aritmetico de DIVISION
	fun esOperadorAritmeticoDivision(): Boolean {
		if (caracterActual == '_') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			val posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()

			if (caracterActual != 'd') {
				hacerBT(posicionInicial, filaInicial, columnaInicial)
				return false
			}


			val palabraSuma = "div"
			for (i in palabraSuma) {
				if (caracterActual == i) {
					lexema += caracterActual
					obtenerSiguienteCaracter()
				} else {
					reportarErrorLexico("No es un operador aritmetico", filaActual, columnaActual)
				}
			}

			if (caracterActual == '_') {
				hacerBT(posicionInicial, filaInicial, columnaInicial)
				return false
			}

			almacenarToken(lexema, Categoria.OPERADOR_ARITMETICO, filaInicial, columnaInicial)
			return true
		}
		return false
	}

	// Funcion para detectar un operador de asignacion
	fun esOperadorAsignacion(): Boolean {
		if (caracterActual == '<') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			var posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()


			if (caracterActual == '-') {
				lexema += caracterActual
				obtenerSiguienteCaracter()
			} else {
				reportarErrorLexico("No es un operador de asignacion", filaActual, columnaActual)
			}

			almacenarToken(lexema, Categoria.OPERADOR_ASIGNACION, filaInicial, columnaInicial)
			return true
		}
		return false
	}

	// Funcion para detectar un operador de incremento
	fun esOperadorIncremento(): Boolean {
		if (caracterActual == '_') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			val posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()

			if (caracterActual != 'p') {
				hacerBT(posicionInicial, filaInicial, columnaInicial)
				return false
			}


			val palabraSuma = "plus"
			for (i in palabraSuma) {
				if (caracterActual == i) {
					lexema += caracterActual
					obtenerSiguienteCaracter()
				} else {
					reportarErrorLexico("No es un operador de incremento", filaActual, columnaActual)
				}
			}

			if (caracterActual == '_') {
				lexema += caracterActual
				obtenerSiguienteCaracter()

				val palabraSuma = "plus"
				for (i in palabraSuma) {
					if (caracterActual == i) {
						lexema += caracterActual
						obtenerSiguienteCaracter()
					} else {
						reportarErrorLexico("No es un operador de incremento", filaActual, columnaActual)
					}
				}
				almacenarToken(lexema, Categoria.OPERADOR_INCREMENTO, filaInicial, columnaInicial)
				return true

			} else {
				hacerBT(posicionInicial, filaInicial, columnaInicial)
				return false
			}
		}
		return false
	}

	// Funcion para detectar un operador de decremento
	fun esOperadorDecremento(): Boolean {
		if (caracterActual == '_') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			val posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()


			val palabraSuma = "minus"
			for (i in palabraSuma) {
				if (caracterActual == i) {
					lexema += caracterActual
					obtenerSiguienteCaracter()
				} else {
					hacerBT(posicionInicial, filaInicial, columnaInicial)
					return false
				}
			}

			if (caracterActual == '_') {
				lexema += caracterActual
				obtenerSiguienteCaracter()

				val palabraSuma = "minus"
				for (i in palabraSuma) {
					if (caracterActual == i) {
						lexema += caracterActual
						obtenerSiguienteCaracter()
					} else {
						reportarErrorLexico("No es un operador de decremento", filaActual, columnaActual)
					}
				}
				almacenarToken(lexema, Categoria.OPERADOR_DECREMENTO, filaInicial, columnaInicial)
				return true

			} else {
				hacerBT(posicionInicial, filaInicial, columnaInicial)
				return false
			}
		}
		return false
	}

	// Funcion para detectar un operador relacional menor que
	fun esOperadorRelacionalMenorQue(): Boolean {
		if (caracterActual == '¡') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			val posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()


			if (caracterActual == '<') {
				lexema += caracterActual
				obtenerSiguienteCaracter()
			} else {
				hacerBT(posicionInicial, filaInicial, columnaInicial)
				return false
			}

			if (caracterActual == '-' || caracterActual == '>') {
				hacerBT(posicionInicial, filaInicial, columnaInicial)
				return false
			}

			almacenarToken(lexema, Categoria.OPERADOR_RELACIONAL, filaInicial, columnaInicial)
			return true
		}
		return false
	}

	// Funcion para detectar un operador relacional mayor que
	fun esOperadorRelacionalMayorQue(): Boolean {
		if (caracterActual == '¡') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			val posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()


			if (caracterActual == '>') {
				lexema += caracterActual
				obtenerSiguienteCaracter()
			} else {
				hacerBT(posicionInicial, filaInicial, columnaInicial)
				return false
			}

			if (caracterActual == '-' || caracterActual == '<') {
				hacerBT(posicionInicial, filaInicial, columnaInicial)
				return false
			}

			almacenarToken(lexema, Categoria.OPERADOR_RELACIONAL, filaInicial, columnaInicial)
			return true
		}
		return false
	}

	// Funcion para detectar un operador relacional menor o igual que
	fun esOperadorRelacionalMenorOIgualQue(): Boolean {
		if (caracterActual == '¡') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			val posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()


			if (caracterActual == '<') {
				lexema += caracterActual
				obtenerSiguienteCaracter()

				if (caracterActual == '-') {
					lexema += caracterActual
					obtenerSiguienteCaracter()

					almacenarToken(lexema, Categoria.OPERADOR_RELACIONAL, filaInicial, columnaInicial)
					return true

				} else {
					hacerBT(posicionInicial, filaInicial, columnaInicial)
					return false
				}
			} else {
				hacerBT(posicionInicial, filaInicial, columnaInicial)
				return false
			}
		}
		return false
	}

	// Funcion para detectar un operador relacional mayor o igual que
	fun esOperadorRelacionalMayorOIgualQue(): Boolean {
		if (caracterActual == '¡') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			val posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()


			if (caracterActual == '>') {
				lexema += caracterActual
				obtenerSiguienteCaracter()

				if (caracterActual == '-') {
					lexema += caracterActual
					obtenerSiguienteCaracter()

					almacenarToken(lexema, Categoria.OPERADOR_RELACIONAL, filaInicial, columnaInicial)
					return true

				} else {
					hacerBT(posicionInicial, filaInicial, columnaInicial)
					return false
				}
			} else {
				hacerBT(posicionInicial, filaInicial, columnaInicial)
				return false
			}
		}
		return false
	}

	// Funcion para detectar un operador relacional igual
	fun esOperadorRelacionalIgual(): Boolean {
		if (caracterActual == '¡') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			val posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()


			if (caracterActual == '>') {
				lexema += caracterActual
				obtenerSiguienteCaracter()

				if (caracterActual == '<') {
					lexema += caracterActual
					obtenerSiguienteCaracter()

					almacenarToken(lexema, Categoria.OPERADOR_RELACIONAL, filaInicial, columnaInicial)
					return true

				} else {
					hacerBT(posicionInicial, filaInicial, columnaInicial)
					return false
				}
			} else {
				hacerBT(posicionInicial, filaInicial, columnaInicial)
				return false
			}
		}
		return false
	}

	// Funcion para detectar un operador relacional diferente
	fun esOperadorRelacionalDiferente(): Boolean {
		if (caracterActual == '¡') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			val posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()


			if (caracterActual == '<') {
				lexema += caracterActual
				obtenerSiguienteCaracter()

				if (caracterActual == '>') {
					lexema += caracterActual
					obtenerSiguienteCaracter()

					almacenarToken(lexema, Categoria.OPERADOR_RELACIONAL, filaInicial, columnaInicial)
					return true

				} else {
					hacerBT(posicionInicial, filaInicial, columnaInicial)
					return false
				}
			} else {
				hacerBT(posicionInicial, filaInicial, columnaInicial)
				return false
			}
		}
		return false
	}

	// Funcion para detectar un operador relacional igual igual
	fun esOperadorRelacionalIgualIgual(): Boolean {
		if (caracterActual == '-') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			var posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()


			if (caracterActual == '>') {
				lexema += caracterActual
				obtenerSiguienteCaracter()

				if (caracterActual == '<') {
					lexema += caracterActual
					obtenerSiguienteCaracter()

					if (caracterActual == '-') {
						lexema += caracterActual
						obtenerSiguienteCaracter()

						almacenarToken(lexema, Categoria.OPERADOR_RELACIONAL, filaInicial, columnaInicial)
						return true

					} else {
						reportarErrorLexico("No es un operador relacional", filaActual, columnaActual)
					}
				} else {
					reportarErrorLexico("No es un operador relacional", filaActual, columnaActual)
				}
			} else {
				reportarErrorLexico("No es un operador relacional", filaActual, columnaActual)
			}
		}
		return false
	}

	// Funcion para detectar un operador logico AND
	fun esOperadorLogicoY(): Boolean {
		if (caracterActual == '$') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			val posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()

			if (caracterActual != 'A') {
				hacerBT(posicionInicial, filaInicial, columnaInicial)
				return false
			}


			val palabraSuma = "AND"
			for (i in palabraSuma) {
				if (caracterActual == i) {
					lexema += caracterActual
					obtenerSiguienteCaracter()
				} else {
					reportarErrorLexico("No es un operador logico", filaActual, columnaActual)
				}
			}

			almacenarToken(lexema, Categoria.OPERADOR_LOGICO, filaInicial, columnaInicial)
			return true
		}
		return false
	}

	// Funcion para detectar un operador logico OR
	fun esOperadorLogicoO(): Boolean {
		if (caracterActual == '$') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			val posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()
			
			if (caracterActual != 'O') {
				hacerBT(posicionInicial, filaInicial, columnaInicial)
				return false
			}


			val palabraSuma = "OR"
			for (i in palabraSuma) {
				if (caracterActual == i) {
					lexema += caracterActual
					obtenerSiguienteCaracter()
				} else {
					reportarErrorLexico("No es un operador logico", filaActual, columnaActual)
				}
			}

			almacenarToken(lexema, Categoria.OPERADOR_LOGICO, filaInicial, columnaInicial)
			return true
		}
		return false
	}

	// Funcion para detectar un operador logico AND
	fun esOperadorLogicoNegacion(): Boolean {
		if (caracterActual == '$') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			val posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()


			val palabraSuma = "NI"
			for (i in palabraSuma) {
				if (caracterActual == i) {
					lexema += caracterActual
					obtenerSiguienteCaracter()
				} else {
					hacerBT(posicionInicial, filaInicial, columnaInicial)
					return false
				}
			}

			almacenarToken(lexema, Categoria.OPERADOR_NEGACION, filaInicial, columnaInicial)
			return true
		}
		return false
	}

	// Funcion para detectar una llave derecha
	fun esLlaveDerecho(): Boolean {
		if (caracterActual == '[') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			var posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()

			almacenarToken(lexema, Categoria.LLAVE_IZQUIERDO, filaInicial, columnaInicial)
			return true
		}
		return false
	}

	// Funcion para detectar una llave izquierda
	fun esLlaveIzquierdo(): Boolean {
		if (caracterActual == ']') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			var posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()

			almacenarToken(lexema, Categoria.LLAVE_DERECHO, filaInicial, columnaInicial)
			return true
		}
		return false
	}

	// Funcion para detectar un parentesis derecho
	fun esParentesisDerecho(): Boolean {
		if (caracterActual == '(') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			var posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()

			almacenarToken(lexema, Categoria.PARENTESIS_IZQUIERDO, filaInicial, columnaInicial)
			return true
		}
		return false
	}

	// Funcion para detectar un parentesis izquierdo
	fun esParentesisIzquierdo(): Boolean {
		if (caracterActual == ')') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			var posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()

			almacenarToken(lexema, Categoria.PARENTESIS_DERECHO, filaInicial, columnaInicial)
			return true
		}
		return false
	}

	// Funcion para detectar un corchete derecho
	fun esCorcheteDerecho(): Boolean {
		if (caracterActual == '{') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			var posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()

			almacenarToken(lexema, Categoria.CORCHETE_IZQUIERDO, filaInicial, columnaInicial)
			return true
		}
		return false
	}

	// Funcion para detectar un corchete izquierdo
	fun esCorcheteIzquierdo(): Boolean {
		if (caracterActual == '}') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			var posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()

			almacenarToken(lexema, Categoria.CORCHETE_DERECHO, filaInicial, columnaInicial)
			return true
		}
		return false
	}

	// Funcion para detectar un fin de sentencia
	fun esFinDeSentencia(): Boolean {
		if (caracterActual == '?') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			var posicionInicial = posicionActual
			lexema += caracterActual
			obtenerSiguienteCaracter()

			//if (caracterActual != '?') {

			//hacerBT(posicionInicial, filaInicial, columnaInicial)
			//return false
			//}

			almacenarToken(lexema, Categoria.FIN_DE_SENTENCIA, filaInicial, columnaInicial)
			return true
		}
		return false
	}

	// Funcion para detectar un separador
	fun esSeparador(): Boolean {
		if (caracterActual == '¬') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			var posicionInicial = posicionActual
			lexema += caracterActual

			almacenarToken(lexema, Categoria.SEPARADOR, filaInicial, columnaInicial)
			obtenerSiguienteCaracter()
			return true
		}
		return false
	}

	// Funcion para detectar un punto
	fun esPunto(): Boolean {
		if (caracterActual == '.') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			var posicionInicial = posicionActual
			lexema += caracterActual

			almacenarToken(lexema, Categoria.PUNTO, filaInicial, columnaInicial)
			obtenerSiguienteCaracter()
			return true
		}
		return false
	}

	// Funcion para detectar dos puntos
	fun esDosPuntos(): Boolean {
		if (caracterActual == ':') {

			var lexema = ""
			val filaInicial = filaActual
			val columnaInicial = columnaActual
			var posicionInicial = posicionActual
			lexema += caracterActual

			almacenarToken(lexema, Categoria.DOS_PUNTOS, filaInicial, columnaInicial)
			obtenerSiguienteCaracter()
			return true
		}
		return false
	}

	// Funcion para detectar un comentario de bloque
	fun esComentarioDeBloque(): Boolean {
		var lexema = ""
		val filaInicial = filaActual
		val columnaInicial = columnaActual
		var posicionInicial = posicionActual
		if (caracterActual == '*') {
			lexema += caracterActual
			obtenerSiguienteCaracter()

			while (caracterActual != '*') {
				lexema += caracterActual
				obtenerSiguienteCaracter()
			}
			if (caracterActual == '*') {
				lexema += caracterActual
				obtenerSiguienteCaracter()

				almacenarToken(lexema, Categoria.COMENTARIO_DE_BLOQUE, filaInicial, columnaInicial)
				return true
			}
		}
		return false
	}

	// Funcion para detectar un comentario de linea
	fun esComentarioDeLinea(): Boolean {
		var lexema = ""
		val filaInicial = filaActual
		val columnaInicial = columnaActual
		var posicionInicial = posicionActual
		if (caracterActual == '#') {
			lexema += caracterActual
			obtenerSiguienteCaracter()

			while (caracterActual != '#') {
				lexema += caracterActual
				obtenerSiguienteCaracter()
			}

			almacenarToken(lexema, Categoria.COMENTARIO_DE_LINEA, filaInicial, columnaInicial)
			return true
		}
		return false
	}

	// Funcion para detectar una cadena de caracter
	fun esCadenaDeCaracter(): Boolean {
		var lexema = ""
		val filaInicial = filaActual
		val columnaInicial = columnaActual
		var posicionInicial = posicionActual
		if (caracterActual == '"') {
			lexema += caracterActual
			obtenerSiguienteCaracter()

			while (caracterActual != '"') {
				lexema += caracterActual
				obtenerSiguienteCaracter()
			}
			if (caracterActual == '"') {
				lexema += caracterActual
				obtenerSiguienteCaracter()

				almacenarToken(lexema, Categoria.CADENA_DE_CARACTER, filaInicial, columnaInicial)
				return true
			}
		}
		return false
	}

	// Funcion para detectar un caracter
	fun esCaracter(): Boolean {
		var lexema = ""
		val filaInicial = filaActual
		val columnaInicial = columnaActual
		var posicionInicial = posicionActual
		if (caracterActual == '~') {
			lexema += caracterActual
			obtenerSiguienteCaracter()

			if (caracterActual != '~') {
				lexema += caracterActual
				obtenerSiguienteCaracter()
				if (caracterActual == '~') {
					lexema += caracterActual
					obtenerSiguienteCaracter()

					almacenarToken(lexema, Categoria.CARACTER, filaInicial, columnaInicial)
					return true
				} else {
					reportarErrorLexico("No es un caracter", filaActual, columnaActual)
					return false
				}
			} else {
				if (caracterActual == '~') {
					lexema += caracterActual
					obtenerSiguienteCaracter()

					almacenarToken(lexema, Categoria.CARACTER, filaInicial, columnaInicial)
					return true
				}
			}
		}
		return false
	}

	// Funcion para obtener el siguiente caracter, esto se debe llamar siempre
	// para poder avanzar en el anazalisis del codigo de fuente
	fun obtenerSiguienteCaracter() {
		if (posicionActual == codigoFuente.length - 1) {
			caracterActual = finCodigo
		} else {
			if (caracterActual == '\n') {
				filaActual++
				columnaActual = 0
			} else {
				columnaActual++
			}

			posicionActual++
			caracterActual = codigoFuente[posicionActual]
		}
	}
}// FIN DE LA CLASE