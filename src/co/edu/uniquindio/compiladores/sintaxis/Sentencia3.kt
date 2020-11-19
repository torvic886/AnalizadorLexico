package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

open class Sentencia3 {
    open fun getArbolVisual(): TreeItem<String> {
        return TreeItem("Sentencia")
    }
}