Azei
====

Azei allows you to interact with Azureus by email.
[![Build Status](https://secure.travis-ci.org/sonata82/azei.png)](https://secure.travis-ci.org/sonata82/azei)

How it works
------------

The plugin checks a POP3 account for emails with a configurable subject. The 
commands found in the email are executed and the result is emailed back to you.

Installation
------------

see INSTALL

License
-------

see LICENSE

Usage
-----

The following commands are available:

    LIST <state>
    
will return the list of downloads or seeds or both
    
    START <state> <index>
    
start the download or seed with the given index
    
    STOP <state> <index>

stop the download or seed with the given index
    
    PAUSE <state> <index>
    
pause the download or seed with the given index

    SET <state> <index> <var> <option> <integer>

sets the given var and option to integer

state = `DOWNLOAD, SEED, ALL`  
index = index of a download  
var = `LIMIT` (only var supported so far)  
option = `DOWNLOAD, UPLOAD`  
integer = valid integer

any other input is ignored.

See also
--------

[Azureus on SourceForge](http://azureus.sourceforge.net/)