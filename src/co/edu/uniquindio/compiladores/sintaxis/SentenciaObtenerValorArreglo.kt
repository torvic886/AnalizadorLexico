package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class SentenciaObtenerValorArreglo(var nombre: Token, var pos: Token): Sentencia3() {
    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito) {
        var s = tablaSimbolos.buscarSimboloValor(nombre.lexema, ambito, nombre.fila, nombre.columna)
        if (s == null) {
            listaErrores.add(Error("El campo '${nombre!!.lexema}' no existe dentro del ambito '${ambito.nombre}'", nombre.fila, nombre.columna))
        }
        if (pos.categoria == Categoria.IDENTIFICADOR) {
            var s2 = tablaSimbolos.buscarSimboloValor(pos.lexema, ambito, pos.fila, pos.columna)
            if (s2 == null) {
                listaErrores.add(Error("El campo '${pos!!.lexema}' no existe dentro del ambito '${ambito.nombre}'", pos.fila, pos.columna))
            }else if (s2!!.tipo != "entero") {
                listaErrores.add(Error("Tipo de dato del campo ${pos.lexema} que es ("+s2.tipo+") no es entero", nombre.fila, nombre.columna))
            }
        }
    }
    override fun getJavaCode(): String {
        return nombre.getJavaCode()+ "[" +pos.getJavaCode()+"];"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Obtener valor arr")
        raiz.children.add(TreeItem("Nombre: ${nombre.lexema}"))
        raiz.children.add(TreeItem("pos: ${pos.getJavaCode()}"))
        return raiz
    }


}