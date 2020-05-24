Vue.component("potvrda-registracije", {
	data : function() {
		return  {

		} 
	},
	template: `
	<div> 
		Uspesno zavrsena registracija. Ako zelite da se ulogujete, kliknite na link <router-link :to="{ path: '/' }" class="btn btn-default">LOGIN</router-link>
	</div>
	`
	,
	mounted() {

	}
});