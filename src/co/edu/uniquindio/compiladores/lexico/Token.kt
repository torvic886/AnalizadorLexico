package co.edu.uniquindio.compiladores.lexico

class Token(var lexema: String, var categoria: Categoria, var fila: Int, var columna: Int) {
    override fun toString(): String {
        return "Token(lexema='$lexema', categoria=$categoria, fila=$fila, columna=$columna)"
    }

    fun getJavaCode(): String {
        when (categoria) {
            Categoria.PALABRA_RESERVADA -> {
                when (lexema) {
                    "entero" -> {
                        return "int"
                    }
                    "dec" -> {
                        return "double"
                    }
                    "cad" -> {
                        return "String"
                    }
                    "atm" -> {
                        return "char"
                    }
                    "centi" -> {
                        return "boolean"
                    }
                    "whereas" -> {
                        return "while"
                    }
                    "check" -> {
                        return "if"
                    }
                    "dev" -> {
                        return "return"
                    }
                    "other" -> {
                        return "else"
                    }
                    "AND" -> {
                        return "&&"
                    }
                    "OR" -> {
                        return "||"
                    }
                    "no" -> {
                        return "!"
                    }
                }
            }
            Categoria.CENTI -> {
                when (lexema) {
                    "yes" -> {
                        return "true"
                    }
                    "not" -> {
                        return "false"
                    }

                }
            }
            Categoria.OPERADOR_DE_ASIGNACION -> {
                when (lexema) {
                    "@" -> {
                        return "="
                    }
                    "@-" -> {
                        return "-="
                    }
                    "@+" -> {
                        return "+="
                    }
                    "@/" -> {
                        return "/="
                    }
                }
            }
            Categoria.OPERADOR_LOGICO -> {
                when (lexema) {
                    "AND" -> {
                        return "&&"
                    }
                    "OR" -> {
                        return "||"
                    }
                }
            }
            Categoria.OPERADOR_RELACIONAL -> {
                when (lexema) {
                    "<?" -> {
                        return "<"
                    }
                    ">?" -> {
                        return ">"
                    }
                    "<??" -> {
                        return "<="
                    }
                    ">??" -> {
                        return ">="
                    }
                    "??" -> {
                        return "=="
                    }
                    "!!" -> {
                        return "!="
                    }
                }
            }
            Categoria.OPERADOR_ADITIVO -> {
                when (lexema) {
                    "<+>" -> {
                        return "+"
                    }
                    "<->" -> {
                        return "-"
                    }
                }
            }
            Categoria.OPERADOR_MULTIPLICATIVO -> {
                when (lexema) {
                    "<*>" -> {
                        return "*"
                    }
                    "</>" -> {
                        return "/"
                    }
                    "<%>" -> {
                        return "%"
                    }
                }
            }
            Categoria.OPERADOR_ARITMETICO -> {
                when (lexema) {
                    "<*>" -> {
                        return "*"
                    }
                    "</>" -> {
                        return "/"
                    }
                    "<+>" -> {
                        return "+"
                    }
                    "<->" -> {
                        return "-"
                    }
                    "<%>" -> {
                        return "%"
                    }
                }
            }
            Categoria.SEPARADOR_DE_SENTENCIA -> {
                when (lexema) {
                    ";" -> {
                        return ","
                    }
                }
            }
            Categoria.FIN_SENTENCIA -> {
                when (lexema) {
                    "_" -> {
                        return ";"
                    }
                }
            }
            Categoria.CADENA_CARACTERES -> {
                return lexema.replace("|", "\"")
            }
            Categoria.CARACTER -> {
                return lexema.replace("\"", "'")
            }
            Categoria.ENTERO -> {
                return lexema.replace("$", "")
            }
            Categoria.DECIMAL -> {
                lexema = lexema.replace(",", ".")
                return lexema.replace("$", "")
            }
            Categoria.NEGACION -> {
                when (lexema) {
                    "no" -> {
                        return "!"
                    }
                }
            }
        }
        return lexema
    }
}