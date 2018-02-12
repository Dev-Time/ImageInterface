import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.input.MouseEvent
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.text.Font
import java.util.*

class MyEventHandler(private val coords: LinkedList<Coordinate>, private val stackPane: StackPane) : EventHandler<MouseEvent> {
    override fun handle(event: MouseEvent) {
        coords.add(Coordinate(event.x, event.y))

        val circle = Circle(event.x, event.y, 5.0, Color.BLUE)
        StackPane.setAlignment(circle, Pos.TOP_LEFT)
        StackPane.setMargin(circle, Insets(event.y - 5 + 50, 0.0, 0.0, event.x - 5 + 50))
        stackPane.children.add(circle)

        val label = Label(coords.size.toString())
        StackPane.setAlignment(label, Pos.TOP_LEFT)
        StackPane.setMargin(label, Insets(event.y + 5 + 50, 0.0, 0.0, event.x + 5 + 50))
        label.font = Font.font(20.0)

        stackPane.children.add(label)

        event.consume()
    }
}