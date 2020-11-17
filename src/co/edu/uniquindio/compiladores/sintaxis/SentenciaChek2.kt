package co.edu.uniquindio.compiladores.sintaxis

class SentenciaChek2(var condicion: Condicion2, var bloqueSentencias: ArrayList<Sentencia2>, var other: Other2?):Sentencia2() {
    override fun toString(): String {
        return "SentenciaChek(condicion=$condicion, bloqueSentencias=$bloqueSentencias, other=$other)"
    }
}