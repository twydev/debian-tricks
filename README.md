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
