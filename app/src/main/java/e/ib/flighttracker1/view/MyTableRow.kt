package e.ib.flighttracker1.view

import android.content.Context
import android.util.AttributeSet
import android.widget.TableRow

class MyTableRow@JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : TableRow(context, attrs) {

    lateinit var objs : Array<String?>

}