package flexe.org.reminder


import android.annotation.SuppressLint
import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class Note() {
// NoteBuilder is a nested class
    class  NoteBuilder(){ // by default, a nested class is static. When we don't need a static class but rather a inner class, we muss add the keyword inner

    private  var description:String
    private  var time:Calendar
    private var recallTime:Calendar
    private var priority :Priority
    private var location :String
    private var concernedPersonOrEnterprise:String

    private val JSON_DESCRIPTION="Description"
    private var JSON_TIME="Time"
    private  var JSON_PRIORITY="Priority"
    private val JSON_LOCATION="Location"
    private val JSON_CONCERNED="Concerned"
    private val JSON_RECALL="Recall"


    init {
        priority=Priority.Very_Important
        time=Calendar.getInstance()
        recallTime=Calendar.getInstance()


        description="default value"

        location="default value"
        concernedPersonOrEnterprise="default value"


    }


    constructor(description: String,time: Calendar,recallTime:Calendar,priority:Priority,location :String,concernedPersonOrEnterprise:String) :this(){
        this.location=location
        this.description=description
        this.time=time
        this.recallTime=recallTime
        this.priority.myPriority=priority.myPriority
        this.concernedPersonOrEnterprise=concernedPersonOrEnterprise
    }
    @Throws(JSONException::class)
    fun convertToJSON(note :Note): JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put(JSON_TIME, note.time)
        jsonObject.put(JSON_RECALL, note.recallTime)

        jsonObject.put(JSON_DESCRIPTION, note.description)
        jsonObject.put(JSON_LOCATION, note.location)
        jsonObject.put(JSON_CONCERNED, note.concernedPersonOrEnterprise)
        jsonObject.put(JSON_PRIORITY, note.priority.myPriority)
        //Log.e("Error ConvertJson",jsonObject.toString() )
        return jsonObject
    }


//Kotlin function parameters are final. There is no val or final keyword because that's the default (and can't be changed).
   fun setRecallTime( recallTime:Calendar) :NoteBuilder{
        this.recallTime=recallTime
        return this
    }
    fun getPriority() :Priority{return this.priority}
    fun setPriority( priority: String) :NoteBuilder{
        this.priority.myPriority=priority
        return this
    }
    fun setLocation( location: String) :NoteBuilder{
        this.location=location
        return this
    }
    fun setConcernedPersonOrEnterprise( concernedPersonOrEnterprise: String) :NoteBuilder{
        this.concernedPersonOrEnterprise=concernedPersonOrEnterprise
        return this
    }
    fun setDescription( description: String) :NoteBuilder{
        this.description=description
        return this
    }
    fun setTime( time: Calendar) :NoteBuilder{
        this.time=time
        return this
    }

    fun create(): Note {
        return Note(description, time)
    }


    fun create2() :Note{
        return Note(description,time,recallTime,priority,location ,concernedPersonOrEnterprise)
    }

} // End Builder

    //Note-Properties

    private  var description:String
    private  var time:Calendar
    private var recallTime:Calendar
    private var priority :Priority
    private var location :String
    private var concernedPersonOrEnterprise:String

//JSON-Properties for saving data

    private val JSON_DESCRIPTION="Description"
    private var JSON_TIME="Time"
    private  var JSON_PRIORITY="Priority"
    private val JSON_LOCATION="Location"
    private val JSON_CONCERNED="Concerned"
    private var JSON_RECALL="Recall"


init {
    this.description=""
    this.location=""
    this.concernedPersonOrEnterprise=""
    this.time=Calendar.getInstance()
    this.time.getTime()

    this.recallTime=time
    this.priority=Priority.Very_Important


}

    @Throws(JSONException::class)
    constructor(jsonObject: JSONObject):this() {

        this.location = jsonObject.getString(JSON_LOCATION)
        this.description = jsonObject.getString(JSON_DESCRIPTION)
        this.concernedPersonOrEnterprise = jsonObject.getString(JSON_CONCERNED)
        this.time=Calendar.getInstance()
        this.recallTime=Calendar.getInstance()
        val sdf=SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.GERMANY)
        JSON_TIME=this.time.getTime().toString()
         JSON_RECALL=this.recallTime.getTime().toString()
//       this.time.setTime(sdf.parse(jsonObject.getString(JSON_TIME)))
        this.priority.myPriority= jsonObject.getString(JSON_PRIORITY)
    }

constructor(description:String,  time:Calendar) :this(){
    this.description=description
    this.time=time

}


    constructor(description: String,time: Calendar,location: String,concernedPersonOrEnterprise: String,recallTime:Calendar,priority:Priority) :this(description,time){
        this.recallTime=recallTime
        this.priority=priority
        this.location=location
        this.concernedPersonOrEnterprise=concernedPersonOrEnterprise
    }
    constructor(description: String,time: Calendar,recallTime:Calendar,priority:Priority,location :String,concernedPersonOrEnterprise:String) :this(description,time){
        this.location=location

        this.recallTime=recallTime
        this.priority.myPriority=priority.myPriority
        this.concernedPersonOrEnterprise=concernedPersonOrEnterprise
    }

    public fun getDescription():String{
        return this.description
    }

    public fun getTime():Calendar{

        Log.e("getTimeInNote",this.time.time.toString())
        return this.time
    }

    fun getPriority():Priority{
        return this.priority
    }
    fun getLocation():String{
        return this.location
    }
    fun getConcerned():String{
        return this.concernedPersonOrEnterprise
    }
    fun getRecall():String{
        this.recallTime=Calendar.getInstance()
        val sdf=SimpleDateFormat("dd.MM.yyy HH:mm")
        val formatter=sdf.format(this.recallTime.time)
        return formatter
    }



    override fun toString(): String {
        return "Note(description='$description', time=$time, recallTime=$recallTime, priority='$priority', location='$location', concernedPersonOrEnterprise='$concernedPersonOrEnterprise')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Note

        if (description != other.description) return false
        if (time != other.time) return false
        if (recallTime != other.recallTime) return false
        if (priority != other.priority) return false
        if (location != other.location) return false
        if (concernedPersonOrEnterprise != other.concernedPersonOrEnterprise) return false

        return true
    }

    override fun hashCode(): Int {
        var result = description.hashCode()
        result = 31 * result + time.hashCode()
        result = 31 * result + recallTime.hashCode()
        result = 31 * result + priority.hashCode()
        result = 31 * result + location.hashCode()
        result = 31 * result + concernedPersonOrEnterprise.hashCode()
        return result
    }

}