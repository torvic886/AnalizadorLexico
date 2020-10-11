package co.edu.uniquindio.compiladores.app

import javafx.application.Application
import javafx.stage.Stage
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene

// APLICACION DEL ANALIZADOR LEXICO
// clase de la aplicacion
// su funcion es arrancar o dar ejecucion a la aplicacion del analizador lexico, es el menu main
class Aplicacion : Application()
{
	//el toString de la GUI
	override fun start(primaryStage: Stage?)
	{
		val loader = FXMLLoader(Aplicacion::class.java.getResource("/Inicio.fxml"))
		val parent:Parent =loader.load()
		
		val scene = Scene(parent)
		
		primaryStage?.scene = scene
		primaryStage?.title = "Analizador Lexico - Helix"
		primaryStage?.show()
	}
	
	companion object{
		//menu main de la aplicacion del analizador lexico
		@JvmStatic
		fun main( args: Array<String>){
			launch(Aplicacion::class.java)
		}
	}
}//FIN DE LA CLASE