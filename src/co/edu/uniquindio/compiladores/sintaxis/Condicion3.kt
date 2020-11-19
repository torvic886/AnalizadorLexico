package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class Condicion3(var condicion: Token?, var expRel: ExpresionRelacional3?, var expLo:ExpresionLogica3?, var negacion: Negacion3?)
{
    fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Condición")

        if (condicion != null) {
            raiz.children.add( TreeItem("Booleano: ${condicion!!.lexema}"))
        }

        if (expRel != null) {
            raiz.children.add( expRel!!.getArbolVisual() )
        }

        if (expLo != null) {
            raiz.children.add( expLo!!.getArbolVisual() )
        }

        if (negacion != null) {
            raiz.children.add( negacion!!.getArbolVisual() )
        }

        return raiz
    }

    override fun toString(): String {
        return "Condicion2(condicion=$condicion, expRel=$expRel, expLo=$expLo, negacion=$negacion)"
    }


}