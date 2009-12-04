abstract class SecureController {

    def beforeInterceptor = [ action: this.&auth,
                              except: [ 'handleLogin', 'login',
                                        'handleRegistration', 'register'
                              ]
    ]
    
 
    private auth() {
        if(!session.user) {
            redirect(controller:'user', action: 'login')
            return false
        }
    }
}