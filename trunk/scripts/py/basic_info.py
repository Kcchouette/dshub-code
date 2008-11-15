#   Basic  Information 0.1a
#   By Toast 08-11-15
#
#   Prints out Hostname and PID for process
#   Simple script (example for DSHub)
import sys, socket, os, sys, platform
print "\n"
print "Basic Info:","\n"
hostname = socket.gethostname()
print "Hostname:", hostname
print 'Operating System:', platform.system(), platform.release()
pathname = os.path.dirname(sys.argv[0])
print 'Working Directory:', sys.argv[0] ,os.path.abspath(pathname)
print 'Parent Process ID:', os.getpid()
print 'Real User ID:', os.getuid()
print "\n"
print "Saving to information to basic_info.log"
f = open('basic_info.log', 'w')
print >> f, "Basic Info:","\n"
print >> f, "Hostname:", hostname
print >> f, 'Operating System:', platform.system(), platform.release()
print >> f, 'Working Directory:', sys.argv[0] ,os.path.abspath(pathname)
print >> f, 'Parent Process ID:', os.getpid()
print >> f, 'Real User ID:', os.getuid()
f.close()