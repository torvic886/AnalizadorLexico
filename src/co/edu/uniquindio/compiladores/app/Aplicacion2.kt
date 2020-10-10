package co.edu.uniquindio.compiladores.app

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
//EL COSTEÑO ES EL PAPI RICO
class Aplicacion2 : Application ()
{
    override fun start(primaryStage: Stage?)
    {
        val loader = FXMLLoader(Aplicacion2::class.java.getResource("/inicio.fxml"))
        val parent:Parent = loader.load()

        val scene = Scene (parent)

        primaryStage?.scene = scene
        primaryStage?.title = "Compiladores - Analizador Lexico"
        primaryStage?.show()



    }

    companion object{


        @JvmStatic
        fun main(args: Array<String>) {
            launch(Aplicacion2::class.java)
        }
    }

}