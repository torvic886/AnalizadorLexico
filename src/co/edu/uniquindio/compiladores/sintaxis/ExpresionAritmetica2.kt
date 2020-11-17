package co.edu.uniquindio.compiladores.sintaxis

open class ExpresionAritmetica2(var terminos:ArrayList<Any>):Expresion2()
{
    override fun toString(): String {
        return "ExpresionAritmetica(terminos=$terminos)"
    }
}