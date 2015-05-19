package hello

class HelloController {

    def helloService

    def index() {
        helloService.methodInvoke()
    }
}
