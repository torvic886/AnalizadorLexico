package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem
import kotlin.concurrent.fixedRateTimer

class Ciclo(var identificador: Token, var identificador2: Token, var listaSentnntencias: ArrayList<Sentencia3>): Sentencia3() {
    var tipoArreglo:String = "Object"
    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito) {
        var s2 = tablaSimbolos.buscarSimboloValor(identificador.lexema, ambito, identificador!!.fila, identificador.columna)
        if (s2 != null) {
            listaErrores.add(Error("El nombre '${identificador.lexema}' del iterador ya existe dentro del '${ambito.nombre}'", identificador.fila, identificador.columna))
        }
        var s3 = tablaSimbolos.buscarSimboloValor(identificador2.lexema, ambito, identificador2.fila, identificador2.columna)
        if (s3 == null) {
            listaErrores.add(Error("El nombre '${identificador2.lexema}' del arreglo a recorrer no existe dentro del '${ambito.nombre}'", identificador2.fila, identificador2.columna))
        } else {
            tipoArreglo = s3.tipo
        }
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Ciclo for")

        raiz.children.add(TreeItem("Iterador: ${identificador.lexema}" ))
        raiz.children.add(TreeItem("Lista: ${identificador2.lexema}" ))

        if (listaSentnntencias.size >= 1) {
            for (s in listaSentnntencias) {
                raiz.children.add(s.getArbolVisual())
            }
        }
        return raiz
    }

    override fun getJavaCode(): String {
        var codigo = "for(" + tipoArreglo+ " " + identificador.getJavaCode()+":"+identificador2.getJavaCode()+"){"
        for (s in listaSentnntencias) {
            codigo += s.getJavaCode()
        }
        codigo += "}"
        return codigo
    }
}