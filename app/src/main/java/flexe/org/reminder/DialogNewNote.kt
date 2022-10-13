package flexe.org.reminder

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import java.text.SimpleDateFormat

import java.util.*

class DialogNewNote(description: String, location: String, concerned: String) : DialogFragment(),View.OnClickListener {
    private var mOnInputListener:OnInputListener?=null

    private val noteBuilder = Note.NoteBuilder()
private   var editDescription : EditText?=null
    private var editLocation : EditText?=null
    private var editConcerned : EditText?=null
   private var  radioGroup: RadioGroup?=null
   private var  veryImportantPriority :RadioButton?=null
    private var importantPriority:RadioButton?=null
    private var notImportantPriority:RadioButton?=null
    private var txtShowSelectedDate : TextView?=null
    private var btnOk:Button?=null
    private var btnCancel:Button?=null
    private var txtSetDateAndTime : TextView?=null

  override
    fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        // convert new_note.xml into an object with help of layoutInflater
        val dateMeeting: String
        var onlyDate=""
      var onlyTime=""
        var descriptioFromActivity=""
        var locacatioFromActivity=""
        var concernedFromActivity=""
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.new_note, null)

       this.editDescription = dialogView.findViewById(R.id.editTxtDescription)
        this.editLocation = dialogView.findViewById(R.id.editTxtLocation)
        this.editConcerned = dialogView.findViewById(R.id.editTxtConcernedTask)
        this.radioGroup = dialogView.findViewById(R.id.radioGroupPriority)
        this.veryImportantPriority = dialogView.findViewById(R.id.radioBtnVeryImportant)
        this.importantPriority = dialogView.findViewById(R.id.radioBtnImportant)
        this.notImportantPriority = dialogView.findViewById(R.id.radioBtnNoImportant)
        this.txtShowSelectedDate = dialogView.findViewById(R.id.txtShowChosenDateandTime)
        this.btnOk = dialogView.findViewById(R.id.btnCreateNewNote)
        this.btnCancel = dialogView.findViewById(R.id.btnCancelNewNote)
        this.txtSetDateAndTime = dialogView.findViewById(R.id.textSetDateAndTime)





        builder.setView(dialogView)
            .setMessage("Add a new Program")// This will be called by the FragmentManager from MainActivity


        var choosenPriority= ""

        // Received Information from MainActivity

        val bundle = getArguments()
        val date= Calendar.getInstance().time // return a Calendar
        val sdf =SimpleDateFormat("dd.MM.yyy HH:mm")
        val defaultDate=sdf.format(date)
        if (bundle != null) {
            dateMeeting = bundle.getString("MyDateToFragment").toString()
            onlyDate=bundle.getString("onlyDate").toString()
            onlyTime=bundle.getString("onlyTime").toString()


            descriptioFromActivity=bundle.getString("DescriptionFromActivity").toString()
            locacatioFromActivity=bundle.getString("LocationFromActivity").toString()
            concernedFromActivity=bundle.getString("ConcernedFromActivity").toString()

            if (dateMeeting.isNotEmpty()){
                this.txtShowSelectedDate!!.text = dateMeeting

            } else  this.txtShowSelectedDate!!.text = defaultDate


        } else{

            this.txtShowSelectedDate!!.text=defaultDate
        }

// Set field with each  received values from Activity

this.editDescription?.setText(descriptioFromActivity)
this.editLocation?.setText(locacatioFromActivity)
this.editConcerned?.setText(concernedFromActivity)




        this.radioGroup!!.setOnCheckedChangeListener { group, checkedId ->

            when (checkedId) {
                this.veryImportantPriority!!.id -> {

                    this.noteBuilder
                        .setPriority(this.veryImportantPriority!!.text.toString())
                    //choosenPriority=Priority.Very_Important.myPriority
                    Log.e("Priority veryIm", choosenPriority)


                }
                this.importantPriority!!.id -> {

                    this.noteBuilder
                        .setPriority(this.importantPriority!!.text.toString())
                    // choosenPriority=Priority.Important.myPriority
                    Log.e("Priority Impo", choosenPriority)


                }
                this.notImportantPriority!!.id -> {

                    this.noteBuilder
                        .setPriority(this.notImportantPriority!!.text.toString())
                    // choosenPriority=Priority.Not_Important.myPriority
                    Log.e("Priority notImpo", choosenPriority)
                }
            }
        }

        this.txtSetDateAndTime!!.setOnClickListener { view->
            val intent = Intent(this.context, DateTimeActivity::class.java)
            this.mOnInputListener?.sendInput(this.editDescription!!.text.toString(), this.editLocation!!.text.toString(),this.editConcerned!!.text.toString())
            startActivityForResult(intent, 1);
            dismiss()
        }

        this.btnOk!!.setOnClickListener {
            val calendar :Calendar  = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY)
            calendar.setTime(sdf.parse(onlyDate))
calendar.set(Calendar.HOUR,onlyTime.substring(0,2).toInt())
            calendar.set(Calendar.MINUTE,onlyTime.substring(4,5).toInt())
            this.noteBuilder
                .setDescription(this.editDescription?.getText().toString())
                .setLocation(this.editLocation!!.getText().toString())
                .setConcernedPersonOrEnterprise(this.editConcerned!!.getText().toString())
                .setTime(calendar )


                       // Validity of fulling of each field

                          if (! ( this.editDescription!!.text.toString().isNotEmpty() )  ||
                              ! ( this.editLocation!!.text.toString().isNotEmpty() )||
                              ! ( this.editConcerned!!.text.toString().isNotEmpty() ) ){
                              Toast.makeText(requireActivity(),"Description is empty !",Toast.LENGTH_LONG).show()
                          } else {
                              // Pass newNote back to MainActivity

                          }


           // Log.i("Saved Note is ", noteBuilder.create2().toString())
            val callingActivity = activity as MainActivity?
            callingActivity!!.createNewNote(this.noteBuilder.create2())
            // quit the dialog after save of note
            dismiss()

        }
        //quit the dialog view
        this.btnCancel!!.setOnClickListener { view ->

            dismiss()
        }

        return builder.create()
    }

    override fun onClick(view: View?) {


    }

    override fun onPause() {
        super.onPause()
        val description = this.editDescription?.getText().toString();
        val locaton = this.editLocation?.getText().toString();
        val concerned = this.editConcerned?.getText().toString();

        mOnInputListener?.sendInput(description,locaton,concerned);
    }


    fun stopFragment(){
        this.onStop()
    }
    override fun onStop() {
        super.onStop()

    }

    // function and interface for sending of information to Activity
    interface OnInputListener {
        fun  sendInput( description:String, location:String,concerned:String);
    }
    override  fun onAttach(context: Context)
    {
        super.onAttach(context)
        try {
            mOnInputListener= requireActivity() as  OnInputListener;
        }
        catch (e:ClassCastException ) {
            Log.e("TAG", "onAttach: ClassCastException: "
                    + e.message);
        }
    }

}