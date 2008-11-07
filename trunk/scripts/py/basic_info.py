#   Basic  Information 0.1
#   By Toast 08-10-02
#
#   Prints out Hostname and PID for process
#   Simple script (example for DSHub)
import sys, socket, os, sys
print "\n"
print "Basic Info:"
print "\n"
hostname = socket.gethostname()
print "Hostname:", hostname
print 'DSHub PID:', os.getpid()
print "\n"