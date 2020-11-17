package co.edu.uniquindio.compiladores.sintaxis

class UnidadDeCompilacion2(var listaDeclVar:ArrayList<SentenciaDeclaracionVariableInmutable2>, var listaFunciones:ArrayList<Funcion2>) {
    override fun toString(): String {
        return "UnidadDeCompilacion(listaDeclVar=$listaDeclVar, listaFunciones=$listaFunciones)"
    }
}