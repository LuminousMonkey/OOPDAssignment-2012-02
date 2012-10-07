/* Written by Mike Robey and released on Feb 10th 2012.
   Provides students with the ability to read and write characters
   and integers to a text file without having to tangle with the
   complexity of the Java API for file inoput/output.

   In order to open a file students must declare and construct a TextFile
   object.  When doing this you must supplky the filename and the type of
   file access as arguments to the constructor. Both arguments must be of
   type String.  The file access is specified as a string containing one of:
   "r" opens the file for reading.  Reading will commence at the first byte
       of the file.
   "w" Opens the file for writing. Writing will commence at the first byte of
       the file. If the file already exists then whatever was in the file
       before the constructing the TextFile object will be lost.
   "a" Opens the file for appending. Same as writing erxcept that writing
       will commence at the end of the file. If the file already exists then 
       information written will be added onto the end of the pre-exisitng data.
       If the file does not exist then this is the same as the "w" access.

   Examples:

       TextFile inputFile, newFile, oldOutputFile;

       inputFile = new TextFile("Fred.dat", "r");
       newFile = new TextFile("Wilma, "w");
       oldOutputFile = new TextFile( "Barney, "a");

   After that all access to the file is done via the TextFile variable. The
   methods below are used to open and cl.ose the file and to read and write 
   to and from the file.

   Analyse the descriptions of each method below to work out which ones you 
   will need to use in your assignment.  Some you must use but others you 
   might not.  You will not need to use all of them.  One of the first things 
   your group should do is identify which ones are most likely the ones you 
   will use.
*/

import java.io.*;

public class TextFile
{
/* Normally I would describe what all these class fields do but given
   the whole idea is for students not to have to understand the internals
   of this class there is no point.
*/
private FileInputStream   is;
private InputStreamReader isr;
private FileOutputStream  os;
private PrintWriter       pw;
private String            fileName;
private boolean           fileStatus=false;
private boolean           append=false;
private boolean           reading=false;

public TextFile( String name, String mode)
  {
  fileName = new String(name);
  if ( mode.equals("r"))
     {
     reading = true;
     fileStatus = true;
     }
  else if ( mode.equals("w"))
     fileStatus = true;
  else if ( mode.equals("a"))
     {
     fileStatus = true;
     append = true;
     }
  else
     fileStatus = false;
  }


/* openFile: Opens the file so that the desired file input or oputput can occur.
   no input or output can occur until the file has been opened.  The method
   EXPORTs back a boolean variable which is set to true if the file was opened
   successfully and false otherwise.

   Example:

   openedOkay = inputFile.fileOpen()
   If ( openedOkay == true )
     etc
*/
public boolean openFile()
   {
   if ( fileStatus )
      {
      try {
          if ( reading )
             {
             is = new FileInputStream( fileName);
             isr = new InputStreamReader( is);
             }
          else
             {
             os = new FileOutputStream( fileName, append);
             pw = new PrintWriter( os, true);
             }
          }
      catch ( Exception e )
          {
          fileStatus = false;
          }
      }
   return fileStatus;
   }

/* endOfFile:  This method is used when reading a file to determine
whether or not the reading has reached the end of the file. The
method EXPORTs a boolean whish is set to true if the end of file
has been reached and false otherwise.

Example:

   while ( inputFile.endOfFile() == false )
      etc
*/
public boolean endOfFile()
   {
   boolean eof;
 
   if (( reading==true )&&(fileStatus==true))
      try {
          eof = isr.ready() == false;
          }
      catch ( Exception e)
          {
          eof = true;
          }
   else
      eof = true;

   return eof;
   }

/* clearRestOfLine:  Text files are arranged as lines of text. This method 
   is used when you wish to ignore the rest of the text on the current line 
   and move to the beginning of the next line in the input file.

   Example:

   inputFile.clearRestOfLine();

   The next attempt to read will be on the begining of the next line.

*/
public void clearRestOfLine()
   {
   char ch;

   if ( reading )
      {
      ch = readChar();
      while( ch != '\n' )
         ch = readChar();
      }

   }

/* readStatus:  EXPORTs a boolean which will be true if the file
   has been opened for reading and false otherwise.

   openForReading = inputFile.readStatus();

   if ( openForReading == true )
      etc
*/
public boolean readStatus()
   {
   boolean status;
   if ( reading )
      status = fileStatus;
   else
      status = false;

   return status;
   }

/* The printIt family of methods:
   All of the prinIt methods below write whatever the IMPORT is
   to the file. Note if the file has been opened for reading then 
   calls to any of the printIt methods will have unpredictable
   results.

   Examples:

      newFile.printIt('P');
      newFile.printIt("Windows broken");
      newFile.printIt(6152);
*/
      
public void printIt( char thing)
   {
   pw.print(thing);
   }

public void printIt( String thing)
   {
   pw.print(thing);
   }

public void printIt( int thing)
   {
   pw.print(thing);
   }

public void printIt( double thing)
   {
   pw.print(thing);
   }

/* newLine:  When writing to a file this method moves the file pointer 
   onto a new line.  This method would typically be used when moving from 
   writing one log entry to the next.

   Example:
      newFile.newLine();

*/
public void newLine()
   {
   pw.println();
   }

/* readChar:  This method reads one characer from the input file. If the 
   file cannot be read the method EXPORTs a blank space, otherwise it
   EXPORTs the character read from the file.

   Example:

   char nextChar;

   nextChar = inputFile.readChar();
*/
public char readChar()
   {
   char ch = ' ';

   if (( reading ) && ( fileStatus ))
      {
      try {
          ch = (char)isr.read();
          }
      catch( Exception e)
          {
          fileStatus = false;
          ch = ' ';
          }
      }

   return ch;
   }

/* readInt:  This method reads one integer from the input file. If the 
   file cannot be read or the next thing in the file is not an integer 
   then the method EXPORTs a value of -1, otherwise it EXPORTs the integer 
   read from the file. Note it reads an integer not one digit of an integer.

   Example:

   int nextNumber;

   nextNumber = inputFile.readInt();
*/
public int readInt()
   {
   int number = -1;
   char ch;
   String buffer;

   buffer = new String();
   ch = readChar();
   if ( ( ch == '-' ) || (( ch >= '0') && ( ch <= '9'))) 
      {
      buffer += ch;
      ch = readChar();
      while (( ch >= '0' ) && ( ch <= '9' ))
         {
         buffer += ch;
         ch = readChar();
         }
      number = Integer.parseInt( buffer);
      }
   return number;
   }

/* rewind:  This method is only used when reading a file.  It resets
   the reading to the beginning of the file. It is used when the file
   needs to be read mutliple times.

   Example:
       inputFile.rewind();

*/
public void rewind()
   {
   if ( reading  && fileStatus )
      {
      closeFile();
      openFile();
      }
   }

/* closeFile:  This method is used to close the file whether it has been 
   opened for reading, writing or appending.  All files MUST be closed when
   finished with. Failure to close a file being written to can result
   in not all the data being written to the file. Marks will be deducted
   for files of any kind being left open.

   Examples:

        inputFile.closeFile();
        newFile.closeFile();
        oldOutputFile.closeFile();
*/
public void closeFile()
  {
  if ( fileStatus )
     {
     try {
         if ( reading )
            is.close();
         else
            pw.close();
         }
     catch( IOException e )
         {
         fileStatus = false;
         }
     }
  }

}
