
function doLogin() {
	new Request.HTML({
		url: "/Webapp/index",
		update: $('body') })
		.post({'do': 'login',
			  'username': document.forms.login.elements.username.value,
			  'password': document.forms.login.elements.password.value});
}
