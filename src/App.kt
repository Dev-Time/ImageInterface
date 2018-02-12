import javafx.application.Application
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ScrollPane
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.FlowPane
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import java.io.File
import java.io.FileOutputStream
import java.util.*

class App: Application() {
    override fun start(primaryStage: Stage) {
        val clearButton = Button()
        val nextButton = Button()
        val buttonPane = FlowPane(Orientation.HORIZONTAL)
        val imageView = ImageView()
        val stackPane = StackPane()
        val root = FlowPane(Orientation.VERTICAL)

        val coords = LinkedList<Coordinate>()
        val images = LinkedList<Image>()
        val imagePaths = LinkedList<String>()

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
                imagePaths.add(i.absolutePath.dropLast(4))
            }
        }

        if (images.size < 1) {
            println("No image files found in current directory")
            return
        }

        primaryStage.title = "Landmark Identification"
        primaryStage.scene = Scene(root)

        clearButton.text = "Clear"
        clearButton.onAction = EventHandler<ActionEvent> {
            coords.clear()
            stackPane.children.remove(1, stackPane.children.size)
        }

        imageView.image = images[imageIndex]
        imageView.isPreserveRatio = true
        imageView.isSmooth = true
        imageView.isCache = true
        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, MyEventHandler(coords, stackPane))

        StackPane.setMargin(imageView, Insets(50.0, 50.0, 50.0, 50.0))
        stackPane.children.add(imageView)

        nextButton.text = "Next"
        nextButton.onAction = EventHandler<ActionEvent> {

            val file = FileOutputStream(imagePaths[imageIndex] + ".csv").bufferedWriter()

            for (i in coords) {
                file.append(i.x.toString() + ',' + i.y.toString())
                file.newLine()
                println(i)

            }

            file.close()

            coords.clear()
            stackPane.children.remove(1, stackPane.children.size)

            if (imageIndex < images.size - 1) {
                imageIndex++
                imageView.image = images[imageIndex]
                primaryStage.width = images[imageIndex].width + 100
            }
        }

        buttonPane.children.add(clearButton)
        buttonPane.children.add(nextButton)

        val scrollpane = ScrollPane()
        scrollpane.maxHeight = 900.0
        scrollpane.maxWidth = 1900.0
        scrollpane.content = stackPane

        root.children.add(scrollpane)
        root.children.add(buttonPane)
        root.prefWrapLength = 1000.0

        primaryStage.show()
    }

    fun begin() {
        launch()
    }
}