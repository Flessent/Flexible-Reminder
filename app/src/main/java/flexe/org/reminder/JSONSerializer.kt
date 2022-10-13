package flexe.org.reminder

import android.content.Context
import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONTokener
import java.io.*
import java.nio.file.FileSystemNotFoundException

class JSONSerializer(private val fileName:String,private val context: Context) {
/*
Context-class allows access to application-specific resources and classes,as well as up-calls for
 application-level operations such as launching activities,broadcasting and receiving intents, etc.
*
* */
    @Throws(IOException::class,JSONException::class)
    fun save(notes:ArrayList<Note>){
    //JSONArray creates an Array of JSON-Object and each JSON-Object is a JSON-representation our object

    val jsonArray=JSONArray()
        for (note in notes) {
            // add a note-object in JSON-ARRAY and its conversion into JSON-Object
            jsonArray.put(Note.NoteBuilder().convertToJSON(note))
        Log.i("Info saved Note", note.getPriority().myPriority)
        }
        var writer:Writer?=null // for writing to character streams.

        try {
            /*The openFileOutput-function from Context-methods open a private file associated with
             this Context's application package for writing. Creates the file if it doesn't already exist
             and returns a FileOutputStream(FileOutputStream is an output stream for writing data to a File).*/
            //InputStream is = getAssets().open("yourfilename.json");

            val fileOutputStream=context.openFileOutput(fileName,Context.MODE_PRIVATE)
            //An OutputStreamWriter is a bridge from character streams to byte streams: Characters written to it are encoded into bytes using a specified
            writer=OutputStreamWriter(fileOutputStream)
            writer.write(jsonArray.toString()) //convert jsonArray into string and write it in file
        } finally {
            if (writer!=null){
                writer.close()
            }
        }
    }

    @Throws(IOException::class,JSONException::class)
    fun loadSavedInformations():ArrayList<Note>{
        val noteList=ArrayList<Note>()
        var reader:BufferedReader?=null
        try {
            //context.openFileInput opens a private file associated with this Context's application package for reading and returns a FileInputStream.
            val fileInputStream =context.openFileInput(fileName)
            reader = BufferedReader(InputStreamReader(fileInputStream))
             val jsonString=StringBuilder() //creates a mutable sequence of characters
            for (line in reader.readLine()){
                jsonString.append(line)
            }
            //JSONTokener parses a JSON  encoded string into the corresponding object.
            val jsonArray = JSONTokener(jsonString.toString()).nextValue() as JSONArray
            for (i in 0 until jsonArray.length()) {
                noteList.add(Note(jsonArray.getJSONObject(i)))
Log.i("Load Data", jsonArray.getJSONObject(i).toString())

            }
        }catch (e:FileNotFoundException){
            Log.e("Error",e.message.toString())

        } finally {
           // reader!!.close()

        }
        return noteList
    }


}