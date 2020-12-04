package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class ExpresionLogica3():Expresion3()
{
    var expresionRelacional: ExpresionRelacional3? = null
    var operadorLogico:Token? = null
    var expresionRelacional2: ExpresionRelacional3? = null
    var centi: Token? = null
    var expresionLogica: ExpresionLogica3? = null

    override fun toString(): String {
        return "ExpresionLogica(expresionRelacional=$expresionRelacional, operadorLogico=$operadorLogico, expresionRelacional2=$expresionRelacional2)"
    }
    constructor( expresionRelacional: ExpresionRelacional3, operadorLogico:Token, expresionRelacional2: ExpresionRelacional3 ): this() {
        this.expresionRelacional = expresionRelacional
        this.operadorLogico = operadorLogico
        this.expresionRelacional2 = expresionRelacional2
    }
    constructor(centi: Token): this() {
        this.centi = centi
    }
    constructor(expresionRelacional: ExpresionRelacional3): this() {
        this.expresionRelacional = expresionRelacional
    }
    constructor(expresionRelacional: ExpresionRelacional3, operadorLogico: Token, expresionLogica: ExpresionLogica3): this() {
        this.expresionRelacional = expresionRelacional
        this.operadorLogico = operadorLogico
        this.expresionLogica = expresionLogica
    }
    constructor(centi: Token, operadorLogico: Token, expresionLogica: ExpresionLogica3): this() {
        this.centi = centi
        this.operadorLogico = operadorLogico
        this.expresionLogica = expresionLogica
    }
    constructor(centi: Token, operadorLogico: Token, expresionRelacional: ExpresionRelacional3): this() {
        this.centi = centi
        this.operadorLogico = operadorLogico
        this.expresionRelacional = expresionRelacional
    }


    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito) {
        if (expresionRelacional2 != null && expresionRelacional != null) {
            expresionRelacional2!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
            expresionRelacional!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        } else if (expresionLogica != null) {
            expresionLogica!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        }
    }
    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Expresión Lógica")

        if (operadorLogico != null) {
            var raizOp = TreeItem("Op. Lógico: ${operadorLogico!!.lexema}")
            if (expresionRelacional != null && expresionLogica != null) {
                raizOp.children.add( expresionRelacional!!.getArbolVisual() )
                raizOp.children.add( expresionLogica!!.getArbolVisual() )
            }else if (centi != null && expresionRelacional != null) {
                raizOp.children.add(TreeItem("Centi: ${centi!!.lexema}"))
                raizOp.children.add( expresionRelacional!!.getArbolVisual() )
            }else if (centi != null && expresionLogica != null) {
                raizOp.children.add(TreeItem("Centi: ${centi!!.lexema}"))
                raizOp.children.add( expresionLogica!!.getArbolVisual() )
            }
            raiz.children.add( raizOp )
        } else if (expresionLogica != null) {
            raiz.children.add( expresionLogica!!.getArbolVisual() )
        } else if (centi != null) {
            raiz.children.add(TreeItem("Centi: ${centi!!.lexema}"))
        }else if (expresionRelacional != null) {
            raiz.children.add(expresionRelacional!!.getArbolVisual())
        }


        return raiz
    }

    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito: Ambito, listaErrores: ArrayList<Error>  ): String {
        return "centi"
    }
    override fun getJavaCode(): String {
        var codigo = ""
        when {
            expresionRelacional != null && expresionLogica != null -> {
                codigo += expresionRelacional!!.getJavaCode()
                codigo += operadorLogico!!.getJavaCode()
                codigo += expresionLogica!!.getJavaCode()
            }
            centi != null && expresionRelacional != null-> {
                codigo += centi!!.getJavaCode()
                codigo += operadorLogico!!.getJavaCode()
                codigo += expresionRelacional!!.getJavaCode()
            }
            centi != null && expresionLogica != null-> {
                codigo += centi!!.getJavaCode()
                codigo += operadorLogico!!.getJavaCode()
                codigo += expresionLogica!!.getJavaCode()
            }
            expresionRelacional != null -> {
                codigo += expresionRelacional!!.getJavaCode()
            }
            centi != null -> {
                codigo += centi!!.getJavaCode()
            }
        }
        return codigo
    }
}