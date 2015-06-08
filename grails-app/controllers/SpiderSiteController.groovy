import org.springframework.dao.DataIntegrityViolationException

class SpiderSiteController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [spiderSiteInstanceList: SpiderSite.list(params), spiderSiteInstanceTotal: SpiderSite.count()]
    }

    def create() {
        [spiderSiteInstance: new SpiderSite(params)]
    }

    def save() {
        def spiderSiteInstance = new SpiderSite(params)
        if (!spiderSiteInstance.save(flush: true)) {
            render(view: "create", model: [spiderSiteInstance: spiderSiteInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'spiderSite.label', default: 'SpiderSite'), spiderSiteInstance.id])
        redirect(action: "show", id: spiderSiteInstance.id)
    }

    def show(Long id) {
        def spiderSiteInstance = SpiderSite.get(id)
        if (!spiderSiteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'spiderSite.label', default: 'SpiderSite'), id])
            redirect(action: "list")
            return
        }

        [spiderSiteInstance: spiderSiteInstance]
    }

    def edit(Long id) {
        def spiderSiteInstance = SpiderSite.get(id)
        if (!spiderSiteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'spiderSite.label', default: 'SpiderSite'), id])
            redirect(action: "list")
            return
        }

        [spiderSiteInstance: spiderSiteInstance]
    }

    def update(Long id, Long version) {
        def spiderSiteInstance = SpiderSite.get(id)
        if (!spiderSiteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'spiderSite.label', default: 'SpiderSite'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (spiderSiteInstance.version > version) {
                spiderSiteInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'spiderSite.label', default: 'SpiderSite')] as Object[],
                        "Another user has updated this SpiderSite while you were editing")
                render(view: "edit", model: [spiderSiteInstance: spiderSiteInstance])
                return
            }
        }

        spiderSiteInstance.properties = params

        if (!spiderSiteInstance.save(flush: true)) {
            render(view: "edit", model: [spiderSiteInstance: spiderSiteInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'spiderSite.label', default: 'SpiderSite'), spiderSiteInstance.id])
        redirect(action: "show", id: spiderSiteInstance.id)
    }

    def delete(Long id) {
        def spiderSiteInstance = SpiderSite.get(id)
        if (!spiderSiteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'spiderSite.label', default: 'SpiderSite'), id])
            redirect(action: "list")
            return
        }

        try {
            spiderSiteInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'spiderSite.label', default: 'SpiderSite'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'spiderSite.label', default: 'SpiderSite'), id])
            redirect(action: "show", id: id)
        }
    }
}
