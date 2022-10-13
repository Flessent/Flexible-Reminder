package flexe.org.reminder

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.collections.ArrayList
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction


class MainActivity : AppCompatActivity(),  DialogNewNote.OnInputListener{
    //Properties for saving data through JSON-Object
    private var mSerializer: JSONSerializer? =null
    private var noteList:ArrayList<Note>? =null
    private var recyclerView:RecyclerView?=null
    private var adapter: NoteAdapter? = null
    private var showDividers:Boolean=true
    private var chosenDate =""
private val CODE_DateAndTime_Activity:Int=0
private  val RESULT_OK:Int=1
    private var reveivedDateMeeting=""
    private var receivedOnlyDate=""
    private  var receivedOnlyTime=""
    private var getDescriptionFromDialog=""
private var getLocationFromDialog=""
    private var getConcernedFromFragment=""
    private var dialogNewNote: DialogNewNote?=null
    private var fragmentManager: FragmentManager?=null
    private var fragmentTransaction:FragmentTransaction?=null
    private var textDateTime:TextView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val  fabRef=findViewById<FloatingActionButton>(R.id.fabCreateNewNote)
     fragmentManager= supportFragmentManager //or getSupportFragmentManager() can be used



        fabRef.setOnClickListener {
            fragmentTransaction=fragmentManager!!.beginTransaction()
            this.dialogNewNote = DialogNewNote("","","")

           if(dialogNewNote!!.isAdded){

               this.dialogNewNote!!.show(fragmentManager!!, "firstInstance") // we retrieve the FragmentManager in order to manage the back stack (child-Fragment)

           } else{
               fragmentTransaction!!.add(dialogNewNote!!,"firstInstance")
               fragmentTransaction!!.addToBackStack("firstInstance")
               fragmentTransaction!!.commit()

           }

            //fragmentManager!!.saveFragmentInstanceState(dialogNewNote!!)

            //val intent=getIntent()
           // val finalDate= intent.getStringExtra("MyDate")
        }


// handling the saves process with JSON
        mSerializer = JSONSerializer("FlexReminder.json", applicationContext)
        try {
             noteList = mSerializer!!.loadSavedInformations()


        } catch (e: Exception) {
            noteList = ArrayList()
           // Log.e("Error loading notes: ", "", e)
        }
        recyclerView = findViewById<RecyclerView>(R.id.recyclerViewListNote)  // create a reference to RecyclerView-Widget
        adapter = NoteAdapter(this, this.noteList!!)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.itemAnimator = DefaultItemAnimator()

        recyclerView!!.adapter = adapter

        // set txtDateAndTime of DialogFragment

        this.textDateTime=findViewById(R.id.txtShowChosenDateandTime)



        //val note =Note("Description",Calendar.getInstance())
        /*   dateTimeString= (note.time.get(Calendar.DAY_OF_MONTH)).toString()+"."+(note.time.get(Calendar.MONTH)).toString()+
            "."+(note.time.get(Calendar.YEAR)).toString()+
            "."+(note.time.get(Calendar.HOUR)).toString()+":"+(note.time.get(Calendar.MINUTE)).toString()+":"+(note.time.get(Calendar.SECOND)).toString()
*/        }

    fun getDate():String{
return this.chosenDate
    }

    private fun saveNotes() {
        try {
            mSerializer!!.save(this.noteList!!)
           // Log.e("Note saved ","Info in saveNote in MainActivity" )

        } catch (e: Exception) {
            //Log.e("Error Saving Notes", "In MainActivity", e)
        }
    }
    fun createNewNote(note: Note) {
        this.noteList!!.add(note)
        adapter!!.notifyDataSetChanged()
       // Log.i("createNewN MainActivity", note.getPriority().myPriority)
    }

    fun showNote(noteToShow: Int) {
        val dialog = DialogShowNote()
        dialog.sendNoteSelected(this.noteList!![noteToShow])
        dialog.show(supportFragmentManager, "")
    }


        override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.settings_menu, menu)
            return true
        }

     /* override  fun onClick(view: View) {
          when(view.id){
              R.id.textSetDateAndTime->{
                  val intent = Intent(this, DateTimeActivity::class.java)

                  startActivityForResult(intent, CODE_DateAndTime_Activity);
              } }   } */

//calls after DateAndTimeActivity finishes. getting date and time from DateAndTimeActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data!=null) {
          //  if (requestCode == CODE_DateAndTime_Activity) {
             //   if (resultCode == RESULT_OK) {
                    reveivedDateMeeting = data.getStringExtra("MyFinalDateTime").toString()
            receivedOnlyDate=data.getStringExtra("onlyDate").toString()
            receivedOnlyTime=data.getStringExtra("onlyTime").toString()


            //  }
          //  }

        }

    }





    //we will override the onPause function to save our user's data
    override fun onPause() {
        super.onPause()
        saveNotes()
        Log.e("MainActivity","onPause()")

    }

    override fun onResume() {
        super.onResume()
        this.fragmentManager= supportFragmentManager //or getSupportFragmentManager() can be used
        this.fragmentTransaction=fragmentManager!!.beginTransaction()
        val prefs = getSharedPreferences("FlexReminder", Context.MODE_PRIVATE)
        val onResumeDialog =DialogNewNote(this.getDescriptionFromDialog,this.getLocationFromDialog,this.getConcernedFromFragment)


   if (!(onResumeDialog!!.isAdded)){

     //  this.fragmentTransaction!!.add(dialogFragment,"secondInstance")
       this.fragmentTransaction!!.addToBackStack("secondInstance")
       this.fragmentTransaction!!.commit()
   }
        //Send Information to Fragment
        val bundle=Bundle()
        bundle.putString("MyDateToFragment",this.reveivedDateMeeting)
        bundle.putString("DescriptionFromActivity",this.getDescriptionFromDialog)
        bundle.putString("LocationFromActivity",this.getLocationFromDialog)
        bundle.putString("ConcernedFromActivity",this.getConcernedFromFragment)
        bundle.putString("onlyDate",this.receivedOnlyDate)
        bundle.putString("onlyTime",this.receivedOnlyTime)

        onResumeDialog!!.arguments=bundle


/*        if(textDateTime!!.text!=null){
            this.textDateTime!!.text=this.reveivedDate
            Log.e("Null", "textDateTime is null")
        } else    this.textDateTime!!.text="Fuck"
*/
        if(this.dialogNewNote!=null && this.dialogNewNote!=null){
            Log.e("Error","DialogNote already started")
            this.dialogNewNote!!.stopFragment()
            onResumeDialog!!.show(this.fragmentManager!!,"secondInstance")
        } else onResumeDialog!!.show(this.fragmentManager!!,"secondInstance")


        showDividers = prefs.getBoolean( "dividers", true)


        // Add a neat dividing line between list items
        if (showDividers)   recyclerView!!.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        else {
// check there are some dividers
// or the app will crash
            if (recyclerView!!.itemDecorationCount > 0) recyclerView!!.removeItemDecorationAt(0)
        }

    }
    override fun  sendInput(description :String, location:String,concerned:String)
    {

        this.getDescriptionFromDialog = description
        this.getLocationFromDialog=location
        this.getConcernedFromFragment=concerned


       // Log.e("gotten Description", this.getDescriptionFromDialog)
    }




}
