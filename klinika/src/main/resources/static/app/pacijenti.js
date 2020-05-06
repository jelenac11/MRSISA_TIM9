Vue.component("pacijenti", {
	data : function() {
		return {
			pacijenti: [],
			token: "",
		} 
	},
	template: `
	<div> 
		<navig-bar v-bind:token="this.token"></navig-bar>
		
		Pacijenti
	</div>
	`
	,
	created() {
		this.token = localStorage.getItem("token");
	}
});