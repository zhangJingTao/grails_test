package hello

class Person {

    static constraints = {
    }
    static hasMany = [addresses:Address]
    static mapping = {
        addresses joinTable: [name : 'address',key:'person_id']
    }
}
