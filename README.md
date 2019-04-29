# debian-tricks
Shortcuts taken along the way

---

## Autorun VNC Server on boot
1. Create a new file (follow the template in this project)
```
$ /etc/systemd/system/vnc-server1.service
```
2. Ensure file is given sufficient permission
```
$ chmod 664 vnc-service1.service
```
3. Update system manager about new file (also run this if file is updated)
```
$ systemctl daemon-reload
```
4. Enable the new file as a service
```
$ systemctl enable vnc-service1.service
```
5. Reboot
```
$ sudo shutdown -r now
```
6. Check that service is running
```
$ systemctl | grep vnc
```
7. Good reading resource for better understanding: 
https://www.digitalocean.com/community/tutorials/how-to-use-systemctl-to-manage-systemd-services-and-units

---

## Remove system services
```
$ systemctl stop [servicename]
$ systemctl disable [servicename]
($ rm /etc/systemd/system/[servicename] #if file has not been removed yet)
$ systemctl daemon-reload
$ systemctl reset-failed
($ systemctl | grep [servicename] #verify service has been removed)
```

---

## Create bash command aliases
1. Create/edit file command alias file. I like to use nano editor.
```
$ nano ~/.bash_aliases
```
2. Create desired alias by coding in the file:
```
# Create an "up" command to go to parent directory from pwd
alias up='cd ..'
```
3. Install the new bash command
```
$ source ~/.bashrc
```
4. Test it out!
```
$ up
```

---

## Set Octave to use GNU plot (to solve the no plot display issue)
1. Locate the Octave config file.
```
$ cd /usr/share/octave/<version.number>/m/startup
$ sudo nano octaverc
```
2. Paste the following command at the top of the config script to force the usage of GNU plot:
```
graphics_toolkit("gnuplot")
```
3. Save the file and reload Octave. Try it with some simple plots, the figures should be showing now.

---

## Check Disk Space and Delete files

A few of the servers in my HDP frequently run into problems, due to a full disk. While I have not figure out a solution to prevent this, the fastest remedy is to delete teh files that are taking up the most disk space: the log files.

1. SSH into the problematic server. Find out how much space you have in the current drive.
```
#check space in current drive
$ df -h .
#check space for all drives
$ df -h
```
2. Go to the root directory of the drive. Check space consumption for each child directory.
```
#print in human readable form, depth 1.
$ du -h --max-depth=1
#for easier inspection, egrep the output to filter for directories taking up space in Gigabytes, sorted in descending order.
$ du -h --max-depth=1 | egrep '^[0-9.]+G' | sort -r
```
3. Recursively perform this analysis on child directories to find the culprit that is taking up the most space. In my case, the hive logs in /var/log/hive are causing the problems.
```
#show file size in current directory
$ du -sh -- * 
#similar to previous steps, identify teh biggest problematic file
$ du -sh -- * | egrep '^[0-9.]+G' | sort -r
```
4. use find command with regex to filter for files to be deleted.
```
$ find -type f -name 'hiveserver2.log.2019-*' | sort
#confirm and delete the files
$ find -type f -name 'hiveserver2.log.2019-*' -delete
```
