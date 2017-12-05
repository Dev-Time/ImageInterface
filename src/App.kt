import javafx.application.Application
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.FlowPane
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import java.io.File
import java.util.*

class App: Application() {
    override fun start(primaryStage: Stage) {
        primaryStage.title = "Landmark Identification"

        val coords = LinkedList<Coordinate>()
        val images = LinkedList<Image>()

        var imageIndex = 0

        val currentDirectory = File(".")
        val fileWalk = currentDirectory.walkTopDown().maxDepth(1)
        fileWalk.maxDepth(1)
        val fileIterator = fileWalk.iterator()

        for (i in fileIterator) {
            if (i.isFile && i.extension == "png") {
                if (images.size == 0) {
                    images.add(Image("file:" + i.absolutePath, false))
                }
                else {
                    images.add(Image("file:" + i.absolutePath, true))
                }
            }
        }

        val clearButton = Button()
        clearButton.text = "Clear"

        val stackPane = StackPane()

        clearButton.onAction = EventHandler<ActionEvent> {
            coords.clear()
            stackPane.children.remove(1, stackPane.children.size)
        }

        val imageView = ImageView(images[imageIndex])
        //imageView.fitWidth =100.0
        imageView.isPreserveRatio = true
        imageView.isSmooth = true
        imageView.isCache = true
        imageView.prefHeight(images[imageIndex].height)
        imageView.prefWidth(images[imageIndex].width)
        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, MyEventHandler(coords, stackPane))

        StackPane.setMargin(imageView, Insets(50.0, 50.0, 50.0, 50.0))
        stackPane.children.add(imageView)

        val nextButton = Button()
        nextButton.text = "Next"

        val buttonPane = FlowPane(Orientation.HORIZONTAL)
        buttonPane.children.add(clearButton)
        buttonPane.children.add(nextButton)
        buttonPane.prefWrapLength = 100.0

        val root = FlowPane(Orientation.VERTICAL)
        root.children.add(stackPane)
        root.children.add(buttonPane)
        root.prefWrapLength = images[0].height + 200

        primaryStage.scene = Scene(root)

        nextButton.onAction = EventHandler<ActionEvent> {
            for (i in coords) {
                println(i)
            }

            coords.clear()
            stackPane.children.remove(1, stackPane.children.size)

            if (imageIndex < images.size - 1) {
                imageIndex++
                imageView.prefHeight(images[imageIndex].height)
                imageView.prefWidth(images[imageIndex].width)
                imageView.image = images[imageIndex]
                primaryStage.width = images[imageIndex].width + 100
            }
        }

        primaryStage.show()
    }

    fun begin() {
        launch()
    }
}