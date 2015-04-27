package hello

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class BookController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }
    /**
     * 修改查询条件
     * @param max
     * @return
     */
    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        if (params.uuid) {
            def criteria = {
                and {
                    like("uuid", "%" + params.uuid + "%");
                }
            }
            [bookInstanceList: Book.createCriteria().list(params, criteria), bookInstanceTotal: Book.createCriteria().count(criteria), params: params]
        } else {
            [bookInstanceList: Book.list(params), bookInstanceTotal: Book.count(), params: params]
        }
    }

    def create() {
        [bookInstance: new Book(params), catalogs: Catalog.list()]
    }

    def addCatalogAmount = { catId ->
        Long id = catId;
        def cat = Catalog.get(id)
        cat.amount++
        cat.save(flush: true)
    }

    def save() {
        def bookInstance = new Book(params)
        if (bookInstance.save(flush: true)) {
            //保存成功  catalog.amount+1
            def catalog = bookInstance.catalog;
            if (catalog) {
                def cat = Catalog.get(catalog.id)
                cat.amount++;
                log.info("add catalog amount result:" + cat.save(flush: true));
            }
            flash.message = message(code: 'default.created.message', args: [message(code: 'book.label', default: 'Book'), bookInstance.id])
            redirect(action: "show", id: bookInstance.id)
        } else {
            log.error("保存失败");
            flash.message = message(code: '保存失败')
            render(view: 'create', model: [bookInstance: bookInstance]);
        }

    }

    def show(Long id) {
        def bookInstance = Book.get(id)
        if (!bookInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'book.label', default: 'Book'), id])
            redirect(action: "list")
            return
        }

        [bookInstance: bookInstance]
    }

    def edit(Long id) {
        def bookInstance = Book.get(id)
        if (!bookInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'book.label', default: 'Book'), id])
            redirect(action: "list")
            return
        }

        [bookInstance: bookInstance]
    }

    def update(Long id, Long version) {
        def bookInstance = Book.get(id)
        def oldCataId = bookInstance?.catalog?.id
        if (!bookInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'book.label', default: 'Book'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (bookInstance.version > version) {
                bookInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'book.label', default: 'Book')] as Object[],
                        "Another user has updated this Book while you were editing")
                render(view: "edit", model: [bookInstance: bookInstance])
                return
            }
        }

        bookInstance.properties = params
        if (bookInstance.save(flush: true)) {
            if (!oldCataId.equals(bookInstance.catalog.id)) {
                def updateCatalogAmount = {
                    if (oldCataId) {//如果原有存在
                        def OldCatalog = Catalog.get(oldCataId);
                        OldCatalog.amount--;
                        OldCatalog.save(flush: true);
                    }
                    addCatalogAmount(bookInstance.catalog.id);
                }
                log.info("变更了catalog，更新完成:" + updateCatalogAmount());
            }
        } else {
            render(view: "edit", model: [bookInstance: bookInstance])
            return
        }


        flash.message = message(code: 'default.updated.message', args: [message(code: 'book.label', default: 'Book'), bookInstance.id])
        redirect(action: "show", id: bookInstance.id)
    }

    def delete(Long id) {
        def bookInstance = Book.get(id)
        if (!bookInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'book.label', default: 'Book'), id])
            redirect(action: "list")
            return
        }

        try {
            bookInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'book.label', default: 'Book'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'book.label', default: 'Book'), id])
            redirect(action: "show", id: id)
        }
    }

    def queryLast() {
        //查询size最大的Book
        def lastBook = Book.list(sort: "size", order: "desc", offset: 0, max: 1);
        println(lastBook.toString());
        render lastBook as JSON;
    }
}