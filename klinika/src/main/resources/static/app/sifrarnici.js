Vue.component("sifrarnici", {
	data : function() {
		return {
			ulogovan: {},
			sifrarnici: [],
			token: "",
		} 
	},
	template: `
	<div> 
		<navig-bar v-bind:token="this.token"></navig-bar>
		
		<div class="tab-content">
			<table class="table table-hover table-striped">
			  	<thead class="thead-light">
			    	<tr>
				      	<th scope="col" width="40%">Naziv</th>
				      	<th scope="col" width="60%">Tip šifrarnika</th>
			    	</tr>
			  	</thead>
			  	<tbody>
			  		<tr v-for="sifrarnik in sifrarnici" data-toggle="modal" data-target="#" v-on:click="">
				      	<td width="40%">{{ sifrarnik.naziv }}</td>
				      	<td width="60%">{{ sifrarnik.tipSifrarnika }}</td>
			    	</tr>
			  	</tbody>
			</table>
			<router-link :to="{ name: 'dodavanjeSifrarnika', params: { korisnikToken: this.token } }" class="btn btn-primary btn-block btn-lg my-2 p-2" id="dodavanjeSale">Dodaj novi šifrarnik</router-link>
		</div>
	</div>
	`
	,
	created() {
		this.token = this.$route.params.korisnikToken;
		axios
        .get('/sifrarnici/ucitajSve', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => (this.sifrarnici = response.data))
        .catch(function (error) { console.log(error); });
	}
});