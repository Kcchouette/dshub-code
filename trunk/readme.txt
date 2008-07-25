DSHUB readme.txt 
*** IMPORTANT NOTE : THIS IS A BASIC README FOR FULL MANUAL PLEASE DOWNLOAD IT FROM http://www.death-squad.ro/dshub ***

1. Installation
Installation is OS dependent. If you want to install DSHub I presume that you have the necessary OS knowledge to handle possible situations. If you don't , consider twice using this software. I also assume that you are familiar with DC++ clients and hubs, and have minimal knowledge of Internet. Again, if you don't, consider again using this software and don't just send mail to admins asking why program doesn't work, or this kind of problems. DSHub is a software for people that know what they want and what they do.
1.1 Linux installation

For a Linux OS installation, i will provide an example. Let's consider your user name is user, e.g. your home dirrectory is /home/user. Also consider the installation in this directory.

First you need the JRE [ Java Runtime Environment ] version 1.6 the least. Most Linux distributions come with built-in Java Interpreter from GNU. Unless you know what you're doing and the GNU interpreter is JRE 1.5 compatibile, install a Sun Microsystems Inc. JRE.

That is System-based installation and its not covered in this manual.

[ Ussualy you can get it from http://java.sun.com or use a package manager like synaptic [ debian ], yum [ fedora, red hat] ,yast [ suse ] or emerge [ gentoo ]].

After you have java installed, you can start using the software.

Create a directory in your home, named at your please, and copy DSHub files into it.

As DSHub has GUI now, you can run it by double clicking the JAR file. The gui appears, and it can be restored via !gui from client ( if hidden via HideMe button).

If aren't running desktop environment, you can ren DSHub via console, like before.

 

To run the program from console just cd to that directory and run command

java -jar DSHub.jar

This will launch the program and also open up program console, where the hub accepts commands.

Example:

$ java -jar DSHub.jar

Initializing DSHub Beta ...
Done. Parsing to Command Mode.Help for info,quit to quit.
Server created. Listening on port 411.
Start Time: 19.04.2007 14:04.045
>
At this moment the prompt ">" indicates that hub is running and waiting commands.

You need root account to listen to any port <1024. For other port simple user is enough.

To run hub in background and to remain there even if you logout [ similar to deamon mode] i googled the net and found this solution:

screen -A -m -d -S DSHub java -jar DSHub.jar

This command creates a "screen" with a virtual console where hub will run. To restore the screen use

screen -r DSHub

and to detach from it again type Ctrl+a, d

[ DSHub is being closed by quit command in its console, you can see in first line that it prints].
1.2 Windows Installation

For a windows installation, a simple installer is provided, which will make most things simpler. After youre done , run dshub.exe and get started.

Otherwise, first you need the JRE [ Java Runtime Environment ] version 1.6 the least. Sun provides the JRE downloadable from their site http://java.sun.com  Java installation is not covered in this manual.


If you are using the installer , skip to running part.
If you have the zip archive, copy DSHub files ( unzipped )  into your new directory. Make sure that java.exe [ aka the Java interpreter] is in your path variable so it can be run from any directory in your pc.

As DSHub has GUI now, you can run it by double clicking the JAR file. The gui appears, and it can be restored via !gui from client ( if hidden via HideMe button).

If aren't running desktop environment, you can run DSHub via console, like before.

To run the program in console mode just cd to that directory and run command

java -jar DSHub.jar

This will launch the program and also open up program console, where the hub accepts commands.

Example:

> java -jar DSHub.jar

Initializing DSHub Beta ...
Done. Parsing to Command Mode.Help for info,quit to quit.
Server created. Listening on port 411.
Start Time: 19.04.2007 14:04.045
>
At this moment the prompt ">" indicates that hub is running and waiting commands.

[ DSHub is being closed by quit command in its console, you can see in first line that it prints].

If you use 
javaw -jar DSHub.jar 
you won't get any console, but the GUI will pop out only ( note that you need a graphical interface for this ).

2. Hub Commands
DSHub accepts commands from starting console. Whether your OS is Linux , Mac , Windows or any other, commands are same.
2.1. Help
First command you should know is help. Just type help at the prompt[">"] and you will see available commands. DSHub is self explanatory so here will be just a brief of available commands. Any man can handle himself in DSHub if he has a minimal knowledge of shell.
2.2. Quit
quit just exits hub.
2.3. Cfg
Cfg modifies hub variables. Cfg list to see all, cfg <varname> <newval> to change a variable.
2.4. Stats
Stats command gives info about PC hub runs on, and current hub statistics.

Other commands are software built-in and will be added later in this readme, please consult the Manual, you can get it from http://www.death-squad.ro/dshub