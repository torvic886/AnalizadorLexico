package co.edu.uniquindio.compiladores.sintaxis

class SentenciaIncremento2(var expresionAritmetica: ExpresionAritmetica2):Sentencia2() {
    override fun toString(): String {
        return "SentenciaIncremento(expresionAritmetica=$expresionAritmetica)"
    }
}