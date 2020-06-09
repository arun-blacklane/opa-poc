package myapi.policy
import data.myapi.acl
import input
default allow = false
allow {
        method = acl[input.user]
        method[_] == input.method
}
whocan[user] {
        method = acl[user]
        method[_] == input.method
}
