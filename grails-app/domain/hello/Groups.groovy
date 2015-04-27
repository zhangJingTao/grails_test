package hello

class Groups {

    static constraints = {
    }
    static hasMany = [user:User]
    static mapping = {
        user column:'Group_User_Id'
    }
}
