class User { 
	String username
	String password
	boolean enabled

	static constraints = {
		username(blank:false,unique:true)
		password(blank:false)
		enabled()
	}
}