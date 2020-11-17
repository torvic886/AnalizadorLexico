package co.edu.uniquindio.compiladores.sintaxis

class SentenciaDecremento2(var expresionAritmetica:ExpresionAritmetica2):Sentencia2() {
    override fun toString(): String {
        return "SentenciaDecremento(expresionAritmetica=$expresionAritmetica)"
    }
}