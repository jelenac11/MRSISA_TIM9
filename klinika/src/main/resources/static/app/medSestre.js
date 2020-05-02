Vue.component("medSestre", {
	data : function() {
		return {
			medSestre: [],
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
				      	<th scope="col" width="12%">Ime</th>
				      	<th scope="col" width="16%">Prezime</th>
				      	<th scope="col" width="20%">Email</th>
				      	<th scope="col" width="20%">Adresa</th>
				      	<th scope="col" width="10%">Grad</th>
				      	<th scope="col" width="10%">Država</th>
				      	<th scope="col" width="12%">Broj telefona</th>
			    	</tr>
			  	</thead>
			  	<tbody>
			  		<tr v-for="sestra in medSestre" data-toggle="modal" data-target="#" v-on:click="">
				      	<td width="12%">{{ sestra.ime }}</td>
				      	<td width="16%">{{ sestra.prezime }}</td>
				      	<td width="20%">{{ sestra.email }}</td>
				      	<td width="20%">{{ sestra.adresa }}</td>
				      	<td width="10%">{{ sestra.grad }}</td>
				      	<td width="10%">{{ sestra.drzava }}</td>
				      	<td width="12%">{{ sestra.brojTelefona}}</td>
			    	</tr>
			  	</tbody>
			</table>
			<router-link :to="{ name: 'dodavanjeMedSestara', params: { korisnikToken: this.token } }" class="btn btn-primary btn-block btn-lg my-2 p-2" id="dodavanjeMedSestara">Dodaj novu medicinsku sestru</router-link>
		</div>
	</div>
	`
	,
	created() {
		this.token = this.$route.params.korisnikToken;
		axios
        .get('sestre/ucitajSve', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => (this.medSestre = response.data))
        .catch(function (error) { console.log(error); });
	}
});