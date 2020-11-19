package co.edu.uniquindio.compiladores.sintaxis

class SentenciaIncremento3(var expresionAritmetica: ExpresionAritmetica3):Sentencia3() {
    override fun toString(): String {
        return "SentenciaIncremento(expresionAritmetica=$expresionAritmetica)"
    }

}