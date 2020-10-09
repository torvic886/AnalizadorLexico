package co.edu.uniquindio.compiladores.lexico

// TOKENS
// Clase de los tokens
// su funcion es mandar el lexema , categoria, fila y columna que se mande por parametro
// para luego ser impreso
class Token (var lexema:String, var categoria:Categoria, var fila:Int, var columna:Int) {
	
	override fun toString(): String {
		return "Token(lexema= $lexema, categoria= $categoria, fila= $fila, columna= $columna)"
	}
	
	fun getJavaCode(): String{
		if(categoria == Categoria.PALABRA_RESERVADA){
			var decimal = "$"+"DOUBLE"
			var entero = "$"+"INT"
			var cadena = "$"+"STRING"
			var boleano = "$"+"BOOLEAN"
			var cad = "$"+"CHAR"
			var vacio = "$"+"VACIO"
			var nulo = "$"+"NULO"
			var arreglo = "$"+"ARREGLO"
			var leer = "$"+"LEER"
			var falso = "$"+"FALSO"
			var verdad = "$"+"VERDAD"
			var imprime = "$"+"IMPRIME"
			var retorno = "$"+"RETORNO"
			var lapso = "$"+"LAPSO"
			var mientras = "$"+"MIENTRAS"
			var no = "$"+"NO"
			var si = "$"+"SI"
			if(lexema == decimal){
				return "double"
			}else if(lexema == entero){
				return "int"
			}else if(lexema == cadena){
				return "String"
			}else if(lexema == boleano){
				return "boolean"
			}else if(lexema == cad){
				return "char"
			}else if(lexema == vacio){
				return "void"
			}else if(lexema == nulo){
				return "null"
			}else if(lexema == arreglo){
				return "" //arreglo
			}else if(lexema == leer){
				return "" //leer
			}else if(lexema == falso){
				return "false"
			}else if(lexema == verdad){
				return "true"
			}else if(lexema == imprime){
				return "" //imprime
			}else if(lexema == retorno){
				return "return"
			}else if(lexema == lapso){
				return "for"
			}else if(lexema == mientras){
				return "while"
			}else if(lexema == no){
				return "else"
			}else if(lexema == si){
				return "if"
			}             
		}else if(categoria == Categoria.OPERADOR_ARITMETICO){
			if(lexema == "_plus"){
				return "+"
			}else if(lexema == "_minus"){
				return "-"
			}else if(lexema == "_multi"){
				return "*"
			}else if(lexema == "_div"){
				return "/"
			}
		}else if(categoria == Categoria.OPERADOR_ASIGNACION){
			if(lexema == "<-"){
				return "="
			}
		}else if(categoria == Categoria.OPERADOR_DECREMENTO){
			if(lexema == "_minus_minus"){
				return "--"
			}
		}else if(categoria == Categoria.OPERADOR_INCREMENTO){
			if(lexema == "_plus_plus"){
				return "++"
			}
		}else if(categoria == Categoria.OPERADOR_LOGICO){
			var and = "$"+"AND"
			var or = "$"+"OR"
			if(lexema == and){
				return "&&"
			}else if(lexema == or){
				return "||"
			}
		}else if(categoria == Categoria.OPERADOR_NEGACION){
			var ni = "$"+"NI"
			if(lexema == ni){
				return "!"
			}
		}else if(categoria == Categoria.OPERADOR_RELACIONAL){
			if(lexema == "¡<"){
				return "<"
			}else if(lexema == "¡>"){
				return ">"
			}else if(lexema == "¡<-"){
				return "<="
			}else if(lexema == "¡>-"){
				return ">="
			}else if(lexema == "¡><" || lexema == "-><-"){
				return "=="
			}else if(lexema == "¡<>"){
				return "!="
			}
		}else if(categoria == Categoria.LLAVE_IZQUIERDO){
			if(lexema == "["){
				return "{"
			}
		}else if(categoria == Categoria.LLAVE_DERECHO){
			if(lexema == "]"){
				return "}"
			}
		}else if(categoria == Categoria.CORCHETE_IZQUIERDO){
			if(lexema == "{"){
				return "["
			}
		}else if(categoria == Categoria.CORCHETE_DERECHO){
			if(lexema == "}"){
				return "]"
			}
		}else if(categoria == Categoria.FIN_DE_SENTENCIA){
			if(lexema == "?"){
				return ";"
			}
		}else if(categoria == Categoria.SEPARADOR){
			if(lexema == "¬"){
				return ","
			}
		}else if(categoria == Categoria.COMENTARIO_DE_LINEA){
			if(lexema == "#"){
				return "//"
			}
		}else if(categoria == Categoria.CARACTER){
			return lexema.replace("~", "'")
		}
		return lexema
	}
}// FIN DE LA CLASE