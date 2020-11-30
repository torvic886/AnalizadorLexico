package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class SentenciaInicializacionConjunto3(): Sentencia3() {
    var valoresNumericos: ArrayList<ValorNumerico3> = ArrayList<ValorNumerico3>()
    var identificador: Token? = null

    constructor(identificador: Token, valoresNumericos: ArrayList<ValorNumerico3>): this() {
        this.valoresNumericos = valoresNumericos
        this.identificador = identificador
    }
    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Inicialización Arreglo")

        var raizValores = TreeItem("Valores")
        if (valoresNumericos.size >= 1) {
            for (v in valoresNumericos) {
                raizValores.children.add( v.getArbolVisual() )
            }
        }
        raiz.children.add(raizValores)
        return raiz
    }
}