#   Basic  Information 0.1a
#   By Toast 08-11-15
#
#   Prints out Hostname and PID for process
#   Simple script (example for DSHub)
import sys, socket, os, sys, platform
print "\n"
print "Basic Info:"
print "\n"
hostname = socket.gethostname()
print "Hostname:", hostname
print 'Operating System:', platform.system(), platform.release()
print 'Parent Process ID:', os.getpid()
print 'Real User ID:', os.getuid()
print "\n"