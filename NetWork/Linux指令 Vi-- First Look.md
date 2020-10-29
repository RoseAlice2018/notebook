## Linux指令 Vi-- First Look

**The article introuduces the Vi editor, presents the basic commands necessary to do a simple editing job.**
**1： The different modes of the vi editor**
	

 - command mode

 - text input mode

**2.memory buffers**
**3.The process of opening a file for editing**
	

- Starting Vi
  : type vi , press return , and type the name of the file
  (if the file already exists , the vi will open it, and show its first page on the screen,
  else clearn the window and show the blanke page of the file.



**4.saving a file**
**5.quiting vi**

**The change mode keys:**
| Key  | Operation                                                    |
| ---- | ------------------------------------------------------------ |
| i    | Place the text you enter before the character that the cursor is on |
| I    | Place the text you enter at the begining of the current line |
| a    | Place the text you enter after the charater that the cursor is on |
| A    | Places the text you enter after the last charcter of the current line |
| o    | Open a blank line below the current line, and put the cursor at the beginning of the new line |
| O    | Open a blank line above the current line and place the cursor at the beginng of the new line |
**Correcting text keys:
(only in command mode)**
| Key  | Operation                                             |
| ---- | ----------------------------------------------------- |
| x    | delete the character specified by the cursor position |
| dd   | delete the line specified by the cursor position      |
| u    | Undose the most recent change                         |
| U    | Undoes all the changes on the current line            |
| r    | Replaces the character that the cursor is on          |
| R    | Replace characters starting from the cursor position  |
| .    | repeats the last changes                              |
**The quit commands:**
With the exception of the ZZ command , these commands start with,: , and you must end a commandwith Return
| Key  | Operation                                                 |
| ---- | --------------------------------------------------------- |
| wq   | Writes the contents of the buffer and quits the vi editor |
| w    | Writes the contents of the buffer but stays in the editor |
| q    | quits the editor                                          |
| q!   | quits the editor and abandons the contents of the buffer  |
| ZZ   | wq                                                        |
The search command :
These keys allow you to search forward or backward in your file for a pattern
| Key  | Operation                                 |
| ---- | ----------------------------------------- |
| /    | Searches forward for a specified pattern  |
| ?    | Searches backward for a specified pattern |