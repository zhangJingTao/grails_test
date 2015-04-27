package hello
/**
 * Grails CRUD 测试用类
 * @author Zhang Jingtao
 */
class Book {

    static constraints = {
        uuid(nullable: false)
        size(max: 9999999)
    }
    static mapping = {
        table 'book_test'
    }

    String uuid;
    String title;
    Integer size;
    Catalog catalog;

    String toString() {
        return "${title}---${size}words";
    }
}
