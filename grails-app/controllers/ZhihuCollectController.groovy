import org.springframework.dao.DataIntegrityViolationException

class ZhihuCollectController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [zhihuCollectInstanceList: ZhihuCollect.list(params), zhihuCollectInstanceTotal: ZhihuCollect.count()]
    }

    def create() {
        [zhihuCollectInstance: new ZhihuCollect(params)]
    }

    def save() {
        def zhihuCollectInstance = new ZhihuCollect(params)
        zhihuCollectInstance.dateCreated = new Date()
        if (!zhihuCollectInstance.save(flush: true)) {
            render(view: "create", model: [zhihuCollectInstance: zhihuCollectInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'zhihuCollect.label', default: 'ZhihuCollect'), zhihuCollectInstance.id])
        redirect(action: "show", id: zhihuCollectInstance.id)
    }

    def show(Long id) {
        def zhihuCollectInstance = ZhihuCollect.get(id)
        if (!zhihuCollectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'zhihuCollect.label', default: 'ZhihuCollect'), id])
            redirect(action: "list")
            return
        }

        [zhihuCollectInstance: zhihuCollectInstance]
    }

    def edit(Long id) {
        def zhihuCollectInstance = ZhihuCollect.get(id)
        if (!zhihuCollectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'zhihuCollect.label', default: 'ZhihuCollect'), id])
            redirect(action: "list")
            return
        }

        [zhihuCollectInstance: zhihuCollectInstance]
    }

    def update(Long id, Long version) {
        def zhihuCollectInstance = ZhihuCollect.get(id)
        if (!zhihuCollectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'zhihuCollect.label', default: 'ZhihuCollect'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (zhihuCollectInstance.version > version) {
                zhihuCollectInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'zhihuCollect.label', default: 'ZhihuCollect')] as Object[],
                        "Another user has updated this ZhihuCollect while you were editing")
                render(view: "edit", model: [zhihuCollectInstance: zhihuCollectInstance])
                return
            }
        }

        zhihuCollectInstance.properties = params

        if (!zhihuCollectInstance.save(flush: true)) {
            render(view: "edit", model: [zhihuCollectInstance: zhihuCollectInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'zhihuCollect.label', default: 'ZhihuCollect'), zhihuCollectInstance.id])
        redirect(action: "show", id: zhihuCollectInstance.id)
    }

    def delete(Long id) {
        def zhihuCollectInstance = ZhihuCollect.get(id)
        if (!zhihuCollectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'zhihuCollect.label', default: 'ZhihuCollect'), id])
            redirect(action: "list")
            return
        }

        try {
            zhihuCollectInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'zhihuCollect.label', default: 'ZhihuCollect'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'zhihuCollect.label', default: 'ZhihuCollect'), id])
            redirect(action: "show", id: id)
        }
    }
}
