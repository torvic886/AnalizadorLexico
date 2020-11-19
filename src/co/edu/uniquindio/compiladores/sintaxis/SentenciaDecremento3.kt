package co.edu.uniquindio.compiladores.sintaxis

class SentenciaDecremento3(var expresionAritmetica:ExpresionAritmetica3):Sentencia3() {
    override fun toString(): String {
        return "SentenciaDecremento(expresionAritmetica=$expresionAritmetica)"
    }

}