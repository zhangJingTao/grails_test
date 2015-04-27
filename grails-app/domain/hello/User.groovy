package hello

class User {

    static constraints = {
    }
    static hasMany = [groups:Groups]
    static belongsTo = Groups
    static mapping = {
        groups column: 'Group_Groups_Id'
    }
}
