package flexe.org.reminder

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NoteAdapter(private val mainActivity: MainActivity,private val noteList:ArrayList<Note>): RecyclerView.Adapter<NoteAdapter.ListItemHolder>() {
    private var lineDivider:Boolean=false

    inner class ListItemHolder(view: View):RecyclerView.ViewHolder(view),View.OnClickListener{
        internal var description=view.findViewById<TextView>(R.id.txtItemDescription)
        internal var time=view.findViewById<TextView>(R.id.txtItemDateTime)
        internal var priority=view.findViewById<TextView>(R.id.txtItemPriority)
        internal var location=view.findViewById<TextView>(R.id.txtItemLocation)
        internal var concerned=view.findViewById<TextView>(R.id.txtItemConcerned)
        internal var recallTime =view.findViewById<TextView>(R.id.txtItemRecall)


        /*
      -internal means that any client inside this module who sees the declaring class sees its internal members.
      You can have multiple Gradle modules that depends on each other within a single Android application,
       in that case, internal restricts visibility to within a given module.
      This could be useful if, for example, you have a separate data module that handles database and network tasks,
        and you only want to expose a couple interfaces from that module, but not their implementations.
          Otherwise, if you're not using multiple modules, and your entire application is just in the default app module,
       then the internal modifier makes no difference in comparison to the default public visibility.

       */
        init {
            view.isClickable = true
            view.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
            mainActivity.showNote(adapterPosition)
        }


    }

    /*
  We must implement/override the three functions coming from RecyclerView-Class :

1-The onCreateViewHolder function, which is called when a layout for a list item is required
2- The onBindViewHolder function, which is called when the RecyclerAdapter instance is bound to (connected/associated with) the RecyclerView instance in the layout
3- The getItemCount function, which will be used to return the number of Note instances in ArrayList
   */

// calls for creating each Item-View through the inflater(xml to object)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemHolder {
        val itemView= LayoutInflater.from(parent.context)
            .inflate(R.layout.each_item,parent,false)
        return ListItemHolder(itemView)

    }
    // displaying the data by calling of onBindViewHolder
    override fun onBindViewHolder(holder: ListItemHolder, position: Int) {

        val note = noteList[position]// or val note = noteList[holder.adapterPosition]
        val simpleDateFormat=SimpleDateFormat("dd.MM.yyyy")

        val formatter=simpleDateFormat.format(note.getTime().time)

        holder.description.text = note.getDescription()
        holder.location.text =note.getLocation()
        holder.concerned.text=note.getConcerned()

        holder.time.text=formatter
        Log.i("Priority NoteAdapter", note.getDescription())
        holder.recallTime.text=note.getRecall()

        when(note.getPriority()) {
            Priority.Very_Important->        holder.priority.text=Priority.Very_Important.myPriority
            Priority.Important->        holder.priority.text=Priority.Important.myPriority
            Priority.Not_Important->        holder.priority.text=Priority.Not_Important.myPriority


        }

    }

    override fun getItemCount(): Int {
        if (this.noteList.size != 0) {
            Log.i("Size of elements",""+noteList.size)
            return noteList.size

        }
// error
        return -1

    }

}
