class BootStrap {

    def init = { servletContext ->
        new User(username:"admin",password:"admin",enabled:true).save()
        new User(username:"test",password:"test",enabled:true).save()
    }
    def destroy = {
    }
} 