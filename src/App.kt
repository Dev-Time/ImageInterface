import javafx.application.Application
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Orientation
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.FlowPane
import javafx.stage.Stage
import java.util.*


class App: Application() {
    override fun start(primaryStage: Stage) {
        primaryStage.title = "Landmark Identification"

        val coords = LinkedList<Coordinate>()
        val images = LinkedList<Image>()

        var imageIndex = 0

        images.add(Image("file:ship-thrust.png", false))
        images.add(Image("file:ship-nothrust.png", true))

        val clearButton = Button()
        clearButton.text = "Clear"
        clearButton.onAction = EventHandler<ActionEvent> { coords.clear() }

        val imageView = ImageView(images[imageIndex])
        //imageView.fitWidth =100.0
        imageView.isPreserveRatio = true
        imageView.isSmooth = true
        imageView.isCache = true
        imageView.prefHeight(images[imageIndex].height)
        imageView.prefWidth(images[imageIndex].width)
        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, MyEventHandler(coords))

        val nextButton = Button()
        nextButton.text = "Next"
        nextButton.onAction = EventHandler<ActionEvent> {
            for (i in coords) {
                println(i)
            }

            if (imageIndex < images.size - 1) {
                imageIndex++
                imageView.prefHeight(images[imageIndex].height)
                imageView.prefWidth(images[imageIndex].width)
                imageView.image = images[imageIndex]
            }
        }

        val buttonPane = FlowPane(Orientation.HORIZONTAL)
        buttonPane.children.add(clearButton)
        buttonPane.children.add(nextButton)
        buttonPane.prefWrapLength = 100.0

        val root = FlowPane(Orientation.VERTICAL)
        root.children.add(imageView)
        root.children.add(buttonPane)
        root.prefWrapLength = images[0].height + 100

        primaryStage.scene = Scene(root)
        primaryStage.show()
    }

    fun begin() {
        launch()
    }
}