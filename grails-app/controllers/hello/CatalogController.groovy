package hello

import grails.converters.JSON
import groovy.sql.Sql
import org.json.simple.JSONObject
import org.springframework.dao.DataIntegrityViolationException

class CatalogController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def dataSource;

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [catalogInstanceList: Catalog.list(params), catalogInstanceTotal: Catalog.count()]
    }

    def create() {
        [catalogInstance: new Catalog(params)]
    }

    def save() {
        def catalogInstance = new Catalog(params)
        catalogInstance.amount = 0;//创建时默认为0
        if (!catalogInstance.save(flush: true)) {
            render(view: "create", model: [catalogInstance: catalogInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'catalog.label', default: 'Catalog'), catalogInstance.id])
        redirect(action: "show", id: catalogInstance.id)
    }

    def show(Long id) {
        def catalogInstance = Catalog.get(id)
        if (!catalogInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'catalog.label', default: 'Catalog'), id])
            redirect(action: "list")
            return
        }

        [catalogInstance: catalogInstance]
    }

    def edit(Long id) {
        def catalogInstance = Catalog.get(id)
        //计算当前book值
        def amount = Book.countByCatalog(catalogInstance);
        catalogInstance.amount = amount;
        if (!catalogInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'catalog.label', default: 'Catalog'), id])
            redirect(action: "list")
            return
        }

        [catalogInstance: catalogInstance]
    }

    def update(Long id, Long version) {
        def catalogInstance = Catalog.get(id)
        if (!catalogInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'catalog.label', default: 'Catalog'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (catalogInstance.version > version) {
                catalogInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'catalog.label', default: 'Catalog')] as Object[],
                        "Another user has updated this Catalog while you were editing")
                render(view: "edit", model: [catalogInstance: catalogInstance])
                return
            }
        }

        catalogInstance.properties = params
        def amount = Book.countByCatalog(catalogInstance);
        catalogInstance.amount = amount;
        if (!catalogInstance.save(flush: true)) {
            render(view: "edit", model: [catalogInstance: catalogInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'catalog.label', default: 'Catalog'), catalogInstance.id])
        redirect(action: "show", id: catalogInstance.id)
    }

    def delete(Long id) {
        def catalogInstance = Catalog.get(id)
        if (!catalogInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'catalog.label', default: 'Catalog'), id])
            redirect(action: "list")
            return
        }

        try {
            catalogInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'catalog.label', default: 'Catalog'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'catalog.label', default: 'Catalog'), id])
            redirect(action: "show", id: id)
        }
    }
    /**
     * 测试查询
     */
    def queryTest() {
        def db = new Sql(dataSource);
        String sql = "SELECT " +
                "b.id," +
                "b.size," +
                "b.title," +
                "c.id," +
                "c. NAME," +
                "c.amount " +
                "FROM " +
                "book_test b " +
                "LEFT JOIN catalog c ON b.catalog_id = c.id;";
        List rows = db.rows(sql);
        for (def i in rows) {
            println(i);
        }
        render rows as JSON;
    }

    def fileUpload(){
        def file = request.getFile('uploadFile');
        JSONObject json = new JSONObject();
        if (file){
            def fileName = file.originalFilename;
            File f = new File("C:\\test\\"+fileName)
            file.transferTo(f);
            json.put("fileName",fileName);
        }
        render json;
    }

    def fileDownload(){
        def fileName = params.fileName
        if (fileName){
            def file = new File("C:\\test\\"+fileName);
            if (!file.exists()){
                log.error("文件不存在")
                return
            }
            response.setHeader("Content-disposition", "attachment; filename="+fileName)
            response.contentType = "application/x-rarx-rar-compressed"
            response.setCharacterEncoding("utf-8")
            response.outputStream << file.newInputStream()
        }
    }
}
