package flexe.org.reminder

import androidx.appcompat.app.AlertDialog;
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.agenda_displaying.*
import android.widget.TextView
import java.text.SimpleDateFormat

class DialogShowNote :DialogFragment() {
    private var note: Note? = null
// displaying a note after its creation
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(this.requireActivity())
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.agenda_displaying, null)
        val txtDescription = dialogView.findViewById<TextView>(R.id.txtShowDescription)
        val txtTDateTime = dialogView.findViewById<TextView>(R.id.txtShowDateTime)
        //txtDescription.text = note!!.description
        val txtLocation =   dialogView.findViewById<TextView>(R.id.txtShowLocation)
        val txtPriority =  dialogView.findViewById<TextView>(R.id.txtShowPriority)
    val txtRecallTime=dialogView.findViewById<TextView>(R.id.txtShowRecallTime)
        val txtConcerned =  dialogView.findViewById<TextView>(R.id.txtShowConcernedPerson)

    val simpleDateFormat= SimpleDateFormat("dd.MM.yyyy")

    val formatter=simpleDateFormat.format(note!!.getTime().time)

      txtDescription.text=note!!.getDescription()
        txtLocation.text=note!!.getLocation()
        txtConcerned.text=note!!.getConcerned()
        txtPriority.text=note!!.getPriority().myPriority
        txtTDateTime.text=formatter
    txtRecallTime.text=note!!.getRecall()


        /*  if (!note!!.important){
              txtImportant.visibility = View.GONE
          }
          if (!note!!.toDo){
              txtTodo.visibility = View.GONE
          }
          if (!note!!.idea){
              txtIdea.visibility = View.GONE
          } */
        val btnOK = dialogView.findViewById<Button>(R.id.btnDestroySDialogShowNote)
        builder.setView(dialogView).setMessage("Your Note")

        btnOK.setOnClickListener{
            dismiss()
        }
        return builder.create()




    }

    // Receive a note from the MainActivity class
    fun sendNoteSelected(noteSelected: Note) {
        note = noteSelected
    }
}