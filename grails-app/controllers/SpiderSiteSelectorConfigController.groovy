import org.springframework.dao.DataIntegrityViolationException

class SpiderSiteSelectorConfigController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [spiderSiteSelectorConfigInstanceList: SpiderSiteSelectorConfig.list(params), spiderSiteSelectorConfigInstanceTotal: SpiderSiteSelectorConfig.count()]
    }

    def create() {
        [spiderSiteSelectorConfigInstance: new SpiderSiteSelectorConfig(params)]
    }

    def save() {
        def spiderSiteSelectorConfigInstance = new SpiderSiteSelectorConfig(params)
        if (!spiderSiteSelectorConfigInstance.save(flush: true)) {
            render(view: "create", model: [spiderSiteSelectorConfigInstance: spiderSiteSelectorConfigInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'spiderSiteSelectorConfig.label', default: 'SpiderSiteSelectorConfig'), spiderSiteSelectorConfigInstance.id])
        redirect(action: "show", id: spiderSiteSelectorConfigInstance.id)
    }

    def show(Long id) {
        def spiderSiteSelectorConfigInstance = SpiderSiteSelectorConfig.get(id)
        if (!spiderSiteSelectorConfigInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'spiderSiteSelectorConfig.label', default: 'SpiderSiteSelectorConfig'), id])
            redirect(action: "list")
            return
        }

        [spiderSiteSelectorConfigInstance: spiderSiteSelectorConfigInstance]
    }

    def edit(Long id) {
        def spiderSiteSelectorConfigInstance = SpiderSiteSelectorConfig.get(id)
        if (!spiderSiteSelectorConfigInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'spiderSiteSelectorConfig.label', default: 'SpiderSiteSelectorConfig'), id])
            redirect(action: "list")
            return
        }

        [spiderSiteSelectorConfigInstance: spiderSiteSelectorConfigInstance]
    }

    def update(Long id, Long version) {
        def spiderSiteSelectorConfigInstance = SpiderSiteSelectorConfig.get(id)
        if (!spiderSiteSelectorConfigInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'spiderSiteSelectorConfig.label', default: 'SpiderSiteSelectorConfig'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (spiderSiteSelectorConfigInstance.version > version) {
                spiderSiteSelectorConfigInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'spiderSiteSelectorConfig.label', default: 'SpiderSiteSelectorConfig')] as Object[],
                        "Another user has updated this SpiderSiteSelectorConfig while you were editing")
                render(view: "edit", model: [spiderSiteSelectorConfigInstance: spiderSiteSelectorConfigInstance])
                return
            }
        }

        spiderSiteSelectorConfigInstance.properties = params

        if (!spiderSiteSelectorConfigInstance.save(flush: true)) {
            render(view: "edit", model: [spiderSiteSelectorConfigInstance: spiderSiteSelectorConfigInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'spiderSiteSelectorConfig.label', default: 'SpiderSiteSelectorConfig'), spiderSiteSelectorConfigInstance.id])
        redirect(action: "show", id: spiderSiteSelectorConfigInstance.id)
    }

    def delete(Long id) {
        def spiderSiteSelectorConfigInstance = SpiderSiteSelectorConfig.get(id)
        if (!spiderSiteSelectorConfigInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'spiderSiteSelectorConfig.label', default: 'SpiderSiteSelectorConfig'), id])
            redirect(action: "list")
            return
        }

        try {
            spiderSiteSelectorConfigInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'spiderSiteSelectorConfig.label', default: 'SpiderSiteSelectorConfig'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'spiderSiteSelectorConfig.label', default: 'SpiderSiteSelectorConfig'), id])
            redirect(action: "show", id: id)
        }
    }
}
