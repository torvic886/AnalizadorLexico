package co.edu.uniquindio.compiladores.sintaxis

class SentenciaRetorno2(var expresion: Expresion2):Sentencia2() {
    override fun toString(): String {
        return "SentenciaRetorno(expresion=$expresion)"
    }
}