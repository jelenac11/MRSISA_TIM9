Vue.component("tipoviPregleda", {
	data : function() {
		return {
			ulogovan: {},
			tipoviPregleda: [],
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
				      	<th scope="col" width="20%">Naziv</th>
				      	<th scope="col" width="80%">Opis</th>
			    	</tr>
			  	</thead>
			  	<tbody>
			  		<tr v-for="tipPregleda in tipoviPregleda" data-toggle="modal" data-target="#" v-on:click="">
				      	<td width="30%">{{ tipPregleda.naziv }}</td>
				      	<td width="70%">{{ tipPregleda.opis }}</td>
			    	</tr>
			  	</tbody>
			</table>
			<router-link :to="{ name: 'dodavanjeTipaPregleda', params: { korisnikToken: this.token } }" class="btn btn-primary btn-block btn-lg my-2 p-2" id="dodavanjeTipaPregleda">Dodaj novi tip pregleda</router-link>
		</div>
	</div>
	`
	,
	created() {
		this.token = this.$route.params.korisnikToken;
		axios
		.get('/auth/dobaviUlogovanog', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => { 
        	this.korisnik=response.data
        	axios
            .get('/tipoviPregleda/ucitajSvePoIdKlinike/'+this.korisnik.id, { headers: { Authorization: 'Bearer ' + this.token }} )
            .then(response => (this.tipoviPregleda = response.data))
            .catch(function (error) { console.log(error); });
        })
        .catch(function (error) { console.log(error); });
		
	}
});