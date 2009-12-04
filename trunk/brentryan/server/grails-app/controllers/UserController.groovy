import grails.converters.JSON;

class UserController extends SecureController {

    def login = {
        log.debug("Checking session for user")
        if(session.user) {
            log.debug("User found in session")
            render "success"
        } else {
            log.debug("User was not in session")
            render "failure"
        }
    }

    def list = {
        def userList = User.list()
        render(builder: 'json') {
            totalCount(userList.size())
            users {
                for(u in userList) {
                  user(username: u.username, password: u.password, enabled: u.enabled)
                }
            }
        }
    }
    
    def logout = {
        log.debug("Logging out session for user ${session?.user?.username} from User agent: " + request.getHeader("User-Agent"))        
        session.invalidate()                    
        render "success"
    }
    
    def handleLogin = {
        if(params == null || params.username == null || params.password == null) {
            log.error("Invalid params passed to handleLogin")
            render(builder: 'json') {
                success(false)
                errors {
                    id("loginError")
                    msg("Not enough arguments to login")
                }
            }                
        } else {
            def user = User.findByUsername( params.username )
            if(user) {
                if(user.password == params.password) {
                    session.user = user
                    render(builder: 'json') {
                        success(true)
                        data {
                            resonseMsg("The user is ${params.username}")
                        }
                    }
                } else {
                    render(builder: 'json') {
                        success(false)
                        errors {
                            id("passwordError")
                            msg("Incorrect password for ${params.username}")
                        }
                    }                
                }
            } else {
        	render(builder: 'json') {
                    success(false)
                    errors {
                        id("loginError")
                        msg("User not found for login ${params.username}")
                    }
        	}                
            }
        }
    }    
}